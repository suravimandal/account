package backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "addresses")
@NoArgsConstructor
public class Address extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String block;

	private Long phone;

	private String altPhone;

	private String villOrSector;

	private String townOrCity;
	
	private String street;

	
	private String landmark;

	private String type;

	private String postalCode;

	private String state;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public void addUser(User user) {
		this.user = user;
		this.user.getAddresses().add(this);
	}

	public long getUserId(){
		return user.getId();
	}

	public Long getId(){
		return id;
	}


}
