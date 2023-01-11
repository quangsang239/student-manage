package learn.spring.student.service.impl;

import learn.spring.student.config.JwtService;
import learn.spring.student.model.UserModel;
import learn.spring.student.repositories.UserRepository;
import learn.spring.student.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public List<UserModel> findAll() {

        return userRepository.findAll();
    }

    @Override
    public UserModel findById(Integer id) {
        Optional<UserModel> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public void create(UserModel user) { this.userRepository.save(user); }

    @Override
    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public String login(UserModel user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateToken(user);
    }

    @Override
    public Boolean existUser(String username) {
        Optional<UserModel> userExist = this.userRepository.userExist(username);
        return userExist.isPresent();
    }

}
