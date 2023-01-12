package learn.spring.student.maps.impl;

import learn.spring.student.entities.UserEntity;
import learn.spring.student.maps.UserMapper;
import learn.spring.student.models.UserModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class UserMapperImpl implements UserMapper {
    @Override
    public UserEntity modelMapToEntity(UserModel model) {
        if (model == null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(model.getUserId());
        userEntity.setUsername(model.getUsername());
        userEntity.setPassword(model.getPassword());
        return userEntity;
    }

    @Override
    public UserModel entityMapToModel(UserEntity entity) {
        if (entity == null)return null;
        UserModel userModel = new UserModel();
        userModel.setUserId(entity.getUserId());
        userModel.setUsername(entity.getUsername());
        userModel.setPassword(entity.getPassword());
        return userModel;
    }
}
