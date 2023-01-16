package learn.spring.student.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import learn.spring.student.constants.EntityMessage;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterModel {

    @NotNull(message = EntityMessage.USERNAME_NULL_FIELD)
    @NotBlank(message = EntityMessage.USERNAME_BLANK_FIELD)
    @Size(max = 20, message = EntityMessage.USERNAME_OVER_SIZE)
    private String username;

    @NotNull(message = EntityMessage.PASSWORD_NULL_FIELD)
    @NotBlank(message = EntityMessage.PASSWORD_BLANK_FIELD)
    @Size(max = 15, min = 6, message = EntityMessage.PASSWORD_OVER_SIZE)
    private String password;

    @NotNull(message = EntityMessage.PASSWORD_NULL_FIELD)
    @NotBlank(message = EntityMessage.PASSWORD_BLANK_FIELD)
    @Size(max = 15, min = 6, message = EntityMessage.PASSWORD_OVER_SIZE)
    private String confirmPassword;
}
