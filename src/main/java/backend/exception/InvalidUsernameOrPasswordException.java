package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidUsernameOrPasswordException extends RuntimeException {
	
	private static final long serialVersionUID = 5945142867705789458L;

	public InvalidUsernameOrPasswordException(String message) {
		super(message);
	}

}
