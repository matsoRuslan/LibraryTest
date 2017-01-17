package dao;


import java.util.List;

public interface BookDao<T, Id> {
    void persist(T entity);

    void update(T entity);

    T findById(Id id);

    void delete(T entity);

    List<T> findAll();

    void deleteAll();

    List<T> findByTitle(String title);

    List<T> findAllOrdered();
}
