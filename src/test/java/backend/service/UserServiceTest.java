package backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import backend.WebConfig;
import backend.dto.UserDTO;
import backend.model.User;
import backend.repository.UserRepository;

@SpringBootTest(classes = {UserServiceImpl.class, PasswordTokenServiceImpl.class})
@Import(WebConfig.class)
class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordTokenService passwordTokenService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	void addUserTest() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@test.nus.edu.sg");
		userDTO.setName("Test 1");
		userDTO.setPassword("********");
		
		User user = new User();
		user.setId(1l);
		when(userRepository.save(any())).thenReturn(user);
		
		userService.create(userDTO);
		
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void updateUserTest() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@test.nus.edu.sg");
		userDTO.setName("Test 1");
		userDTO.setPassword("********");
		
		userService.update(userDTO);
		
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void getUserByIdTest() {
		User user = new User();
		user.setId(1l);
		
		when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		userService.getById(1l);
		
		verify(userRepository, times(1)).findById(1l);
	}
	
	@Test
	void deleteUserByIdTest() {
		userService.deleteById(1l);
		verify(userRepository, times(1)).deleteById(1l);
	}
	
	@Test
	void findAllUserTest() {
		userService.findAll();
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	void passwordEqualityTest() {
		String plain_password = "testpassword";
		String hashPassword1 = passwordTokenService.encodePassword(plain_password);
		String hashPassword2 = passwordTokenService.encodePassword(plain_password);
		
		assertTrue(passwordTokenService.checkPassword(plain_password, hashPassword1));
		assertTrue(passwordTokenService.checkPassword(plain_password, hashPassword2));
	}
	
}
