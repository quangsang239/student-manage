package learn.spring.student.service.impl;

import learn.spring.student.model.StudentInfoModel;
import learn.spring.student.repositories.StudentInfoRepository;
import learn.spring.student.service.StudentInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {
    final
    StudentInfoRepository studentInfoRepository;

    public StudentInfoServiceImpl(StudentInfoRepository studentInfoRepository) {
        this.studentInfoRepository = studentInfoRepository;
    }

    @Override
    public List<StudentInfoModel> findAll() {

        return this.studentInfoRepository.findAll();
    }

    @Override
    public StudentInfoModel findById(Integer id) {
        Optional<StudentInfoModel> studentInfo = this.studentInfoRepository.findById(id);
        return studentInfo.orElse(null);
    }

    @Override
    public void create(StudentInfoModel model) {
        this.studentInfoRepository.save(model);
    }

    @Override
    public void delete(Integer id) {
        this.studentInfoRepository.deleteById(id);
    }
}
