package learn.spring.student.service;

import learn.spring.student.common.EntityPageNumber;
import learn.spring.student.model.StudentModel;

public interface StudentService extends Service<StudentModel> {
    Boolean existStudent(String code);

    EntityPageNumber<StudentModel> getAllOfPageNumber(Integer page);

    StudentModel updateStudent(StudentModel model);
}
