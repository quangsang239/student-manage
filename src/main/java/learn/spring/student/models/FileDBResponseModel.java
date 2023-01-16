package learn.spring.student.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDBResponseModel {
    private String name;
    private String url;
    private String type;
    private Long size;
}
