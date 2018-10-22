# TP Cloud Computing 2018

## Partie 1: Développement d'une application

Le but de cette partie est l'utilisation de la plateforme github dans le cadre d'un travail collaboratif.

### Enoncé: Mettre en place une simple application web de recommandations de restaurants à partir des opinions laissées par les visiteurs.

Un client (ou un visiteur) peut donner trois notes sur 20 pour évaluer un restaurant qu'il a visité selon trois critères:
* Qualité de la nourriture
* Qualité de la salle
* Qualité du service

Les utilisateurs de l'application web, représentant des clients potentiels, peuvent donc consulter une liste des restaurants classés selon les avis donnés par les anciens visiteurs.

Pour établir ce classement, un serveur doit calculer à chaque fois un score qui correspond à la moyenne des 3 notes entrées par les visiteurs. Le serveur retourne et affiche ainsi une liste de restaurants classés lorsqu'un client la demande.

### Présentation de l'application

Page d'accueil

![Welcome Screen](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/restoAppRunnable/screenshots/welcome-screen.png)

Liste des restaurants (ordonnées selon les notes de clients)

![Restaurants List Screen](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/restoAppRunnable/screenshots/restaurants-list.png)

Informations sur un retaurant

![Restaurant Detail Screen](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/restoAppRunnable/screenshots/restaurants-detail.png)

Noter un restaurant

![Restaurant Rating Screen](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/restoAppRunnable/screenshots/restaurant-rating.png)

### Repartition des tâches

### Architecture de l'application

Nous avons développer l'application en utilisant le framework Spring Boot et avons opte pour une base de données in memory H2. 
Spring Boot est un framework qui permet de démarrer rapidement le développement d'applications ou services en fournissant les dépendances nécessaires et en auto-configurant celles-ci.

Pour activer l'auto-configuration, on utilise l'annotation @EnableAutoConfiguration . Si vous écrivez vos propres configurations, celles-ci priment sur celles de Spring Boot.

Les starters permettent d'importer un ensemble de dépendances selon la nature de l'application à développer afin de démarrer rapidement.

Une base de données in memory est crée lorsque l'application démarre et est detruite lorqu'elle est arrêtée.

H2 est l'une des base de données in memory les plus populaires. Spring Boot a une très bonne integration pour H2.

Ci-dessous une liste exhaustive des technologies utilisées:

* Spring Boot 2.0.6 avec les dependances: Web, JPA, H2, Lombok, Devtools
* AngularJS et Bootstrap 4
* Maven 3.1
* JDK 1.8
* Intellij Idea

#### Création du  projet Spring Boot avec H2

Spring Initializr http://start.spring.io/ est un très bon outils pour préparer les projets Spring Boot.

![Project Bootstraping](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/project-setup.png)

Comme montre sur la figure ci-dessus, les étapes sont:

* A partir de Spring Initializr, choisir :
   - org.constantine comme Group
   - resto comme Artifact
   - Selectionner les dépendances suivantes: 
      - Web
      - JPA
      - H2
      - DevTools
* Cliquer sur Generate Project.
* Importer le projet dans un IDE de choix (disposant de Maven, pour pouvoir telécharger les dépendances et construire le projet)

#### Création des entités



