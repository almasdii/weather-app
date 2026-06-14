package testingSpring.dao;

public interface Dao<K,E> {
    E save(E user);
    E find(K uuid);
    boolean update(E user);
    boolean delete(K uuid);
}
