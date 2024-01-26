# Projet_Dubois_Dufrane_Watiotienne

## Fonctionnalités disponibles
L’université a besoin de créer les emplois du temps de réservation des salles informatiques à l’intérieur du campus.

L'application a pour but :   

  • Ajouter/modifier/supprimer une salle
  
    Une salle est identifiée par :
    
    o un bâtiment
    
    o un étage
    
    o un numéro
    
  • Ajouter une réservation dans une salle :
  
    o Une date
    
    o Une heure
    
    o Une promo
    
    o Un responsable de la réservation
    
  • Un affichage des créneaux libres sur une plage donnée
  
  • Une liste des réservations sur une plage donnée
  
  • Un affichage du détail d’une réservation choisie
  
A la fermeture de l’application, les données saisies sont conservées dans une base de données et restituées à la prochaine ouverture.

Le projet contient :

  • MCD / MLD / MPD
  
  • Script de création de la base de données

  • Les sources de votre projet Java
  
  • Une documentation éventuelle

## Informations Importantes

Pour exécuter l'application, assurez-vous d'ajouter le connecteur MySQL JDBC suivant à votre projet : `mysql-connector-j-8.0.31.jar`.
Vérifiez également dans le fichier `ConnectBd.java` du package `ConnexionBd` que l'URL, l'identifiant et le mot de passe sont en accord avec ceux définis lors de la configuration de votre utilitaire de base de données (tel que DBeaver).

## Evolutions futures possibles

Améliorer la structure globale de la base de données, par exemple intégrer bien plus de bâtiments (et donc en faire une entité à part entière).

Ajouter d'autres informations sur chaque bâtiment/salle.

Améliorer l'aspect visuel de l'application (interface graphique). 
