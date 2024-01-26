package ConnexionBd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.*;
import gestion_salles.*;


/**
 * Classe Requetes_sql pour effectuer les opérations sur la base de données
 */
public class Requetes_sql {

	/**
	 * Ajoute une salle à la base de données
	 * @param salle L'objet Salle à ajouter
	 * @throws SQLException si la salle existe déjà
	 */
    public void ajouterSalle(Salle salle) throws SQLException {
        String query = "INSERT INTO salles (numeroSalle, nomBatiment, numeroEtage) VALUES (?, ?, ?)";
        if (salleExiste(salle)) {
            throw new SQLException("La salle avec le numéro " + salle.getNumeroSalle() + 
                                   " dans le bâtiment " + salle.getBatiment().getNom() + 
                                   " existe déjà.");
        }
        
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, salle.getNumeroSalle());
            pstmt.setString(2, salle.getBatiment().getNom());
            pstmt.setInt(3, salle.getEtage().getNumeroEtage());
            pstmt.executeUpdate();
        }
    }
    /**
     * Verifie si une salle existe déjà dans la base de données
     * @param salle 
     * @return true si la salle existe, false sinon
     */
    public boolean salleExiste(Salle salle) throws SQLException {
        String query = "SELECT COUNT(*) FROM salles WHERE numeroSalle = ? AND nomBatiment = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, salle.getNumeroSalle());
            pstmt.setString(2, salle.getBatiment().getNom());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    /**
     * Création de la table 'salles' dans la base de données si elle n'existe pas.
     */
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
    /**
     * Afficher toutes les reservations en parcourant la table reservation est affiche son contenu
     */
    public static void afficherAllReservation() {
        String query = "SELECT r.id_reservation, s.numeroSalle, s.nomBatiment, r.date, r.heure, r.promo, r.responsable " +
                       "FROM reservations r " +
                       "INNER JOIN salles s ON r.id_salle = s.id_salle";

        try (Connection conn = ConnectBd.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idReservation = rs.getInt("id_reservation");
                String numeroSalle = rs.getString("numeroSalle");
                String nomBatiment = rs.getString("nomBatiment");
                String date = rs.getString("date");
                String heureDebut = rs.getString("heure");
                String promo = rs.getString("promo");
                String responsable = rs.getString("responsable");

                System.out.println("ID de la réservation : " + idReservation);
                System.out.println("Numéro de salle : " + numeroSalle);
                System.out.println("Nom du bâtiment : " + nomBatiment);
                System.out.println("Date : " + date);
                System.out.println("Heure de début : " + heureDebut);
                System.out.println("Promo : " + promo);
                System.out.println("Responsable : " + responsable);
                System.out.println("-----------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }


    /**
     * Obtenir les informations d'une salle spécifique
     * @param numeroSalle le numero de la salle
     * @param nomBatiment le nom du batiment 
     * @return L'objet Salle correspondant ou null si la salle n'existe pas 
     */
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

    /**
     * Recupere la liste de toutes les salles de la base de données.
     * @return Une liste d'objets Salle.
     */
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
    /**
     * Recupere l'ID d'une salle spécifique dans la base de données.
     * @param numeroSalle le numero de la salle
     * @param nomBatiment le nom du batiement
     * @return L'ID de la salle, ou -1 si elle n'existe pas.
     */
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
    /**
     * Modifie les informations d'une salle existante dans la base de données.
     * @param numeroOriginal le numero actuel de la salle
     * @param nouveauNumero le nouveau numéro de la salle, ou vide pour ne pas changer
     * @param nouveauNomBatiment le nouveau nom du batiment, ou vide pour ne pas changer
     * @param nouveauNumeroEtage le nouveau numéro d'étage, ou -1 pour ne pas changer
     * @param nomBatimentActuel le nom actuel du batiment
     * @param numeroEtageActuel le numero actuel de l'étage
     */
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
    /**
     * Supprime une salle de la base de données
     * @param numeroSalle Le numéro de la salle à supprimer
     * @param nomBatiment Le nom du batiment de la salle à supprimer
     */
    public void supprimerSalle(String numeroSalle, String nomBatiment) throws SQLException {
        String query = "DELETE FROM salles WHERE numeroSalle = ? AND nomBatiment = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, numeroSalle);
            pstmt.setString(2, nomBatiment);
            pstmt.executeUpdate();
        }
    }
    /**
     * Supprime une réservation de la base de données
     * @param numeroSalle Le numéro de la salle à supprimer
     * @param nomBatiment Le nom du batiment de la salle à supprimer
     * @return un boléan pour savoir si on a supprimé la reservation
     */
    public static boolean supprimerReservationsSalle(int idSalle) throws SQLException {
        String query = "DELETE FROM reservations WHERE id_salle = ?";
        try (Connection conn = ConnectBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, idSalle);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Création de la table Réservation
     */
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
    /**
     * Inclure dans la table réservation une nouvelle réservation avec les paramétres suivants:
     * @param idSalle l'identifiant de la salle
     * @param date La date de la réservation
     * @param heure l'heure du début de la réservation (pour une heure)
     * @param promo la promotion pour laquelle la salle est réservé
     * @param responsable le responsable, celui qui a effectué la reservation
     */
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
    /**
     * Vérifie si une salle est disponible pour une réservation à une date et une heure spécifiques.
     *
     * @param idSalle L'identifiant de la salle.
     * @param date La date pour laquelle la disponibilité est vérifiée.
     * @param heure L'heure pour laquelle la disponibilité est vérifiée.
     * @return true si la salle est disponible, false sinon.
     */
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
    /**
     * Obtient une liste de réservations pour une salle spécifique et un créneau horaire donné.
     *
     * @param date La date des réservations.
     * @param heureDebut L'heure de début des réservations à récupérer.
     * @param heureFin L'heure de fin des réservations à récupérer.
     * @param numeroSalle Le numéro de la salle.
     * @param nomBatiment Le nom du bâtiment de la salle.
     * @return Une liste d'objets Reservation.
     */
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

    /**
     * Obtient une liste de créneaux horaires libres pour une salle spécifique et un jour donné.
     *
     * @param date La date pour laquelle les créneaux sont vérifiés.
     * @param heureDebut L'heure de début pour la vérification des créneaux.
     * @param heureFin L'heure de fin pour la vérification des créneaux.
     * @param numeroSalle Le numéro de la salle.
     * @param nomBatiment Le nom du bâtiment de la salle.
     * @return Une liste de créneaux horaires disponibles.
     */
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

    /**
     * Récupère les détails d'une réservation spécifique.
     *
     * @param date La date de la réservation.
     * @param heure L'heure de la réservation.
     * @param numeroSalle Le numéro de la salle réservée.
     * @param nomBatiment Le nom du bâtiment de la salle réservée.
     * @return Un objet Reservation si la réservation est trouvée, null sinon.
     */

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