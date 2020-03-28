package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.TokenDTO;
import backend.dto.UserDTO;
import backend.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public TokenDTO login(@RequestParam String email, @RequestParam String password) {
		return authenticationService.authenticate(email, password);
	}
	
	@GetMapping("/user/token")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getByToken(@RequestHeader("authorization") String accessToken) {
		return authenticationService.getUserByToken(accessToken);
	}

}
