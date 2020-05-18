package backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import backend.dto.CreateAddressDTO;
import backend.service.AddressService;

@RestController
@RequestMapping("/user")	
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping(value = "/{userId}/addresses")
	@ResponseStatus(HttpStatus.OK)
    public List<CreateAddressDTO> findAll(@PathVariable("userId") Long userId) {
        return addressService.getUserAddress(userId);
	}
	
	@PostMapping(value = "/{userId}/addresses")
	@ResponseStatus(HttpStatus.CREATED)
	public CreateAddressDTO create(@PathVariable("userId") Long userId, @RequestBody CreateAddressDTO dto) {
		dto.setUserId(userId);
        return addressService.create(dto);
	}
	@CrossOrigin(origins = "http://127.0.0.1:4200")
	@DeleteMapping(value = "/{userId}/addresses/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId) {
		addressService.deleteUserAddress(userId, addressId);
    }
	@CrossOrigin(origins = "http://127.0.0.1:4200")
	@PutMapping(value = "/{userId}/addresses/{addressId}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId, @RequestBody CreateAddressDTO addressDTO) {
		addressDTO.setUserId(userId);
		//addressDTO.setId(addressId);
		//System.out.println(addressId);
		addressService.update(addressDTO, addressId);
	}
	
}
