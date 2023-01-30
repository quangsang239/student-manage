package learn.spring.student.controller;

import jakarta.validation.Valid;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.constants.EnumStatusResponse;
import learn.spring.student.models.UserModel;
import learn.spring.student.models.UserRegisterModel;
import learn.spring.student.services.impl.UserServiceImpl;
import learn.spring.student.utils.GetMessageError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/users")
    public EntityResponse<List<UserModel>> getAllUser() {
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                userServiceImpl.findAll());
    }

    @GetMapping(value = "/users/{userId}")
    public EntityResponse<UserModel> getUserById(@PathVariable("userId") Integer userId) {
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                userServiceImpl.findById(userId));
    }

    @PostMapping("/users")
    public EntityResponse<UserModel> createUser(@Valid @RequestBody UserRegisterModel user, BindingResult bindingResult) {
        String message = GetMessageError.getMessageError(bindingResult);
        if (message != null) return new EntityResponse<>(EnumStatusResponse.WARNING, message, null);
        if (user.getPassword().equals(user.getConfirmPassword())) {
            if (Boolean.FALSE.equals(userServiceImpl.existUser(user.getUsername()))) {
                try {
                    UserModel newUser = new UserModel();
                    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    newUser.setUsername(user.getUsername());
                    userServiceImpl.create(newUser);
                    return new  EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.CREATE_SUCCESS, null);
                } catch (Exception e) {
                    return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.SERVER_ERROR, null);
                }
            } else {
                return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.EXIST, null);
            }
        } else {
            return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.PASSWORD_NOT_LIKE_CONFIRM, null);
        }
    }


    @PostMapping("/login")
    public EntityResponse<String> login(@Valid @RequestBody UserModel user, BindingResult bindingResult) {
        String message = GetMessageError.getMessageError(bindingResult);
        if (message != null) return new EntityResponse<>(EnumStatusResponse.WARNING, message, null);
        if (userServiceImpl.existUser(user.getUsername())) {
            String token = userServiceImpl.login(user);
            if (token != null) {
                return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.LOGIN_SUCCESS, token);
            }
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.NOT_FOUND_USER, null);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userServiceImpl.delete(userId);
    }
}