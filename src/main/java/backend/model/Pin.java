package backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pins")
@NoArgsConstructor
public class Pin {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer pin;

    private String email;

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPin(Integer pin) {
        this.pin=pin;
    }


    public Integer getPin() {
        return pin;
    }

    public Long getId() {
        return id;
    }
}
