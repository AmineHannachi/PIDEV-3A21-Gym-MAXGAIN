package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource {  private String url="jdbc:mysql://localhost:3306/MaxGain";
    private String login="root";
    private String pwd="";

    private Connection cnx;

    private static DataSource instance;

    public DataSource(){
        try {
            cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("Connected !!");
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getCnx(){
        return cnx;
    }
}
