package backend.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import backend.dto.PinDTO;
import backend.email.SendEmailHTML;
import backend.exception.InvalidUsernameOrPasswordException;
import backend.model.Pin;
import backend.repository.PinRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import backend.dto.UserDTO;
import backend.exception.UserNotFoundException;
import backend.model.User;
import backend.repository.UserRepository;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	public Environment env;
	public final String EMAIL_USERNAME = "email.username";
	public final String EMAIL_PASSWORD = "email.password";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PinRepository pinRepository;
	
	@Autowired
	private PasswordTokenService passwordService;
	
	public Long create(UserDTO userDTO) {
		List<UserDTO> existingUserDTO = findAll();
		String email = userDTO.getEmail();
		Boolean emailExistOrNot = existingUserDTO.stream().anyMatch(o -> o.getEmail().equals(email));
		if (emailExistOrNot.equals(true)){
			return 0L;
		}
		else {
			passwordService.encodePassword(userDTO);
			User user = new User();
			BeanUtils.copyProperties(userDTO, user);
			userRepository.save(user);
			return user.getId();
		}
	}


	public Boolean sendPin(PinDTO pinDTO) throws MessagingException {
		List<UserDTO> existingUserDTO = findAll();
		String email=pinDTO.getEmail();
		Boolean emailExistOrNot = existingUserDTO.stream().anyMatch(o -> o.getEmail().equals(email));

		if (emailExistOrNot.equals(true)){
			double pin_v=(Math.random() * ((999999 - 100000) + 1)) + 100000;
			Integer pin2_v = (int) pin_v;
			SendEmailHTML.sendmail(env.getProperty(EMAIL_USERNAME),env.getProperty(EMAIL_PASSWORD),email, "Your password Recovery pin is here","your one time pin is "+pin2_v+". " + "This pin is valid for 5 minutes.");
			Pin pin1_m = new Pin();
			pin1_m.setEmail(email);
			pin1_m.setPin(pin2_v);
			Pin searchPin1_m = new Pin();
			searchPin1_m.setEmail(email);

			Example<Pin> pinExample1 = Example.of(searchPin1_m);
			Optional<Pin> optional1 = pinRepository.findOne(pinExample1);
			if (!optional1.isEmpty()) {
				pinRepository.deleteById(optional1.get().getId());
			}
			pinRepository.save(pin1_m);
			Pin searchPin_m = new Pin();
			searchPin_m.setEmail(email);

			Example<Pin> pinExample = Example.of(searchPin_m);
			Optional<Pin> optional = pinRepository.findOne(pinExample);

			Pin pin3_m=optional.get();


			deletePinAfter5Mins(pin3_m.getId());
			return true;
		}
		else{
			return false;
		}

	}

	@Override
	public void update(UserDTO userDTO) {
		passwordService.encodePassword(userDTO);
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		userRepository.save(user);
	}

	@Override
	public UserDTO getById(Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		
		if (optional.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		User user = optional.get();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		
		return userDTO;
	}





	@Override
	public List<UserDTO> findAll() {
		Iterable<User> iterable = userRepository.findAll();

		List<UserDTO> result = StreamSupport.stream(iterable.spliterator(), false).map(new Function<User, UserDTO>() {
			@Override
			public UserDTO apply(User user) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(user, userDTO);
				
				return userDTO;
			}
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void deletePinAfter5Mins(Long id) {
		ScheduledExecutorService scheduler
				= Executors.newSingleThreadScheduledExecutor();

		Runnable task = () -> pinRepository.deleteById(id);

		int delay = 300;
		scheduler.schedule(task, delay, TimeUnit.SECONDS);
		scheduler.shutdown();
	}

}
