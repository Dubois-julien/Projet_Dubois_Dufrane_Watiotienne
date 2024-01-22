package gestion_salles;
/**
 * Représente une salle au sein d'un bâtiment. 
 * Chaque salle est identifiée par son numéro, et est associée à un bâtiment et un étage spécifiques.
 */
public class Salle {
    private String numeroSalle;
    private Batiment batiment;
    private Etage etage;

    // Constructeur
    /**
     * Construit une nouvelle salle.
     * @param numeroSalle Le numéro de la salle.
     * @param batiment L'objet Batiment associé à la salle.
     * @param etage L'objet Etage où se trouve la salle.
     */
    public Salle(String numeroSalle, Batiment batiment, Etage etage) {
        this.numeroSalle = numeroSalle;
        this.batiment = batiment;
        this.etage = etage;
    }

    // Getters et Setters
    /**
     * Récupère le numéro de la salle.
     * @return Le numéro de la salle.
     */
    public String getNumeroSalle() {
        return numeroSalle;
    }
    /**
     * Définit ou met à jour le numéro de la salle.
     * @param numeroSalle Le nouveau numéro de la salle.
     */
    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }
    /**
     * Récupère le bâtiment associé à la salle.
     * @return L'objet Batiment associé à la salle.
     */
    public Batiment getBatiment() {
        return batiment;
    }
    /**
     * Définit ou met à jour le bâtiment associé à la salle.
     * @param batiment Le nouveau bâtiment associé à la salle.
     */
    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
    /**
     * Récupère l'étage où se trouve la salle.
     * @return L'objet Etage où se trouve la salle.
     */
    public Etage getEtage() {
        return etage;
    }
    /**
     * Définit ou met à jour l'étage où se trouve la salle.
     * @param etage Le nouvel étage où se trouve la salle.
     */
    public void setEtage(Etage etage) {
        this.etage = etage;
    }
}
