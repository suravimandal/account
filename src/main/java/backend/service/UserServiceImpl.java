package backend.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.UserDTO;
import backend.exception.UserNotFoundException;
import backend.model.User;
import backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordTokenService passwordService;
	
	public Long create(UserDTO userDTO) {
		passwordService.encodePassword(userDTO);
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		userRepository.save(user);
		
		return user.getId();
	}

	@Override
	public void update(UserDTO userDTO) {
		passwordService.encodePassword(userDTO);
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		userRepository.save(user);
	}

	@Override
	public UserDTO getById(Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		
		if (optional.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		User user = optional.get();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		
		return userDTO;
	}

	@Override
	public List<UserDTO> findAll() {
		Iterable<User> iterable = userRepository.findAll();

		List<UserDTO> result = StreamSupport.stream(iterable.spliterator(), false).map(new Function<User, UserDTO>() {
			@Override
			public UserDTO apply(User user) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(user, userDTO);
				
				return userDTO;
			}
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

}
