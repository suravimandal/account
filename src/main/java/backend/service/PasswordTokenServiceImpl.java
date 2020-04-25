package backend.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.dto.UserDTO;

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {
	
	private final static char[] hexArray = "0123456789abcdef".toCharArray();

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public UserDTO encodePassword(UserDTO userDTO) {
		userDTO.setPassword(encodePassword(userDTO.getPassword()));
		return userDTO;
	}
	
	@Override
	public String generateAccessToken() {
		MessageDigest salt;
		try {
			salt = MessageDigest.getInstance("SHA-256");
			salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
			String digest = bytesToHex(salt.digest());
			
			return digest;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
