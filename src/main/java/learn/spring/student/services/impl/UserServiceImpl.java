package learn.spring.student.services.impl;

import jakarta.transaction.Transactional;
import learn.spring.student.config.JwtService;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.entities.UserEntity;
import learn.spring.student.exception.NotFoundUserException;
import learn.spring.student.exception.StudentException;
import learn.spring.student.maps.impl.UserMapperImpl;
import learn.spring.student.models.UserModel;
import learn.spring.student.repositories.UserRepository;
import learn.spring.student.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapperImpl userMapper;

    @Override
    @Cacheable(value = "UserModel")
    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(userMapper::entityMapToModel).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "UserModel", key = "#id")
    public UserModel findById(Integer id) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        if (user.isPresent()) return userMapper.entityMapToModel(user.get());
        else throw new NotFoundUserException(EntityMessage.NOT_FOUND);
    }

    @Override
    @Transactional
    @CachePut(value = "UserModel", key = "#user.userId")
    public UserModel create(UserModel user) {
        if (existUser(user.getUsername())) {
            throw new StudentException(EntityMessage.EXIST);
        }
        userRepository.save(userMapper.modelMapToEntity(user));
        return user;
    }

    @Override
    @CacheEvict(value = "UserModel", key = "#id")
    public UserModel delete(Integer id) {
        if (findById(id) != null) {
            userRepository.deleteById(id);
            return findById(id);
        } else throw new StudentException(EntityMessage.NOT_FOUND);
    }

    @Override
    public String login(UserModel user) {
        UserEntity userEntity = userMapper.modelMapToEntity(user);
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(), userEntity.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateToken(userEntity);
        } catch (Exception e) {
            throw new NotFoundUserException(EntityMessage.NOT_FOUND_USER);
        }

    }

    @Override
    public Boolean existUser(String username) {
        Optional<UserEntity> userExist = this.userRepository.findByUsername(username);
        return userExist.isPresent();
    }
}
