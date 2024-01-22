package ConnexionBd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Classe ConnectBd pour établir une connexion avec une base de données MySQL.
 * Elle contient les configurations pour se connecter à la base de données et fournir une méthode pour obtenir cette connexion
 */
public class ConnectBd {
 //URL
 private static final String URL = "jdbc:mysql://localhost:3305/Java";
// Nom d'utilisateur 
 private static final String USER = "root";
// Mot de passe pour la base de données
 private static final String PASSWORD = "mdproot";


 public static Connection getConnection() throws SQLException {
     return DriverManager.getConnection(URL, USER, PASSWORD);
 }
}
