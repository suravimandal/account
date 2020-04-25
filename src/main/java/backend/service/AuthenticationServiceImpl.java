package backend.service;

import java.util.Optional;

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
	
	@Autowired
	private PasswordTokenService passwordService;
		
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public TokenDTO authenticate(String email, String password) {
		User searchUser = new User();
		searchUser.setEmail(email);
		
		Example<User> userExample = Example.of(searchUser);
		Optional<User> optional = userRepository.findOne(userExample);
		
		if (optional.isEmpty()) {
			throw new InvalidUsernameOrPasswordException("Invalid username or password.");
		}
		
		User user = optional.get();
		
		if (!passwordService.checkPassword(password, user.getPassword())) {
			throw new InvalidUsernameOrPasswordException("Invalid username or password.");
		}

		Token token = new Token();
		token.setUserId(user.getId());
		token.setAuthorization(passwordService.generateAccessToken());
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


}
