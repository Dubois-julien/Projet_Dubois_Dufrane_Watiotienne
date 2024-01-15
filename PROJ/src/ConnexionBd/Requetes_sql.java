package ConnexionBd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import gestion_salles.Salle;

public class Requetes_sql {

    public void ajouterSalle(Salle salle) throws SQLException {
        String query = "INSERT INTO salles (numeroSalle, nomBatiment, numeroEtage) VALUES (?, ?, ?)";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, salle.getNumeroSalle());
            pstmt.setString(2, salle.getBatiment().getNom());
            pstmt.setInt(3, salle.getEtage().getNumeroEtage());
            pstmt.executeUpdate();
        }
    }

    public void creerTableSalles() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS salles (" +
                       "id_salle INT AUTO_INCREMENT PRIMARY KEY," +
                       "numeroSalle VARCHAR(10)," +
                       "nomBatiment VARCHAR(50)," +
                       "numeroEtage INT)";
        try (Connection conn = ConnectBd.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("La table 'salles' a été créée ou existe déjà.");
        }
    }

}