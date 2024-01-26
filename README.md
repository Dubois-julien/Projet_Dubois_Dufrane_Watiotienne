# Projet_Dubois_Dufrane_Watiotienne

## Informations Importantes

Pour exécuter l'application, assurez-vous d'ajouter le connecteur MySQL JDBC suivant à votre projet : `mysql-connector-j-8.0.31.jar` il peut être télécharger via ce lien : https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.31/
Vérifiez également dans le fichier `ConnectBd.java` du package `ConnexionBd` que l'URL, l'identifiant et le mot de passe sont en accord avec ceux définis lors de la configuration de votre utilitaire de base de données (tel que DBeaver). 

Les modèles sont disponibles à la visualisation (`MLD.PNG` et `MCD.PNG`).

Lire les fonctionnalités suivantes pour les détails d'utilisation :

## Fonctionnalités disponibles
L’université a besoin de créer les emplois du temps de réservation des salles informatiques à l’intérieur du campus.

L'application a pour but :   

  • Ajouter/modifier/supprimer une salle
  
    Une salle est identifiée par :
    
    o un bâtiment
    
    o un étage
    
    o un numéro
    
  • Ajouter une réservation dans une salle :
  
    o Une date (au format YYYY-MM-DD)
    
    o Une heure (au format XX:00 ou XX:30). Les réservations sont possibles toutes les 30 minutes et l'on considère 
    
    qu'une réservation dure 1h, sur un créneau de réservation de 8h à 18h car les salles ferment à 19h.
    
    o Une promo (par exemple M2 ISN)
    
    o Un responsable de la réservation
    
  • Un affichage des créneaux libres sur une plage donnée (et pour une salle donnée). Si la plage horaire dépasse les heures disponibles alors on affiche uniquement les créneaux possibles (entre 8h et 18h).
  
  • Une liste des réservations sur une plage donnée (On considère que l'on veut la liste pour une salle donnée afin de vérifier qui l'a réservée par exemple).
  
  • Un affichage du détail d’une réservation choisie (Si l'on veut obtenir toutes les informations d'une réservation spécifique).
  
A la fermeture de l’application, les données saisies sont conservées dans une base de données (en local uniquement) et restituées à la prochaine ouverture.

Le projet contient :

  • MCD / MLD 
  
  • Script de création des tables de la base de données

  • Les sources du projet Java
  
  • Une documentation 

## Evolutions futures possibles

Améliorer la structure globale de la base de données, par exemple intégrer bien plus de bâtiments (et donc en faire une entité à part entière).

Ajouter d'autres informations sur chaque bâtiment/salle.

Améliorer l'aspect visuel de l'application (interface graphique). 
