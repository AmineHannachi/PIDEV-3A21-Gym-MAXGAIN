package tn.esprit.test;

import tn.esprit.entities.*;
import tn.esprit.services.CoursService;
import tn.esprit.services.SalleService;
import tn.esprit.services.TerrainService;
import tn.esprit.services.UserService;
import tn.esprit.utilis.DataSource;


import java.sql.SQLException;
import java.util.List;

import static org.controlsfx.control.WorldMapView.Country.MG;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws SQLException {
        DataSource ds1 = DataSource.getInstance();
        User u1 = new User("SmatiHadil", "hadil", "smati", 23, "hadilsmati99@gmail.com", "1234", 54034540, Role.ADMIN);
        //User u2=new User("khalfions","ons","khalfi",50,"chihidorsaf99@gmail.com" ,"1234" ,54034540, Role.CLIENT);
        //User u3=new User("wesltimariem","mariem","wesleti",30,"chihidorsaf99@gmail.com" ,"1234" ,54034540, Role.CLIENT);
        UserService ps = new UserService();
        //ps.add(u1);
        // ps.add(u2);
        // ps.add(u3);
        // ps.delete(3);
        //ps.delete(7);
        //ps.delete(6);

        //u2.setUsername("youssef");
        //ps.update(1,u2);
        //List<User> users = ps.readAll();

        // Afficher les utilisateurs récupérés
        //for (User user : users) {
        //  System.out.println(user);
        //}

        //int idUser = 3; // Remplacez 1 par l'ID de l'utilisateur que vous souhaitez récupérer
        //User user = ps.readByID(idUser);

        // Vérifier si l'utilisateur a été récupéré avec succès
        //if (user != null) {
        //  System.out.println("Utilisateur récupéré : " + user);
        //} else {
        //  System.out.println("Aucun utilisateur trouvé avec l'ID : " + idUser);
        //}
        // ps.addPreS(u3);
        Salle s1 = new Salle("lac1", "MG lac1", "salle", "C:\\Users\\Hadil\\Desktop\\photo pidev\\photosalle");
        Salle s2 = new Salle("soukra", "MG soukra", "gym", "C:\\Users\\Hadil\\Desktop\\photo pidev\\photosalle");
        Salle s3 = new Salle("Boumhal", "MG Boumhal", "sallesport", "C:\\Users\\Hadil\\Desktop\\photo pidev\\photosalle");
        SalleService ss = new SalleService();

        //ss.add(s2);
        Terrain p1=new Terrain ("Olympysky Club","Les Berges du lac",12,"10-02-2024","C:\\Users\\User\\terrain.jpg");
        Terrain p2=new Terrain ("Terrain Lake Club","Lac 2",11,"14-02-2024","C:\\Users\\User\\terrain.jpg");
        Terrain p3=new Terrain ("Club des Princes","La Soukra",6,"13-02-2024","C:\\Users\\User\\terrain.jpg");

        TerrainService pt=new TerrainService();
        pt.add(p1);
        pt.add(p2);
        pt.add(p3);



          ss.obtenirIdSalle("MG bardo");

        System.out.println("--------------------------");

        System.out.println(new CoursService().readAll2());

        System.out.println("*****");

        System.out.println(new SalleService().obtenirNomSalle(1));
    }
}