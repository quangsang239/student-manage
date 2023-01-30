package learn.spring.student.services.impl;

import learn.spring.student.constants.EntityMessage;
import learn.spring.student.entities.FileDBEntity;
import learn.spring.student.exception.CreateFileException;
import learn.spring.student.models.FileDBResponseModel;
import learn.spring.student.repositories.FileDBRepository;
import learn.spring.student.services.FileDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileDBServiceImpl implements FileDBService {
    private final FileDBRepository fileDBRepository;

    @Override
    public FileDBEntity store(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CreateFileException(EntityMessage.CREATE_FILE_FAIL);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileDBEntity file = new FileDBEntity();
        try {
            file.setName(fileName);
            file.setType(multipartFile.getContentType());
            file.setData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileDBRepository.save(file);
    }

    @Override
    public FileDBEntity downloadFile(Integer id) {
        Optional<FileDBEntity> fileDBEntity =fileDBRepository.findById(id);

        if (fileDBEntity.isPresent()){
            return fileDBEntity.get();
        }else throw new CreateFileException(EntityMessage.NOT_FOUND);
    }


    @Override
    public List<FileDBResponseModel> findAll() {
        List<FileDBEntity> fileDBEntityList = fileDBRepository.findAll();
        return fileDBEntityList.stream().map(dbFile -> {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/files/download/").path(dbFile.getId().toString()).toUriString();
            return new FileDBResponseModel(dbFile.getName(), fileDownloadURL, dbFile.getType(),
                    (long) dbFile.getData().length);
        }).collect(Collectors.toList());
    }

    @Override
    public FileDBResponseModel findById(Integer id) {
        Optional<FileDBEntity> fileDBEntity = fileDBRepository.findById(id);
        if (fileDBEntity.isPresent()) {
            String fileDownloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/files/download/").path(fileDBEntity.get().getId().toString()).toUriString();

            return new FileDBResponseModel(fileDBEntity.get().getName(),
                    fileDownloadURL, fileDBEntity.get().getType(), (long) fileDBEntity.get().getData().length);
        }else throw new CreateFileException(EntityMessage.NOT_FOUND);
    }

    @Override
    public FileDBResponseModel create(FileDBResponseModel model) {
        return null;
    }

    @Override
    public FileDBResponseModel delete(Integer id) {
        return null;
    }
}
