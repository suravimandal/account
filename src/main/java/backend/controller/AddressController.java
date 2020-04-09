package backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	
	@DeleteMapping(value = "/{userId}/addresses/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId) {
		addressService.deleteUserAddress(userId, addressId);
    }
	
}
