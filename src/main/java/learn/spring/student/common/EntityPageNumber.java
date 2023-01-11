package learn.spring.student.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityPageNumber<T> {
    List<T> listModel;
    Long totalElement;
    Integer totalPage;
}
