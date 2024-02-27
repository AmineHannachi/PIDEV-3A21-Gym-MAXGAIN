/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Pass;
import javafx.collections.ObservableList;

/**
 *
 * @author Dali
 */
public interface iPass {
    
     public boolean ajouterPass(Pass c);

    public ObservableList<Pass> GetAll();

    //public boolean modifierPass(Pass c);

    //public boolean supprimerPass(Pass c);
    
    
}
