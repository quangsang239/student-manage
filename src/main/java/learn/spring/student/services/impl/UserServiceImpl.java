package learn.spring.student.services.impl;

import jakarta.transaction.Transactional;
import learn.spring.student.common.EntityMessage;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.common.EnumStatusResponse;
import learn.spring.student.config.JwtService;
import learn.spring.student.entities.UserEntity;
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
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.getDataSuccess,
                userRepository.findAll().stream().map(userMapper::entityMapToModel).collect(Collectors.toList()));
    }

    @Override
    public EntityResponse<UserModel> findById(Integer id) {
        Optional<UserEntity> user = this.userRepository.findById(id);
        return user.map(userEntity -> new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.getDataSuccess,
                userMapper.entityMapToModel(userEntity))).orElse(null);
    }

    @Override
    @Transactional
    public EntityResponse<UserModel> create(UserModel user) {
        if (existUser(user.getUsername())){
            return new EntityResponse<>(EnumStatusResponse.WARNING,EntityMessage.exist, null);
        }
        userRepository.save(userMapper.modelMapToEntity(user));
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.createSuccess, null);
    }

    @Override
    public EntityResponse<UserModel> delete(Integer id) {
        if (findById(id)!= null){
            userRepository.deleteById(id);
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.deleteSuccess, null);
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.deleteFail, null);
    }

    @Override
    public String login(UserModel user) {
        UserEntity userEntity = userMapper.modelMapToEntity(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userEntity.getUsername(), userEntity.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateToken(userEntity);
    }

    @Override
    public Boolean existUser(String username) {
        Optional<UserModel> userExist = this.userRepository.userExist(username);
        return userExist.isPresent();
    }
}
