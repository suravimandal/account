package backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import backend.dto.CreateAddressDTO;
import backend.exception.UserNotFoundException;
import backend.model.Address;
import backend.model.User;
import backend.repository.AddressRepository;
import backend.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired 
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public CreateAddressDTO create(CreateAddressDTO dto) {
		
		Optional<User> userOpt = userRepository.findById(dto.getUserId());
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			Address address  = new Address();
			BeanUtils.copyProperties(dto, address);
			address.addUser(user);
			address = addressRepository.save(address);
			dto.setId(address.getId());
			return dto;
		}	
		throw new UserNotFoundException();
	}

	@Override
	public List<CreateAddressDTO> getUserAddress(long userId) {
		Iterable<Address> itAddress = getAddressesByUserId(userId);
		return StreamSupport
		.stream(itAddress.spliterator(), false)
		.map(e -> {
			CreateAddressDTO dto = new CreateAddressDTO();
			BeanUtils.copyProperties(e, dto);
			return dto;
		})
		.collect(Collectors.toList());	
	}

	@Override
	public void deleteUserAddress(long userId, long addressId) {
		
		Optional<Address> optAddress = addressRepository.findById(addressId);
		if(optAddress.isPresent()) {
			Address address = optAddress.get();
			if(address.getUser().getId() != userId) {
				return;
			}
			addressRepository.delete(address);
		}		
	}
	
	private Iterable<Address> getAddressesByUserId(long userId) {
		Address criteria = new Address();
		User user = new User();
		user.setId(userId);
		
		criteria.setUser(user);
		criteria.setIsDeleted(false);
		
		Example<Address> exAddress = Example.of(criteria);
		return addressRepository.findAll(exAddress);
	}

}
