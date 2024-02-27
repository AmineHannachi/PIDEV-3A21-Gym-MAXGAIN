package Interfaces;

import Entities.Sponsor;
import javafx.collections.ObservableList;

public interface ISponsor {
    public boolean ajouterSponsor(Sponsor c);

    public ObservableList<Sponsor> GetAll();

    public boolean modifierSponsor(Sponsor c);

    public boolean supprimerSponsor(Sponsor c);

}
