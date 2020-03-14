package backend.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;
	private String name;
	private String address;
	private String password;
	private String email;
	private ZonedDateTime registeredDate;
	private String createdBy;
	private ZonedDateTime createdDate;
	private String lastUpdatedBy;
	private ZonedDateTime lastUpdatedDate;
	
}
