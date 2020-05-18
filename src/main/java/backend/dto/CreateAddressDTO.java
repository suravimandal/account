package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAddressDTO {

	private Long id;
	
	private String name;
	
	private String block;

	private Long phone;

	private String altPhone;

	private String villOrSector;

	private String townOrCity;

	private String street;
	
	private String landmark;
	
	private String postalCode;

	private String state;

	private String type;
	
	private Long userId;

	public void setId(long id){this.id=id;}

	public void setUserId(long userId){this.userId=userId;}

	public Long getId() { return id;
	}
}
