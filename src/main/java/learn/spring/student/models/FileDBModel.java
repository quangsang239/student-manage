package learn.spring.student.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDBModel {
    private Integer id;
    private String name;
    private String type;
    private byte[] data;
}
