package backend.service;

import java.util.List;

import backend.dto.PinDTO;
import backend.dto.UserDTO;

import javax.mail.MessagingException;


public interface UserService {

	public Long create(UserDTO userDTO);
	
	public void update(UserDTO userDTO);

	public Boolean sendPin(PinDTO pinDTO) throws MessagingException;

	public void deletePinAfter5Mins(Long id);
	
	public UserDTO getById(Long userId);
	
	public List<UserDTO> findAll();
	
	public void deleteById(Long userId);
}
