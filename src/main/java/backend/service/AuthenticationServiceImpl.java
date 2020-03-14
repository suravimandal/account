package backend.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import backend.dto.TokenDTO;
import backend.dto.UserDTO;
import backend.exception.InvalidUsernameOrPasswordException;
import backend.exception.TokenNotFoundException;
import backend.model.Token;
import backend.model.User;
import backend.repository.TokenRepository;
import backend.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final static char[] hexArray = "0123456789abcdef".toCharArray();

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public TokenDTO authenticate(String email, String password) {
		User searchUser = new User();
		searchUser.setEmail(email);
		searchUser.setPassword(password);
		
		Example<User> userExample = Example.of(searchUser);
		Optional<User> optional = userRepository.findOne(userExample);
		
		if (optional.isEmpty()) {
			throw new InvalidUsernameOrPasswordException("Invalid username or password.");
		}
		
		User user = optional.get();

		Token token = new Token();
		token.setUserId(user.getId());
		token.setAuthorization(generateAccessToken());
		token.setCreatedDate(ZonedDateTime.now());
		tokenRepository.save(token);

		TokenDTO tokenDTO = new TokenDTO();
		BeanUtils.copyProperties(token, tokenDTO);

		return tokenDTO;
	}
	
	@Override
	public UserDTO getUserByToken(String acccessToken) {
		Token searchToken = new Token();
		searchToken.setAuthorization(acccessToken);
		
		Example<Token> tokenExample = Example.of(searchToken);
		Optional<Token> optional = tokenRepository.findOne(tokenExample);
		
		if (optional.isEmpty()) {
			throw new TokenNotFoundException("Token not found.");
		}
		
		Token token = optional.get();
		long userId = token.getUserId();

		Optional<User> optionalUser = userRepository.findById(userId);

		User user = optionalUser.get();

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);

		return userDTO;
	}
	
	private static String generateAccessToken() {
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
	
	private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
