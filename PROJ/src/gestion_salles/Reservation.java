package gestion_salles;

public class Reservation {
    private int idReservation;
    private String numeroSalle; 
    private String nomBatiment;
    private String date;
    private String heure;
    private String promo;
    private String responsable;

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
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public String getNomBatiment() {
        return nomBatiment;
    }

    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }
    public String getDateReservation() {
        return date;
    }

    public void setDateReservation(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
