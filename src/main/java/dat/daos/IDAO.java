package dat.daos;

import java.util.List;

public interface IDAO<T, I> {
    T save(T T);
    T read (I i);
    List<T> readAll ();
    T update (T t);
    void delete (I i);
}