package backend.controller;

import java.util.List;

import backend.dto.PinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import backend.dto.UserDTO;
import backend.service.UserService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/user")	
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getById(@PathVariable Long id) {
		return userService.getById(id);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
	}

	@PostMapping("/recovery")
	@ResponseStatus(HttpStatus.OK)
	public Boolean sendPin(@RequestBody PinDTO pinDTO) throws MessagingException {
		return userService.sendPin(pinDTO);
	}


	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
	}
	
	@DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
		userService.deleteById(id);
    }
	
	
}
