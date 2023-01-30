package learn.spring.student.services;

import learn.spring.student.entities.FileDBEntity;
import learn.spring.student.models.FileDBResponseModel;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileDBService extends Service<FileDBResponseModel> {
    FileDBEntity store(MultipartFile multipartFile);

    FileDBEntity downloadFile(Integer id) throws IOException;
}
