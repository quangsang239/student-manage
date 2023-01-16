package learn.spring.student.maps;

import org.mapstruct.Mapper;

@Mapper
public interface MapsInterfaceData<E, M> {
    E modelMapToEntity(M model);

    M entityMapToModel(E entity);
}
