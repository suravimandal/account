package backend.service;

import backend.dto.UserDTO;

public interface PasswordTokenService {
	
	public String encodePassword(String password);
	
	public UserDTO encodePassword(UserDTO userDTO);
	
	public String generateAccessToken();

}
