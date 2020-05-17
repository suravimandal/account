package backend.service;

import backend.dto.TokenDTO;
import backend.dto.UserDTO;

import javax.mail.MessagingException;

public interface AuthenticationService {
	
	public TokenDTO authenticate(String email, String password);

	public TokenDTO authenticateByPin(Integer pin, String email);


	
	public UserDTO getUserByToken(String acccessToken);
}
