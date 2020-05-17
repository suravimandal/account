package backend.service;

import java.util.Optional;

import backend.model.Pin;
import backend.repository.PinRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import backend.email.SendEmailHTML;

import javax.mail.MessagingException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private PasswordTokenService passwordService;

		
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PinRepository pinRepository;
	
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
	public TokenDTO authenticateByPin(Integer pin, String email) {
		Pin searchPin = new Pin();
		searchPin.setEmail(email);

		Example<Pin> pinExample = Example.of(searchPin);
		Optional<Pin> optional = pinRepository.findOne(pinExample);

		if (optional.isEmpty()) {
			throw new InvalidUsernameOrPasswordException("Invalid email!");
		}

		Pin pin1 = optional.get();

		if (pin.equals(pin1.getPin())) {
			Token token = new Token();
			User searchUser = new User();
			searchUser.setEmail(email);

			Example<User> userExample = Example.of(searchUser);
			Optional<User> optional1 = userRepository.findOne(userExample);
			User user = optional1.get();
			token.setUserId(user.getId());
			token.setAuthorization(passwordService.generateAccessToken());
			tokenRepository.save(token);

			TokenDTO tokenDTO = new TokenDTO();
			BeanUtils.copyProperties(token, tokenDTO);
			pinRepository.deleteById(pin1.getId());

			return tokenDTO;
		}
		else{
			throw new InvalidUsernameOrPasswordException("Invalid username or password.");
		}


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
