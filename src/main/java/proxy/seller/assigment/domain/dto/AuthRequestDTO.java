package proxy.seller.assigment.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;
import proxy.seller.assigment.constants.Constants;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class AuthRequestDTO implements Serializable {
    private String usernameOrEmail;
    @Pattern(regexp = Constants.VALID_PASSWORD_REGEXP,
            message = Constants.VALID_PASSWORD_MESSAGE)
    private String password;
}
