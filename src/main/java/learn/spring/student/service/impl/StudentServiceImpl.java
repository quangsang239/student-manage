package learn.spring.student.service.impl;

import jakarta.transaction.Transactional;
import learn.spring.student.common.EntityPageNumber;
import learn.spring.student.model.StudentInfoModel;
import learn.spring.student.model.StudentModel;
import learn.spring.student.repositories.StudentInfoRepository;
import learn.spring.student.repositories.StudentRepository;
import learn.spring.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentInfoRepository studentInfoRepository;

    @Override
    public List<StudentModel> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentModel findById(Integer id) {
        Optional<StudentModel> sOptional = studentRepository.findById(id);
        return sOptional.orElse(null);
    }

    @Override
    @Transactional
    public void create(StudentModel model) {
        StudentInfoModel studentInfoModel = model.getStudentInfoModel();
        StudentModel studentModel = new StudentModel();
        studentModel.setStudentCode(model.getStudentCode());
        studentModel.setStudentName(model.getStudentName());
        studentInfoModel.setStudentModel(studentRepository.save(studentModel));
        studentInfoRepository.save(studentInfoModel);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Boolean existStudent(String code) {
        Optional<StudentModel> studentModel = studentRepository.findByStudentCode(code);
        return studentModel.isPresent();
    }

    @Override
    public EntityPageNumber<StudentModel> getAllOfPageNumber(Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1,10);
        Page<StudentModel> pageResult =studentRepository.findAll(pageRequest);
        return (new EntityPageNumber<>(pageResult.toList(), pageResult.getTotalElements(),pageResult.getTotalPages()));
    }

    @Override
    @Transactional
    public StudentModel updateStudent(StudentModel model) {
        Optional<StudentModel> studentModel = studentRepository.findById(model.getStudentId());
        if (studentModel.isPresent()){
            StudentModel newStudentUpdate = studentModel.get();
            StudentInfoModel newStudentInfoUpdate = newStudentUpdate.getStudentInfoModel();
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
            newStudentUpdate.setStudentInfoModel(newStudentInfoUpdate);
            return studentRepository.save(newStudentUpdate);
        }
        return null;
    }
}
