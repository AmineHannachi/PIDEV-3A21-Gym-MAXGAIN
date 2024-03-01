package tn.esprit.services;

import tn.esprit.entities.Salle;
import tn.esprit.entities.User;

import java.util.List;

public interface IService<T>{
    void add(T t);
    void delete(int id);
    void update(int id_Modif, T t);
    void recherche(T t);
    List<T> readAll();
    T readByID(int id);
    void Update( T t);


    int obtenirIdSalle(String nomSalle);

    String obtenirNomSalle(int idSalle);

}



