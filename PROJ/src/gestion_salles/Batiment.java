package gestion_salles;

/**
 * Représente un bâtiment. Un bâtiment est caractérisé par son nom.
 */
public class Batiment {
    private String nom;

    // Constructeur
    /**
     * Construit un nouvel objet Batiment avec un nom spécifié.
     * @param nom Le nom du bâtiment.
     */
    public Batiment(String nom) {
        this.nom = nom;
    }

    // Getter
    /**
     * Renvoie le nom de ce bâtiment.
     * @return Le nom du bâtiment.
     */
    public String getNom() {
        return nom;
    }

    // Setter
    /**
     * Définit ou met à jour le nom de ce bâtiment.
     * @param nom Le nouveau nom du bâtiment.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}
