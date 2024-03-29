package gestion_salles;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import ConnexionBd.Requetes_sql;
import outils.outils;
/**
 * Classe Gestion pour gérer les interactions avec la base de données concernant les salles.
 */
public class Gestion {
    private static Scanner scanner = new Scanner(System.in);
    /**
     * Initialise la base de données en créant les tables nécessaires.
     */
    public static void initialiserBaseDeDonnees() {
        Requetes_sql requete = new Requetes_sql();
        try {
            requete.creerTableSalles();
            requete.creerTableReservations();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
        }
    }
    /**
     * Permet d'ajouter une nouvelle salle à la base de données.
     * C'est l'utilisateur qui les informations
     */
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
    /**
     * Affiche les informations d'une salle spécifique.
     * @param numero Le numéro de la salle.
     * @param nomBatiment Le nom du bâtiment de la salle.
     */
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
    /**
     * Permet de modifier les informations d'une salle existante.
     * C'est l'utilisateur qui les informations
     */
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
    /**
     * Supprime une salle de la base de données.
     * C'est l'utilisateur qui les informations
     */
    public static void supprimerSalle() {
        System.out.print("Entrez le numéro de la salle à supprimer : ");
        String numero = scanner.nextLine();
        System.out.print("Entrez le nom du bâtiment de la salle : ");
        String nomBatiment = scanner.nextLine();
        Requetes_sql requete = new Requetes_sql();
        try {
            Salle salle = requete.getInfosSalle(numero, nomBatiment);
            if (salle != null) {
                afficherInfosSalle(numero, nomBatiment);
                System.out.print("Êtes-vous sûr de vouloir supprimer cette salle ? (O/N) : ");
                String confirmation = scanner.nextLine().trim().toUpperCase();

                if (confirmation.equals("O")) {
                	int idSalle = requete.getIdSalle(numero,nomBatiment);
                    if (Requetes_sql.supprimerReservationsSalle(idSalle)) {
                    	requete.supprimerSalle(numero, nomBatiment);
                        System.out.println("Salle supprimée avec succès !");
                    } else {
                        System.out.println("La suppression des réservations a échoué.");
                    }
                } else {
                    System.out.println("Suppression annulée.");
                }
            } else {
                System.out.println("Salle non trouvée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }

    /**
     * Affiche toutes les salles enregistrées dans la base de données.
     * C'est l'utilisateur qui les informations
     */
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
    /**
     * Ajoute une nouvelle réservation pour une salle.
     * C'est l'utilisateur qui les informations
     */
    public static void faireReservation() {
        System.out.println("Ajouter une nouvelle réservation :");

        System.out.print("Entrez le numéro de la salle : ");
        String numeroSalle = scanner.nextLine();
        System.out.print("Entrez le nom du bâtiment : ");
        String nomBatiment = scanner.nextLine();

        Requetes_sql requete = new Requetes_sql();

        try {
            int idSalle = requete.getIdSalle(numeroSalle, nomBatiment);

            if (idSalle != -1) {
                System.out.print("Entrez la date de la réservation (au format yyyy-MM-dd) : ");
                String date = scanner.nextLine();

                System.out.print("Entrez l'heure de la réservation (au format HH:00 ou HH:30) : ");
                String heure = scanner.nextLine();

                System.out.print("Entrez la promo : ");
                String promo = scanner.nextLine();

                System.out.print("Entrez le responsable de la réservation : ");
                String responsable = scanner.nextLine();

                if (outils.isValidDate(date) && outils.isValidTime(heure)) {
                    if (requete.SalleDisponible(idSalle, date, heure)) {
                        requete.faireReservation(idSalle, date, heure, promo, responsable);
                        System.out.println("Réservation ajoutée avec succès dans la base de données !");
                    }
                    
                } else {
                    System.out.println("Format de date ou d'heure invalide. Veuillez respecter les formats yyyy-MM-dd et HH:mm.");
                }
            } else {
                System.out.println("Salle non trouvée. Veuillez vérifier le numéro de salle et le nom du bâtiment.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réservation dans la base de données : " + e.getMessage());
        }
    }
    /**
     * Affiche les réservations pour une salle spécifique sur une plage horaire donnée.
     *C'est l'utilisateur qui les informations
     */
    public static void afficherReservations() {
        System.out.print("Entrez le numéro de la salle : ");
        String numeroSalle = scanner.nextLine();   
        
        System.out.print("Entrez le nom du bâtiment : ");
        String nomBatiment = scanner.nextLine();      
    	
        System.out.print("Entrez une date (format yyyy-MM-dd) : ");
        String date = scanner.nextLine();
        
        System.out.print("Entrez l'heure de début (format HH:mm) : ");
        String heureDebut = scanner.nextLine();
        
        System.out.print("Entrez l'heure de fin (format HH:mm) : ");
        String heureFin = scanner.nextLine();

        if (outils.isValidDate(date) && outils.isValidTime(heureDebut) && outils.isValidTime(heureFin)) {
            LocalTime debut = LocalTime.parse(heureDebut);
            LocalTime fin = LocalTime.parse(heureFin);
            if (debut.isBefore(fin)) {
                Requetes_sql requete = new Requetes_sql();
                try {
                    List<Reservation> reservations = requete.getReservations(date, heureDebut, heureFin,numeroSalle, nomBatiment);
                    if (reservations.isEmpty()) {
                        System.out.println("Aucune réservation trouvée pour la plage horaire spécifiée.");
                    } else {
                    	for (Reservation reservation : reservations) {
                    	    System.out.println("Réservation: " + reservation.getIdReservation() +
                    	                       " - Salle: " + reservation.getNumeroSalle() +
                    	                       " - Bâtiment: " + reservation.getNomBatiment() +
                    	                       " - Heure: " + reservation.getHeure() +
                    	                       " - Promo: " + reservation.getPromo() +
                    	                       " - Responsable: " + reservation.getResponsable());
                    	}
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
                }
            } else {
                System.out.println("L'heure de début doit être antérieure à l'heure de fin.");
            }
        } else {
            System.out.println("Formats de date ou d'heure invalides. Veuillez respecter les formats yyyy-MM-dd et HH:mm.");
        }
    }
    /**
     * Affiche les créneaux libres pour une salle spécifique sur une plage horaire donnée.
     * C'est l'utilisateur qui les informations
     */
    public static void afficherCreneauxLibres() {
        System.out.print("Entrez le numéro de la salle : ");
        String numeroSalle = scanner.nextLine();   
        
        System.out.print("Entrez le nom du bâtiment : ");
        String nomBatiment = scanner.nextLine();    
        
        System.out.print("Entrez une date (format yyyy-MM-dd) : ");
        String date = scanner.nextLine();
        
        System.out.print("Entrez l'heure de début (format HH:mm) : ");
        String heureDebut = scanner.nextLine();
        
        System.out.print("Entrez l'heure de fin (format HH:mm) : ");
        String heureFin = scanner.nextLine();
        if (heureDebut.compareTo("08:00") < 0) {
            heureDebut = "08:00";
            System.out.println("L'heure de début a été ajustée à 08:00 (heure minimal pour une reservation).");
        }
        if (heureFin.compareTo("18:00") > 0) {
            heureFin = "18:00";
            System.out.println("L'heure de fin a été ajustée à 18:00 (heure maximale pour une resservation).");
        }
        if (outils.isValidDate(date) && outils.isValidTime(heureDebut) && outils.isValidTime(heureFin)) {
        	LocalTime debut = LocalTime.parse(heureDebut);
            LocalTime fin = LocalTime.parse(heureFin);
            if (debut.isBefore(fin)) {
            	Requetes_sql requete = new Requetes_sql();
                try {
                    List<String> creneauxLibres = requete.getCreneauxLibres(date, heureDebut, heureFin, numeroSalle, nomBatiment);
                    if (creneauxLibres.isEmpty()) {
                        System.out.println("Aucun créneau n'est disponible sur la plage horaire spécifiée.");
                    } else {
                        System.out.println("Créneaux libres :");
                        for (String creneau : creneauxLibres) {
                            System.out.println(creneau);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la récupération des créneaux libres : " + e.getMessage());
                } 
            } else {
                System.out.println("L'heure de début doit être antérieure à l'heure de fin.");
            }
        } else {
            System.out.println("Formats de date ou d'heure invalides. Veuillez respecter les formats yyyy-MM-dd et HH:mm.");
        }
    }
    /**
     * Affiche les informations détaillées d'une réservation spécifique.
     * C'est l'utilisateur qui les informations
     */
    public static void afficherInfosReservation() {
        System.out.print("Entrez le numéro de la salle : ");
        String numeroSalle = scanner.nextLine();   
        
        System.out.print("Entrez le nom du bâtiment : ");
        String nomBatiment = scanner.nextLine();    
 
    	System.out.print("Entrez la date de la réservation (format yyyy-MM-dd) : ");
        String date = scanner.nextLine();

        System.out.print("Entrez l'heure de la réservation (format HH:mm) : ");
        String heure = scanner.nextLine();
        
        if (outils.isValidDate(date) && outils.isValidTime(heure)) {
        	Requetes_sql requete = new Requetes_sql();
            try {
                Reservation reservation = requete.getReservation(date, heure,nomBatiment, numeroSalle);
                if (reservation != null) {
                    System.out.println("Détails de la réservation :");
                    System.out.println("ID Réservation: " + reservation.getIdReservation());
                    System.out.println("Salle: " + reservation.getNumeroSalle() + " - Bâtiment: " + reservation.getNomBatiment());
                    System.out.println("Date: " + reservation.getDateReservation() + " - Heure: " + reservation.getHeure());
                    System.out.println("Promo: " + reservation.getPromo() + " - Responsable: " + reservation.getResponsable());
                } else {
                    System.out.println("Aucune réservation trouvée pour cette date et heure.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des informations de réservation : " + e.getMessage());
            }
        }else {
            System.out.println("Formats de date ou d'heure invalides. Veuillez respecter les formats yyyy-MM-dd et HH:mm.");
        }
    }
    
}
