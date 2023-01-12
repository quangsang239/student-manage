package learn.spring.student.maps;

public interface MapsInterfaceData<E, M> {
    E modelMapToEntity(M model);

    M entityMapToModel(E entity);
}
