package ConnexionBd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import gestion_salles.*;

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
    
    public Salle getInfosSalle(String numeroSalle, String nomBatiment) throws SQLException {
        String query = "SELECT * FROM salles WHERE numeroSalle = ? AND nomBatiment = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, numeroSalle);
            pstmt.setString(2, nomBatiment);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int numeroEtage = rs.getInt("numeroEtage");

                    Batiment batiment = new Batiment(nomBatiment);
                    Etage etage = new Etage(numeroEtage);
                    return new Salle(numeroSalle, batiment, etage);
                }
            }
        }
        return null; 
    }
    
    public List<Salle> getToutesLesSalles() throws SQLException {
        List<Salle> listeSalles = new ArrayList<>();
        String query = "SELECT * FROM salles";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String numeroSalle = rs.getString("numeroSalle");
                String nomBatiment = rs.getString("nomBatiment");
                int numeroEtage = rs.getInt("numeroEtage");

                Batiment batiment = new Batiment(nomBatiment);
                Etage etage = new Etage(numeroEtage);
                Salle salle = new Salle(numeroSalle, batiment, etage);
                listeSalles.add(salle);
            }
        }
        return listeSalles;
    }
    
    public void modifierSalle(String numeroOriginal, String nouveauNumero, String nouveauNomBatiment, int nouveauNumeroEtage, String nomBatimentActuel, int numeroEtageActuel) throws SQLException {
        String query = "UPDATE salles SET numeroSalle = ?, nomBatiment = ?, numeroEtage = ? WHERE numeroSalle = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nouveauNumero.isEmpty() ? numeroOriginal : nouveauNumero);
            pstmt.setString(2, nouveauNomBatiment.isEmpty() ? nomBatimentActuel : nouveauNomBatiment);
            pstmt.setInt(3, nouveauNumeroEtage < 0 ? numeroEtageActuel : nouveauNumeroEtage);
            pstmt.setString(4, numeroOriginal);
            pstmt.executeUpdate();
        }
    }

    public void supprimerSalle(String numeroSalle, String nomBatiment) throws SQLException {
        String query = "DELETE FROM salles WHERE numeroSalle = ? AND nomBatiment = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, numeroSalle);
            pstmt.setString(2, nomBatiment);
            pstmt.executeUpdate();
        }
    }

}