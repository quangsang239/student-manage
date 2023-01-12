package learn.spring.student.maps.impl;

import learn.spring.student.entities.StudentEntity;
import learn.spring.student.entities.StudentInfoEntity;
import learn.spring.student.maps.StudentMapper;
import learn.spring.student.models.StudentInfoModel;
import learn.spring.student.models.StudentModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class StudentMapperImpl implements StudentMapper {
    @Override
    public StudentEntity modelMapToEntity(StudentModel model) {
        if (model == null) return null;
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentId(model.getStudentId());
        studentEntity.setStudentCode(model.getStudentCode());
        studentEntity.setStudentName(model.getStudentName());
        StudentInfoEntity studentInfoEntity = new StudentInfoEntity();
        studentInfoEntity.setInfoId(model.getStudentInfoModel().getInfoId());
        studentInfoEntity.setAddress(model.getStudentInfoModel().getAddress());
        studentInfoEntity.setAverageScore(model.getStudentInfoModel().getAverageScore());
        studentInfoEntity.setDateOfBirth(model.getStudentInfoModel().getDateOfBirth());
        studentEntity.setStudentInfoEntity(studentInfoEntity);
        return studentEntity;
    }

    @Override
    public StudentModel entityMapToModel(StudentEntity entity) {
        if (entity == null) return null;
        StudentInfoModel  studentInfoModel = StudentInfoModel.builder().infoId(entity.getStudentInfoEntity().getInfoId())
                .address(entity.getStudentInfoEntity().getAddress())
                .averageScore(entity.getStudentInfoEntity().getAverageScore())
                .dateOfBirth(entity.getStudentInfoEntity().getDateOfBirth()).build();
        return StudentModel.builder().studentId(entity.getStudentId())
                .studentCode(entity.getStudentCode()).studentInfoModel(studentInfoModel)
                .studentName(entity.getStudentName()).build();
    }
}
