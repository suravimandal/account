package backend.service;

import java.util.List;

import backend.dto.UserDTO;


public interface UserService {

	public Long create(UserDTO userDTO);
	
	public void update(UserDTO userDTO);
	
	public UserDTO getById(Long userId);
	
	public List<UserDTO> findAll();
	
	public void deleteById(Long userId);
}
