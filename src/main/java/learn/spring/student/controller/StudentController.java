package learn.spring.student.controller;

import jakarta.validation.Valid;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.constants.EnumStatusResponse;
import learn.spring.student.models.StudentModel;
import learn.spring.student.services.impl.StudentServiceImpl;
import learn.spring.student.utils.GetMessageError;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    @PostMapping(value = "/students")
    public EntityResponse<StudentModel> postMethodName(@Valid @RequestBody StudentModel student,
                                                       BindingResult bindingResult) {
        String message = GetMessageError.getMessageError(bindingResult);
        if (message != null) {
            return new EntityResponse<>(EnumStatusResponse.WARNING, message,null);
        }
        try {
            return studentServiceImpl.create(student);
        }catch (Exception exception) {
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.SERVER_ERROR, null);
        }
    }

    @GetMapping(value = "/students/{page}")
    public EntityResponse<?> updateStudent(@PathVariable("page") Integer page) {
        if (page == null)
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                    studentServiceImpl.findAll());
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                studentServiceImpl.getAllOfPageNumber(page));
    }

    @PutMapping(value = "/students")
    public EntityResponse<StudentModel> getAllStudent(@RequestBody StudentModel studentModel){
        if (studentServiceImpl.findById(studentModel.getStudentId()) == null){
            return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.GET_DATA_SUCCESS, null);
        }
        try{
            return studentServiceImpl.updateStudent(studentModel);
        }catch (Exception e){
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.SERVER_ERROR, null);
        }
    }

    @DeleteMapping(value = "/students/{id}")
    public  EntityResponse<StudentModel> deleteStudent(@PathVariable("id") Integer id){
        if (studentServiceImpl.findById(id)!= null){
            return studentServiceImpl.delete(id);
        }else {
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.DELETE_FAIL, null);
        }
    }
}
