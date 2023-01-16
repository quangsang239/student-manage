package learn.spring.student.services.impl;

import jakarta.transaction.Transactional;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.constants.EnumStatusResponse;
import learn.spring.student.config.JwtService;
import learn.spring.student.entities.UserEntity;
import learn.spring.student.exception.NotFoundUserException;
import learn.spring.student.maps.impl.UserMapperImpl;
import learn.spring.student.models.UserModel;
import learn.spring.student.repositories.UserRepository;
import learn.spring.student.services.UserService;
import lombok.RequiredArgsConstructor;
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
    public EntityResponse<List<UserModel>> findAll() {
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                userRepository.findAll().stream().map(userMapper::entityMapToModel).collect(Collectors.toList()));
    }

    @Override
    public EntityResponse<UserModel> findById(Integer id) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        return user.map(userEntity -> new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                userMapper.entityMapToModel(userEntity))).orElse(null);
    }

    @Override
    @Transactional
    public EntityResponse<UserModel> create(UserModel user) {
        if (existUser(user.getUsername())){
            return new EntityResponse<>(EnumStatusResponse.WARNING,EntityMessage.EXIST, null);
        }
        userRepository.save(userMapper.modelMapToEntity(user));
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.CREATE_SUCCESS, null);
    }

    @Override
    public EntityResponse<UserModel> delete(Integer id) {
        if (findById(id)!= null){
            userRepository.deleteById(id);
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.DELETE_SUCCESS, null);
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.DELETE_FAIL, null);
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
        } catch (Exception e){
            throw new NotFoundUserException(EntityMessage.NOT_FOUND_USER);
        }

    }

    @Override
    public Boolean existUser(String username) {
        Optional<UserEntity> userExist = this.userRepository.findByUsername(username);
        return userExist.isPresent();
    }
}
