package backend.service;

import java.util.List;

import backend.dto.CreateAddressDTO;


public interface AddressService {

	public CreateAddressDTO create(CreateAddressDTO dto);
	
	public List<CreateAddressDTO> getUserAddress(long userId);
	
	public void deleteUserAddress(long userId, long addressId);
}
