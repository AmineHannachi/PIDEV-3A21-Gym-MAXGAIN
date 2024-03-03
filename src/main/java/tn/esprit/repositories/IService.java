package tn.esprit.repositories;

import tn.esprit.entities.User;

import java.util.List;

public interface IService<T> {
    void add(T t);
    void delete(T t);
    void update(String oldUsername, User newUser);
    T recherche(T t);
    List<T> readAll();
}
