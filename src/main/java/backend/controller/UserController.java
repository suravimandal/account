package backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.UserDTO;
import backend.service.UserService;
import backend.service.UserServiceImpl;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	List<String> all() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("erwin@erwin.com");
		userDTO.setName("erwin");	
		userService.create(userDTO);
		List<String> a = new ArrayList<>();
		a.add("erwin");
		
		Function<List<Long>, List<Long>> adder = (value) -> value;
		
		return a;
	}
}
