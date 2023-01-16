package learn.spring.student.maps.impl;

import learn.spring.student.entities.FileDBEntity;
import learn.spring.student.maps.FileDBMapper;
import learn.spring.student.models.FileDBModel;
import org.springframework.stereotype.Component;

@Component
public class FileDBMapperImpl implements FileDBMapper {
    @Override
    public FileDBEntity modelMapToEntity(FileDBModel model) {
        if (model == null) return null;
        FileDBEntity fileDBEntity = new FileDBEntity();
        fileDBEntity.setId(model.getId());
        fileDBEntity.setType(model.getType());
        fileDBEntity.setName(model.getName());
        fileDBEntity.setData(model.getData());
        return fileDBEntity;
    }

    @Override
    public FileDBModel entityMapToModel(FileDBEntity entity) {
        if (entity == null) return null;
        return FileDBModel.builder().id(entity.getId()).data(entity.getData()).name(entity.getName())
                .type(entity.getType()).build();
    }
}
