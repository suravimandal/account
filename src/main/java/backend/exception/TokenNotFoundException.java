package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5707899784085005366L;

	public TokenNotFoundException(String message) {
		super(message);
	}
}
