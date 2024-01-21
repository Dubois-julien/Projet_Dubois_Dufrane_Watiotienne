package ConnexionBd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
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
    
    public int getIdSalle(String numeroSalle, String nomBatiment) throws SQLException {
        int idSalle = -1; 
        
        String query = "SELECT id_salle FROM salles WHERE numeroSalle = ? AND nomBatiment = ?";
        
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, numeroSalle);
            pstmt.setString(2, nomBatiment);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idSalle = rs.getInt("id_salle"); 
                }
            }
        }
        
        return idSalle; 
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
    
    public void creerTableReservations() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS reservations (" +
                       "id_reservation INT AUTO_INCREMENT PRIMARY KEY," +
                       "id_salle INT," + 
                       "date DATE," + 
                       "heure TIME," + 
                       "promo VARCHAR(50)," + 
                       "responsable VARCHAR(100)," + 
                       "FOREIGN KEY (id_salle) REFERENCES salles(id_salle))"; 
        try (Connection conn = ConnectBd.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("La table 'reservations' a été créée ou existe déjà.");
        }
    }

    public void faireReservation(int idSalle, String date, String heure, String promo, String responsable) throws SQLException {
        String query = "INSERT INTO reservations (id_salle, date, heure, promo, responsable) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idSalle);
            pstmt.setString(2, date);
            pstmt.setString(3, heure);
            pstmt.setString(4, promo);
            pstmt.setString(5, responsable);
            pstmt.executeUpdate();
        }
    }

    public boolean SalleDisponible(int idSalle, String date, String heure) throws SQLException {
        LocalTime heureDemandee = LocalTime.parse(heure);
        
        if (heureDemandee.getMinute() != 0 && heureDemandee.getMinute() != 30) {
            System.out.println("Les réservations ne peuvent se faire que par tranche de 30 minutes (HH:00 ou HH:30).");
            return false;
        }
        
        LocalTime heureAvant = heureDemandee.minusMinutes(30);
        LocalTime heureApres = heureDemandee.plusMinutes(30);
        LocalTime heureDebut = LocalTime.of(8, 0);
        LocalTime heureFin = LocalTime.of(18, 0);
        
        if (heureDemandee.isBefore(heureDebut) || heureDemandee.isAfter(heureFin)) {
            System.out.println("Les réservations ne sont autorisées qu'entre 8h00 et 18h00.");
            return false;
        }
        String query = "SELECT COUNT(*) FROM reservations WHERE id_salle = ? AND date = ? " +
                       "AND (heure BETWEEN ? AND ?)";
        
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idSalle);
            pstmt.setString(2, date);
            pstmt.setString(3, heureAvant.toString());
            pstmt.setString(4, heureApres.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("La salle n'est pas disponible à l'heure indiquée, veuillez vérifier les créneaux disponibles.");
                    return false;
                }
            }
        }
        return true; 
    }
    
    public List<Reservation> getReservations(String date, String heureDebut, String heureFin, String numeroSalle, String nomBatiment) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.id_reservation, r.id_salle, r.date, r.heure, r.promo, r.responsable, s.numeroSalle, s.nomBatiment " +
                       "FROM reservations r " +
                       "JOIN salles s ON r.id_salle = s.id_salle " +
                       "WHERE r.date = ? AND r.heure BETWEEN ? AND ? AND s.numeroSalle= ? AND s.nomBatiment = ?";

        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, date);
            pstmt.setString(2, heureDebut);
            pstmt.setString(3, heureFin);
            pstmt.setString(4, numeroSalle);
            pstmt.setString(5, nomBatiment);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                        rs.getInt("id_reservation"), 
                        rs.getString("numeroSalle"), 
                        rs.getString("nomBatiment"), 
                        rs.getString("date"), 
                        rs.getString("heure"),
                        rs.getString("promo"),
                        rs.getString("responsable")
                    );
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<String> getCreneauxLibres(String date, String heureDebut, String heureFin, String numeroSalle, String nomBatiment) throws SQLException {
        List<String> creneauxLibres = new ArrayList<>();
        LocalTime debut = LocalTime.parse(heureDebut);
        LocalTime fin = LocalTime.parse(heureFin);

        while (debut.isBefore(fin)) {
            creneauxLibres.add(debut.toString());
            debut = debut.plusMinutes(30);
        }

        String query = "SELECT heure FROM reservations r " +
                "JOIN salles s ON r.id_salle = s.id_salle " +
                "WHERE r.date = ? AND r.heure BETWEEN ? AND ? AND s.numeroSalle = ? AND s.nomBatiment = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (String creneau : new ArrayList<>(creneauxLibres)) {
                LocalTime heureCreneau = LocalTime.parse(creneau);
                LocalTime demiHeureAvant = heureCreneau.minusMinutes(30);
                LocalTime demiHeureApres = heureCreneau.plusMinutes(30);
                
                pstmt.setString(1, date);
                pstmt.setString(2, demiHeureAvant.toString());
                pstmt.setString(3, demiHeureApres.toString());
                pstmt.setString(4, numeroSalle);
                pstmt.setString(5, nomBatiment);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        creneauxLibres.remove(creneau);
                    }
                }
            }
        }
        return creneauxLibres;
    }


    public Reservation getReservation(String date, String heure, String numeroSalle, String nomBatiment) throws SQLException {
        String query = "SELECT r.id_reservation, r.id_salle, r.date, r.heure, r.promo, r.responsable, s.numeroSalle, s.nomBatiment " +
                       "FROM reservations r " +
                       "JOIN salles s ON r.id_salle = s.id_salle " +
                       "WHERE r.date = ? AND r.heure = ? AND s.numeroSalle = ? AND nomBatiment= ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, date);
            pstmt.setString(2, heure);
            pstmt.setString(3, numeroSalle);
            pstmt.setString(4, nomBatiment);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                        rs.getInt("id_reservation"), 
                        rs.getString("numeroSalle"),
                        rs.getString("nomBatiment"),
                        rs.getString("date"), 
                        rs.getString("heure"),
                        rs.getString("promo"),
                        rs.getString("responsable")
                    );
                }
            }
        }
        return null; 
    }

}