package backend.service;

import backend.dto.TokenDTO;
import backend.dto.UserDTO;

public interface AuthenticationService {
	
	public TokenDTO authenticate(String email, String password);
	
	public UserDTO getUserByToken(String acccessToken);
}
