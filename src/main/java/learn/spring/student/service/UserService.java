package learn.spring.student.service;

import learn.spring.student.model.UserModel;

public interface UserService extends Service<UserModel> {
    String login(UserModel user);

    Boolean existUser(String username);
}
