package ConnexionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBd {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3305/Java"; 
        String user = "root"; 
        String password = "mdproot"; 

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connecté à la base de données !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
