package learn.spring.student.services;

import learn.spring.student.models.UserModel;

public interface UserService extends Service<UserModel> {
    String login(UserModel user);

    Boolean existUser(String username);
}
