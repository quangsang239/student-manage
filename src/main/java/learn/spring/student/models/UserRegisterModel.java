package learn.spring.student.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterModel {
    private String username;
    private String password;
    private String confirmPassword;
}
