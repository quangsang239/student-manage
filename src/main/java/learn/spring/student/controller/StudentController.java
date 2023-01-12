package learn.spring.student.controller;

import jakarta.validation.Valid;
import learn.spring.student.common.EntityMessage;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.common.EnumStatusResponse;
import learn.spring.student.models.StudentModel;
import learn.spring.student.services.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    @PostMapping(value = "/create-student")
    @ResponseBody
    public EntityResponse<StudentModel> postMethodName(@Valid @RequestBody StudentModel student,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> listError;
            StringBuilder message = new StringBuilder();
            listError = bindingResult.getAllErrors();
            for (var error : listError) {
                message.append(error.getDefaultMessage());
            }
            return new EntityResponse<>(EnumStatusResponse.WARNING, message.toString(), null);
        }
        try {
            return studentServiceImpl.create(student);
        }catch (Exception exception) {
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.serverError, null);
        }
    }

    @GetMapping(value = "/get-all-student/{page}")
    @ResponseBody
    public EntityResponse<?> updateStudent(@PathVariable("page") Integer page) {
        if (page == null)
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.getDataSuccess,
                    studentServiceImpl.findAll());
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.getDataSuccess,
                studentServiceImpl.getAllOfPageNumber(page));
    }

    @PutMapping(value = "/update-student")
    @ResponseBody
    public EntityResponse<StudentModel> getAllStudent(@RequestBody StudentModel studentModel){
        if (studentServiceImpl.findById(studentModel.getStudentId()) == null){
            return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.getDataSuccess, null);
        }
        try{
            return studentServiceImpl.updateStudent(studentModel);
        }catch (Exception e){
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.serverError, null);
        }
    }

    @DeleteMapping(value = "/delete-student/{id}")
    @ResponseBody
    public  EntityResponse<StudentModel> deleteStudent(@PathVariable("id") Integer id){
        if (studentServiceImpl.findById(id)!= null){
            return studentServiceImpl.delete(id);
        }else {
            return new EntityResponse<>(EnumStatusResponse.ERROR, EntityMessage.deleteFail, null);
        }
    }
}
