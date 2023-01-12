package learn.spring.student.services;

import learn.spring.student.common.EntityPageNumber;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.models.StudentModel;

public interface StudentService extends Service<StudentModel> {
    Boolean existStudent(String code);

    EntityPageNumber<StudentModel> getAllOfPageNumber(Integer page);

    EntityResponse<StudentModel> updateStudent(StudentModel model);
}
