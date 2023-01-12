package learn.spring.student.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer userId;
    private String username;
    private String password;
}
