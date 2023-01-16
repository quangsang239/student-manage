package learn.spring.student.services;

import learn.spring.student.common.EntityResponse;
import learn.spring.student.entities.FileDBEntity;
import learn.spring.student.models.FileDBResponseModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;

public interface FileDBService extends Service<FileDBResponseModel> {
    EntityResponse<FileDBEntity> store(MultipartFile multipartFile);

    FileDBEntity downloadFile(Integer id) throws IOException;
}
