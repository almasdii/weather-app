package testingSpring.dao;

import java.util.Optional;

public interface Dao<E,K> {
    E save(E entity);
    Optional<E> findById(K id);
    void remove(K id);
}
