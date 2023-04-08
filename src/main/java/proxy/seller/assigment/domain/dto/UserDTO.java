package proxy.seller.assigment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import proxy.seller.assigment.constants.Constants;
import proxy.seller.assigment.domain.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String id;
    private String username;
    @Pattern(regexp = Constants.VALID_PASSWORD_REGEXP,
            message = Constants.VALID_PASSWORD_MESSAGE)
    private String password;
    @Email(message = Constants.VALID_EMAIL_MESSAGE)
    private String email;

    public static UserDTO from(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail());
    }

    public static UserDTO fromWithPassword(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setPassword(user.getPassword())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail());
    }

    public User toDto() {
        return new User()
                .setUsername(username)
                .setPassword(password)
                .setEmail(email);
    }

}
