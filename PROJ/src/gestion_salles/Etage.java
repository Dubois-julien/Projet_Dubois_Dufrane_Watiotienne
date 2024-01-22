package gestion_salles;

/**
 * Représente un étage dans un bâtiment.
 */
public class Etage {
    private int numeroEtage;

    // Constructeur

    /**
     * Construit un nouvel étage avec un numéro spécifique.

     * @param numeroEtage Le numéro de l'étage.
     */
    public Etage(int numeroEtage) {
        this.numeroEtage = numeroEtage;
    }
    
    /**
     * Renvoie le numéro de cet étage.
     * @return Le numéro de l'étage.
     */
    // Getter
    public int getNumeroEtage() {
        return numeroEtage;
    }

    // Setter
    /**
     * Définit le numéro de cet étage.
     * @param numeroEtage Le nouveau numéro de l'étage.
     */
    public void setNumeroEtage(int numeroEtage) {
        this.numeroEtage = numeroEtage;
    }
}
