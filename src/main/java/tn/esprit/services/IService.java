package tn.esprit.services;

import tn.esprit.entities.Salle;
import tn.esprit.entities.User;

import java.util.List;

public interface IService<T>{
    void add(T t);
    void delete(String username);
    void update(String username, User nouvelle_valeurs);
    void recherche(T t);
    List<T> readAll();

}



