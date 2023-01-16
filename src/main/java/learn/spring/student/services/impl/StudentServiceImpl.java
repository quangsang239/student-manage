package learn.spring.student.services.impl;

import jakarta.transaction.Transactional;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.common.EntityPageNumber;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.constants.EnumStatusResponse;
import learn.spring.student.entities.StudentEntity;
import learn.spring.student.entities.StudentInfoEntity;
import learn.spring.student.maps.impl.StudentInfoMapperImpl;
import learn.spring.student.maps.impl.StudentMapperImpl;
import learn.spring.student.models.StudentModel;
import learn.spring.student.repositories.StudentInfoRepository;
import learn.spring.student.repositories.StudentRepository;
import learn.spring.student.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentInfoRepository studentInfoRepository;
    private final StudentMapperImpl studentMapper;
    private final StudentInfoMapperImpl studentInfoMapper;

    @Override
    public EntityResponse<List<StudentModel>> findAll() {
        List<StudentModel> studentModelList =studentRepository.findAll().stream().map(studentMapper::entityMapToModel)
                .collect(Collectors.toList());
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,studentModelList);
    }

    @Override
    public EntityResponse<StudentModel> findById(Integer id) {
        Optional<StudentEntity> sOptional = studentRepository.findById(id);
        if (sOptional.isPresent()){
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                    sOptional.map(studentMapper::entityMapToModel).orElse(null));
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.NOT_FOUND, null);

    }

    @Override
    @Transactional
    public EntityResponse<StudentModel> create(StudentModel model) {
        if (existStudent(model.getStudentCode())){
            return new EntityResponse<>(EnumStatusResponse.WARNING,EntityMessage.EXIST, null);
        }
        StudentInfoEntity studentInfoModel = studentInfoMapper.modelMapToEntity(model.getStudentInfoModel());
        StudentModel studentModel = new StudentModel();
        studentModel.setStudentCode(model.getStudentCode());
        studentModel.setStudentName(model.getStudentName());
        studentInfoModel.setStudentEntity(studentRepository.save(studentMapper.modelMapToEntity(studentModel)));
        studentInfoRepository.save(studentInfoModel);
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.CREATE_SUCCESS, model);
    }

    @Override
    @Transactional
    public EntityResponse<StudentModel> delete(Integer id) {
        if (findById(id) != null)
            return new EntityResponse<>(EnumStatusResponse.SUCCESS,EntityMessage.DELETE_SUCCESS, null);
        return new EntityResponse<>(EnumStatusResponse.WARNING,EntityMessage.NOT_FOUND, null);
    }

    @Override
    public Boolean existStudent(String code) {
        Optional<StudentEntity> studentModel = studentRepository.findByStudentCode(code);
        return studentModel.isPresent();
    }

    @Override
    public EntityPageNumber<StudentModel> getAllOfPageNumber(Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1,10);
        Page<StudentEntity> pageResult =studentRepository.findAll(pageRequest);
        return (new EntityPageNumber<>(pageResult.toList().stream().map(studentMapper::entityMapToModel).
                collect(Collectors.toList()), pageResult.getTotalElements(),pageResult.getTotalPages()));
    }

    @Override
    @Transactional
    public EntityResponse<StudentModel> updateStudent(StudentModel model) {
        Optional<StudentEntity> studentModel = studentRepository.findById(model.getStudentId());
        if (studentModel.isPresent()){
            StudentEntity newStudentUpdate = studentModel.get();
            StudentInfoEntity newStudentInfoUpdate = newStudentUpdate.getStudentInfoEntity();
            if (model.getStudentName() != null && !model.getStudentName().equals(newStudentUpdate.getStudentName())){
                newStudentUpdate.setStudentName(model.getStudentName());
            }
            if (model.getStudentInfoModel().getAddress() != null){
                newStudentInfoUpdate.setAddress(model.getStudentInfoModel().getAddress());
            }
            if (model.getStudentInfoModel().getAverageScore() != null){
                newStudentInfoUpdate.setAverageScore(model.getStudentInfoModel().getAverageScore());
            }
            if (model.getStudentInfoModel().getDateOfBirth() != null){
                newStudentInfoUpdate.setDateOfBirth(model.getStudentInfoModel().getDateOfBirth());
            }
            newStudentUpdate.setStudentInfoEntity(newStudentInfoUpdate);
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.UPDATE_SUCCESS,
                    studentMapper.entityMapToModel(studentRepository.save(newStudentUpdate)));
        }
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.UPDATE_FAIL,
                null);
    }
}
