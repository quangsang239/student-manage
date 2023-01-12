package learn.spring.student.maps.impl;

import learn.spring.student.entities.StudentEntity;
import learn.spring.student.entities.StudentInfoEntity;
import learn.spring.student.maps.StudentInfoMapper;
import learn.spring.student.models.StudentInfoModel;
import learn.spring.student.models.StudentModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class StudentInfoMapperImpl implements StudentInfoMapper {
    @Override
    public StudentInfoEntity modelMapToEntity(StudentInfoModel model) {
        if (model == null) return null;
        StudentInfoEntity studentInfoEntity = new StudentInfoEntity();
        studentInfoEntity.setInfoId(model.getInfoId());
        studentInfoEntity.setDateOfBirth(model.getDateOfBirth());
        studentInfoEntity.setAverageScore(model.getAverageScore());
        studentInfoEntity.setAddress(model.getAddress());
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentId(model.getStudentModel().getStudentId());
        studentEntity.setStudentName(model.getStudentModel().getStudentName());
        studentEntity.setStudentCode(model.getStudentModel().getStudentCode());
        studentInfoEntity.setStudentEntity(studentEntity);
        return studentInfoEntity;
    }

    @Override
    public StudentInfoModel entityMapToModel(StudentInfoEntity entity) {
        if (entity == null) return null;
        StudentModel studentModel = StudentModel.builder().studentId(entity.getStudentEntity().getStudentId())
                .studentCode(entity.getStudentEntity().getStudentCode())
                .studentName(entity.getStudentEntity().getStudentName()).build();
        return StudentInfoModel.builder().infoId(entity.getInfoId())
                .address(entity.getAddress()).averageScore(entity.getAverageScore())
                .dateOfBirth(entity.getDateOfBirth()).studentModel(studentModel).build();
    }
}
