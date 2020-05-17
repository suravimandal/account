package backend.dto;
import java.time.ZonedDateTime;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class PinDTO {

    private Long id;
    private String email;
    private Integer pin;


    public String getEmail() {
        return email;
    }
}
