package learn.spring.student.controller;

import learn.spring.student.common.EntityResponse;
import learn.spring.student.constants.EntityMessage;
import learn.spring.student.constants.EnumStatusResponse;
import learn.spring.student.constants.FileAcceptUpload;
import learn.spring.student.entities.FileDBEntity;
import learn.spring.student.exception.CreateFileException;
import learn.spring.student.exception.FileUploadNotSupport;
import learn.spring.student.models.FileDBResponseModel;
import learn.spring.student.services.impl.FileDBServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class FileDBController {
    private final FileDBServiceImpl fileDBService;

    @PostMapping(value = "/files")
    public EntityResponse<FileDBEntity> createFile(@RequestParam("file") MultipartFile multipartFile) {
        String typeFile = multipartFile.getContentType();
        if (!multipartFile.isEmpty() && typeFile != null) {
            if (typeFile.matches(FileAcceptUpload.DOC) || typeFile.matches(FileAcceptUpload.EXCEL)
            || typeFile.matches(FileAcceptUpload.PDF)){
                try {
                    return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.CREATE_FILE_SUCCESS,
                            fileDBService.store(multipartFile));
                } catch (Exception e) {
                    throw new CreateFileException(EntityMessage.CREATE_FILE_FAIL);
                }
            }else throw new FileUploadNotSupport(EntityMessage.FILE_TYPE_NOT_SUPPORT);
        }
        return new EntityResponse<>(EnumStatusResponse.WARNING, EntityMessage.FILE_NULL, null);
    }

    @GetMapping(value = "/files/{id}")
    public EntityResponse<FileDBResponseModel> getFileById(@PathVariable("id") Integer id) {
        try {
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                    fileDBService.findById(id));
        } catch (Exception e) {
            throw new CreateFileException(EntityMessage.NOT_FOUND);
        }
    }

    @GetMapping(value = "/files")
    public EntityResponse<List<FileDBResponseModel>> getAllFiles() {
        try {
            return new EntityResponse<>(EnumStatusResponse.SUCCESS, EntityMessage.GET_DATA_SUCCESS,
                    fileDBService.findAll());
        } catch (Exception e) {
            throw new CreateFileException(EntityMessage.NOT_FOUND);
        }
    }

    @GetMapping(value = "/files/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Integer id) {
        if (id == null) throw new CreateFileException(EntityMessage.ID_FIND_IS_NULL);
        try {
            FileDBEntity fileDBEntity = fileDBService.downloadFile(id);
            if (fileDBEntity != null) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDBEntity.getId().toString() + "\"")
                        .body(fileDBEntity.getData());
            }
            throw new CreateFileException(EntityMessage.NOT_FOUND);
        } catch (Exception e) {
            throw new CreateFileException(EntityMessage.SERVER_ERROR);
        }

    }
}
