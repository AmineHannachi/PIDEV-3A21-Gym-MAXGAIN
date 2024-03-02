package tn.esprit.repositories;

import java.util.List;

public interface IService<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    T recherche(String id);
    List<T> readAll();
}
