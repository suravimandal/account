package backend.service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import backend.dto.UserDTO;

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {
	
	@Override
	public String encodePassword(String password) {
		return sha256hash(password);
	}

	@Override
	public UserDTO encodePassword(UserDTO userDTO) {
		userDTO.setPassword(encodePassword(userDTO.getPassword()));
		return userDTO;
	}
	
	@Override
	public boolean checkPassword(String password, String hashedPassword) {
		return sha256hash(password).equals(hashedPassword);
	} 
	
	@Override
	public String generateAccessToken() {	
		return sha256hash(UUID.randomUUID().toString());
	}
	
	private String sha256hash(String originalString) {
		String sha256hex = Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
		return sha256hex;
	}
	
	
}
