package tn.esprit.services;

import javafx.collections.ObservableList;
import tn.esprit.entities.Salle;
import tn.esprit.entities.Terrain;
import tn.esprit.entities.User;

import java.util.List;

public interface IService<T>{
    void add(T t);
    void delete(int id);

    void update( int id_modif,T t);

    void Update( T t);
    void recherche(T t);
    List<T> readAll();
    T readByID(int id);


}



