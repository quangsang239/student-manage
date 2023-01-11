package learn.spring.student.controller;

import jakarta.validation.Valid;
import learn.spring.student.common.EntityResponse;
import learn.spring.student.common.EnumStatusResponse;
import learn.spring.student.model.StudentModel;
import learn.spring.student.service.impl.StudentServiceImpl;
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
        if (studentServiceImpl.existStudent(student.getStudentCode()))
            return new EntityResponse<>(EnumStatusResponse.WARNING, "Student code is exist!", null);
        try {
            studentServiceImpl.create(student);
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Create user successful!", null);
        }catch (Exception exception) {
            return new EntityResponse<>(EnumStatusResponse.ERROR, "Server error!", null);
        }
    }

    @GetMapping(value = "/get-all-student/page={page}")
    @ResponseBody
    public EntityResponse<?> getAllStudent(@PathVariable("page") Integer page) {
        if (page == null)
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Get data success!",
                    studentServiceImpl.findAll());
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Get Data success!",
                studentServiceImpl.getAllOfPageNumber(page));
    }

    @PutMapping(value = "/update-student")
    @ResponseBody
    public EntityResponse<StudentModel> getAllStudent(@RequestBody StudentModel studentModel){
        if (studentServiceImpl.findById(studentModel.getStudentId()) == null){
            return new EntityResponse<>(EnumStatusResponse.WARNING, "Student not found!", null);
        }
        StudentModel studentUpdated = studentServiceImpl.updateStudent(studentModel);
        if (studentUpdated == null){
            return new EntityResponse<>(EnumStatusResponse.ERROR, "Server error!", null);
        }
        return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Update student success!", studentUpdated);
    }

    @DeleteMapping(value = "/delete-student/{id}")
    @ResponseBody
    public  EntityResponse<StudentModel> deleteStudent(@PathVariable("id") Integer id){
        if (studentServiceImpl.findById(id)!= null){
            studentServiceImpl.delete(id);
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, "Delete student success!", null);
        }else {
            return new EntityResponse<>(EnumStatusResponse.ERROR, "Not found student!", null);
        }

    }
}
