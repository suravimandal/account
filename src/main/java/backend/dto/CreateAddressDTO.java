package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressDTO {

	private Long id;
	
	private String name;
	
	private String block;
	
	private String street;
	
	private String unitNumber;
	
	private String postalCode;
	
	private Long userId;
}
