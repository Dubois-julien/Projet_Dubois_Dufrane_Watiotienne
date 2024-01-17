package main;

import gestion_salles.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Gestion.initialiserBaseDeDonnees();
    	boolean quitter = false;

        while (!quitter) {
            System.out.println("Choisissez une option :");
            System.out.println("1. Afficher toutes les salles");
            System.out.println("2. Ajouter une nouvelle salle");
            System.out.println("3. Modifier une salle");
            System.out.println("4. Supprimer une salle");
            System.out.println("5. Afficher les réservations");
            System.out.println("6. Afficher les créneaux libres");
            System.out.println("7. Réserver une salle");
            System.out.println("8. Quitter");

            int choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
            	case 1:
            		Gestion.afficherToutesLesSalles();
            		break;
            	case 2:
                    Gestion.ajouterSalle();
                    break;
                case 3:
                    Gestion.modifierSalle();
                    break;
                case 4:
                    Gestion.supprimerSalle();
                    break;
                case 5:
                	Gestion.afficherReservations();
                    break;
                case 6:
                	Gestion.afficherCreneauxLibres();
                	break;
                case 7:
                    Gestion.faireReservation();
                    break; 
                case 8:
                    quitter = true;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        }

    }
}
