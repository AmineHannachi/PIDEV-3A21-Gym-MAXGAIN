package Interfaces;

import Entities.Evenement;

import java.util.List;

public interface IEvenement {
    public boolean ajouterEvenement(Evenement c);

    public List<Evenement> GetAll();

    public boolean modifierEvenement(Evenement c);

    public boolean supprimerEvenement(int c);
}
