package Interfaces;

import Entities.SponsorType;
import javafx.collections.ObservableList;

public interface ISponsorType {

    public boolean ajouterSponsorType(SponsorType c);

    public ObservableList<SponsorType> GetAll();

    public boolean modifierSponsorType(SponsorType c);

    public boolean supprimerSponsorType(SponsorType c);

}
