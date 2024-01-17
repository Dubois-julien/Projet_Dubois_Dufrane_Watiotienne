package gestion_salles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ConnexionBd.Requetes_sql;

public class Gestion {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Salle> salles = new ArrayList<>();
    
    public static void initialiserBaseDeDonnees() {
        Requetes_sql requete = new Requetes_sql();
        try {
            requete.creerTableSalles();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table 'salles' : " + e.getMessage());
        }
    }
    
    public static void ajouterSalle() {
        System.out.println("Ajouter une nouvelle salle :");
        System.out.print("Entrez le numéro de la salle : ");
        String numeroSalle = scanner.nextLine();
        System.out.print("Entrez le nom du bâtiment : ");
        String nomBatiment = scanner.nextLine();
        System.out.print("Entrez le numéro de l'étage : ");
        int numeroEtage = scanner.nextInt();
        scanner.nextLine(); 

        Batiment batiment = new Batiment(nomBatiment);
        Etage etage = new Etage(numeroEtage);
        Salle salle = new Salle(numeroSalle, batiment, etage);
        Requetes_sql requete = new Requetes_sql();
        try {
            requete.ajouterSalle(salle);
            System.out.println("Salle ajoutée avec succès dans la base de données !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la salle dans la base de données : " + e.getMessage());
        }
    }
    
    public static void afficherInfosSalle(String numero, String nomBatiment) {
        Requetes_sql requete = new Requetes_sql();
        try {
            Salle salle = requete.getInfosSalle(numero, nomBatiment);
            if (salle != null) {
                System.out.println("Informations de la salle :");
                System.out.println("Numéro de la salle: " + salle.getNumeroSalle());
                System.out.println("Nom du bâtiment: " + salle.getBatiment().getNom());
                System.out.println("Numéro de l'étage: " + salle.getEtage().getNumeroEtage());
            } else {
                System.out.println("Salle non trouvée.");
            }
        } catch (SQLException e) {
        System.out.println("Erreur lors de la récupération des informations de la salle : " + e.getMessage());
        }
    }
    
    public static void modifierSalle() {
        System.out.print("Entrez le numéro de la salle à modifier : ");
        String numero = scanner.nextLine();
        System.out.print("Entrez le nom du bâtiment de la salle : ");
        String nomBatiment = scanner.nextLine();
        Requetes_sql requete = new Requetes_sql();

        try {
            Salle salle = requete.getInfosSalle(numero, nomBatiment);
            if (salle != null) {
                afficherInfosSalle(numero,nomBatiment);
                System.out.print("Nouveau numéro de salle (laissez vide pour ne pas modifier) : ");
                String nouveauNumero = scanner.nextLine();

                System.out.print("Nouveau nom de bâtiment (laissez vide pour ne pas modifier) : ");
                String nouveauNomBatiment = scanner.nextLine();

                System.out.print("Nouveau numéro d'étage (entrez un nombre négatif pour ne pas modifier) : ");
                int nouveauNumeroEtage = scanner.nextInt();
                scanner.nextLine(); 
                
                String nomBatimentActuel = salle.getBatiment().getNom();
                int numeroEtageActuel = salle.getEtage().getNumeroEtage();

                requete.modifierSalle(numero, nouveauNumero, nouveauNomBatiment, nouveauNumeroEtage, nomBatimentActuel, numeroEtageActuel);
                System.out.println("Salle modifiée avec succès !");
            } else {
                System.out.println("Salle non trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la salle : " + e.getMessage());
        }
    }

    public static void supprimerSalle() {
        System.out.print("Entrez le numéro de la salle à supprimer : ");
        String numero = scanner.nextLine();
        System.out.print("Entrez le batiment de la salle à supprimer : ");
        String nomBatiment = scanner.nextLine();
        for (int i = 0; i < salles.size(); i++) {
            if (salles.get(i).getNumeroSalle().equals(numero) && salles.get(i).getBatiment().getNom().equals(nomBatiment)) {
            	afficherInfosSalle(numero,nomBatiment);
            	salles.remove(i);
                System.out.println("Salle supprimée avec succès !");
                return;
            }
        }
        System.out.println("Salle non trouvée.");
    }
    
    public static void afficherToutesLesSalles() {
        Requetes_sql requete = new Requetes_sql();
        try {
            List<Salle> salles = requete.getToutesLesSalles();
            if (salles.isEmpty()) {
                System.out.println("Aucune salle n'est enregistrée.");
            } else {
                System.out.println("Liste de toutes les salles :");
                for (Salle salle : salles) {
                    System.out.println("Numéro de la salle: " + salle.getNumeroSalle());
                    System.out.println("Nom du bâtiment: " + salle.getBatiment().getNom());
                    System.out.println("Numéro de l'étage: " + salle.getEtage().getNumeroEtage());
                    System.out.println("--------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des salles : " + e.getMessage());
        }
    }

}
