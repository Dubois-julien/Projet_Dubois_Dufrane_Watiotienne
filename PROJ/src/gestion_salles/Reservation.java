package gestion_salles;
/**
 * Représente une réservation pour une salle. 
 * Chaque réservation contient des informations comme l'identifiant de la réservation, 
 * le numéro de la salle, le nom du bâtiment, la date et l'heure de la réservation, 
 * la promo concernée et le responsable de la réservation.
 */
public class Reservation {
    private int idReservation;
    private String numeroSalle; 
    private String nomBatiment;
    private String date;
    private String heure;
    private String promo;
    private String responsable;

    /**
     * Construit une nouvelle réservation avec les détails fournis.
     * @param idReservation L'identifiant de la réservation.
     * @param numeroSalle Le numéro de la salle réservée.
     * @param nomBatiment Le nom du bâtiment où se trouve la salle.
     * @param date La date de la réservation.
     * @param heure L'heure de la réservation.
     * @param promo La promo concernée par la réservation.
     * @param responsable Le nom du responsable de la réservation.
     */
    public Reservation(int idReservation, String numeroSalle, String nomBatiment, String date, String heure, String promo, String responsable) {
        this.idReservation = idReservation;
        this.numeroSalle = numeroSalle;
        this.nomBatiment = nomBatiment;
        this.date = date;
        this.heure = heure;
        this.promo = promo;
        this.responsable = responsable;
    }

    // Getters et Setters
    /**
     * Récupère l'ID de la réservation.
     * @return L'ID de la réservation.
     */
    public int getIdReservation() {
        return idReservation;
    }
    /**
     * Définit l'ID de la réservation.
     * @param idReservation L'ID de la réservation à définir.
     */
    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }
    /**
     * Récupère le numéro de la salle réservée.
     * @return Le numéro de la salle.
     */
    public String getNumeroSalle() {
        return numeroSalle;
    }
    /**
     * Définit le numéro de la salle réservée.
     * @param numeroSalle Le numéro de la salle à définir.
     */
    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }
    /**
     * Récupère le nom du bâtiment de la salle réservée.
     * @return Le nom du bâtiment.
     */
    public String getNomBatiment() {
        return nomBatiment;
    }
    /**
     * Définit le nom du bâtiment de la salle réservée.
     * @param nomBatiment Le nom du bâtiment à définir.
     */
    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }
    /**
     * Récupère la date de la réservation.
     * @return La date de la réservation.
     */
    public String getDateReservation() {
        return date;
    }
    /**
     * Définit la date de la réservation.
     * @param date La date de la réservation à définir.
     */
    public void setDateReservation(String date) {
        this.date = date;
    }
    /**
     * Récupère l'heure de la réservation.
     * @return L'heure de la réservation.
     */
    public String getHeure() {
        return heure;
    }
    /**
     * Définit l'heure de la réservation.
     * @param heure L'heure de la réservation à définir.
     */
    public void setHeure(String heure) {
        this.heure = heure;
    }
    /**
     * Récupère la promo concernée par la réservation.
     * @return La promo de la réservation.
     */
    public String getPromo() {
        return promo;
    }
    /**
     * Définit la promo concernée par la réservation.
     * @param promo La promo à définir pour la réservation.
     */
    public void setPromo(String promo) {
        this.promo = promo;
    }
    /**
     * Récupère le nom du responsable de la réservation.
     * @return Le nom du responsable.
     */
    public String getResponsable() {
        return responsable;
    }
    /**
     * Définit le nom du responsable de la réservation.
     * @param responsable Le nom du responsable à définir.
     */

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
