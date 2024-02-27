package Interfaces;

import Entities.EvenementType;

import java.util.List;

public interface IEvenementType {
    public boolean ajouterEvenementType(EvenementType c);

    public List<EvenementType> GetAll();

    public boolean modifierEvenementType(EvenementType c);

    public boolean supprimerEvenementType(int c);

}
