package learn.spring.student.controller;

import learn.spring.student.common.EntityResponse;
import learn.spring.student.common.EnumStatusResponse;
import learn.spring.student.model.UserModel;
import learn.spring.student.model.UserRegisterModel;
import learn.spring.student.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final
    UserServiceImpl userServiceImpl;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/get-all-user")
    @ResponseBody
    public ResponseEntity<EntityResponse<List<UserModel>>> getAllUser() {
        EntityResponse<List<UserModel>> dataResponse = new EntityResponse<>(EnumStatusResponse.SUCCESS,
                "Get user successful!",
                userServiceImpl.findAll());
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping(value = "/get-user-by-id/{userId}")
    @ResponseBody
    public UserModel getUserById(@PathVariable("userId") Integer userId) {
        return userServiceImpl.findById(userId);
    }

    @PostMapping("/create-user")
    @ResponseBody
    public EntityResponse<UserModel> createUser(@RequestBody UserRegisterModel user) {
        StringBuilder message = new StringBuilder();
        message.append(checkFormRegisterEmpty(user));
        if (message.toString().isBlank()) {
            message.append(checkMinLengthInputRegister(user));
            message.append(checkMaxLengthRegister(user));
            message.append(checkChar1ByteRegister(user));
            if (message.toString().isBlank()) {
                if (user.getPassword().equals(user.getConfirmPassword())) {
                    if (Boolean.FALSE.equals(userServiceImpl.existUser(user.getUsername()))) {
                        try {
                            UserModel newUser = new UserModel();
                            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                            newUser.setUsername(user.getUsername());
                            userServiceImpl.create(newUser);
                            return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Create user success!",
                                    null);
                        } catch (Exception e) {
                            return new EntityResponse<>(EnumStatusResponse.ERROR, "Server error!", null);
                        }
                    } else
                        message.append("User is exist!");
                } else
                    message.append("Password unlike confirm password!");
            }
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, message.toString(), null);
    }

    private String checkFormRegisterEmpty(UserRegisterModel user) {

        StringBuilder message = new StringBuilder();
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            message.append("User name is empty!");
        }
        if (user.getConfirmPassword() == null || user.getConfirmPassword().isBlank()) {
            message.append("Confirm password is empty!");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            message.append("Password is empty!");
        }
        return message.toString();
    }

    private String checkMinLengthInputRegister(UserRegisterModel user) {
        StringBuilder message = new StringBuilder();
        if (user.getConfirmPassword().length() < 6) {
            message.append("Confirm password require length >= 6 char!");
        }
        if (user.getPassword().length() < 6) {
            message.append("Password require length >= 6 char!");
        }
        return message.toString();
    }

    private String checkMaxLengthRegister(UserRegisterModel user) {
        StringBuilder message = new StringBuilder();
        if (user.getUsername().length() > 20) {
            message.append("Max length username require <= 20 char!");
        }
        if (user.getPassword().length() > 15) {
            message.append("Max length password require <= 15 char!");
        }
        if (user.getConfirmPassword().length() > 15) {
            message.append("Max length confirm password require <= 15 char!");
        }
        return message.toString();
    }

    private String checkChar1ByteRegister(UserRegisterModel user) {
        StringBuilder message = new StringBuilder();
        if (Boolean.TRUE.equals(checkChar(user.getUsername()))) {
            message.append("Username have character > 2 byte");
        }
        if (Boolean.TRUE.equals(checkChar(user.getPassword()))) {
            message.append("Password have character > 2 byte");
        }
        if (Boolean.TRUE.equals(checkChar(user.getConfirmPassword()))) {
            message.append("Confirm password have character > 2 byte");
        }
        return message.toString();
    }

    private Boolean checkChar(String string) {
        byte[] convertStringToByte = string.getBytes();
        for (byte b : convertStringToByte) {
            if (b < 1 || b > 126) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/login")
    @ResponseBody
    public EntityResponse<String> login(@RequestBody UserModel user) {
        StringBuilder message = new StringBuilder();
        message.append(checkFormLoginEmpty(user));
        if (message.toString().isBlank()) {
            message.append(checkMaxLengthLogin(user));
            message.append(checkMinLengthLogin(user));
            message.append(checkChar1ByteLogin(user));
            if (message.toString().isBlank()) {
                String token = userServiceImpl.login(user);
                if (token != null) {
                    return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Login successful!", token);
                }
                message.append("Username or password is not exact!");
            }
        }
        return new EntityResponse<>(EnumStatusResponse.ERROR, message.toString(), null);
    }

    private String checkFormLoginEmpty(UserModel user) {

        StringBuilder message = new StringBuilder();
        if (user.getUsername() == null || user.getPassword().isBlank()) {
            message.append("User name is empty!");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            message.append("Password is empty!");
        }
        return message.toString();
    }

    private String checkMinLengthLogin(UserModel user) {
        StringBuilder message = new StringBuilder();
        if (user.getPassword().length() < 6) {
            message.append("Password require length >= 6 char!");
        }
        return message.toString();
    }

    private String checkMaxLengthLogin(UserModel user) {
        StringBuilder message = new StringBuilder();
        if (user.getUsername().length() > 20) {
            message.append("Max length username require <= 20 char!");
        }
        if (user.getPassword().length() > 15) {
            message.append("Max length password require <= 15 char!");
        }
        return message.toString();
    }

    private String checkChar1ByteLogin(UserModel user) {
        StringBuilder message = new StringBuilder();
        if (Boolean.TRUE.equals(checkChar(user.getUsername()))) {
            message.append("Username have character > 2 byte");
        }
        if (Boolean.TRUE.equals(checkChar(user.getPassword()))) {
            message.append("Password have character > 2 byte");
        }
        return message.toString();
    }

    @DeleteMapping("/delete-user/{userId}")
    @ResponseBody
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userServiceImpl.delete(userId);
    }
}