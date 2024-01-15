package gestion_salles;

public class Salle {
    private String numeroSalle;
    private Batiment batiment;
    private Etage etage;

    // Constructeur
    public Salle(String numeroSalle, Batiment batiment, Etage etage) {
        this.numeroSalle = numeroSalle;
        this.batiment = batiment;
        this.etage = etage;
    }

    // Getters et Setters
    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }
}
