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

Nous avons décider pour cette application de ne pas séparer le serveur du client. c'est à dire le serveur se chargera lui même de rendre les vues au client. Ce dernier sera donc un simple navigateur. Tout le code serveur et client sont au niveau de l'application serveur elle-même.

| Tâches | Mahamet Habibou | Souley Ladan |
|------------ | -------------| -------------- |
| Choix de l'architecture et des technologies | :heavy_check_mark: | :heavy_check_mark: |
| Project Setup | :heavy_check_mark: | :heavy_check_mark: |
| Serveur | :heavy_check_mark: | :heavy_check_mark: |
| Client | :heavy_check_mark: | :heavy_check_mark: |
| Rapport | :heavy_check_mark: | :heavy_check_mark: |

### Architecture de l'application

Nous avons développé l'application en utilisant le framework Spring Boot et avons opté pour une base de données in memory H2. 
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

**Restaurant**(id, name, description, address)

**Visitor**(id, fullname, username, password)

**Grade**(restoId, visitorId, foodQuality, roomQuality, serviceQuality, quality, graduateAt)

**Address**(street, zip, city)

Exemple: Entité Restaurant

```java
package org.constantine.resto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 100)
    private String name;

    @NotNull
    @Column(columnDefinition = "text")
    private String description;

    @Embedded
    private Address address;


}
```

#### Spring Boot et H2

H2 fournit une interface web appelée H2 Console pour visualiser les données.

Fichier /src/main/resources/application.properties
```java
# Enabling H2 Console
spring.h2.console.enabled=true

# Configuring Database
spring.datasource.url=jdbc:h2:mem:restoGrades;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```
Spring Boot se charge automatiquement de créer les tables correspondant à nos entités dans la base de données H2.

![H2 Console](https://github.com/Abdoulmagid/TP_CLCO_2018/blob/master/h2-console.png)

Aussi, la base de données peut être peuplée en ajoutant un fichier `data.sql`

```sql
insert into restaurants (id, name, description, street, zip, city) values (null, 'DAR ES-SOLTANE', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', '23, rue Hamlaoui', '25000', 'Constantine');
insert into restaurants (id, name, description, street, zip, city) values (null, 'TIDDIS', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 'Avenue Ali-Zaamouche', '25000', 'Constantine');

insert into visitors (id, fullname, username, password) values (null, 'Mahamet Habibou', 'Mahamet2018', 'password');
insert into visitors (id, fullname, username, password) values (null, 'Souley Ladan', 'Souley2018', 'password');

insert into grades (restaurant_id, visitor_id, food_quality, room_quality, service_quality, quality, graduate_at) values (1, 5, 12, 13, 15, 14, '2018-10-17 01:50:55.618');
insert into grades (restaurant_id, visitor_id, food_quality, room_quality, service_quality, quality, graduate_at) values (1, 10, 15, 16, 14, 15, '2018-10-17 01:50:55.618');

```

#### Création des controller Rest et des repository

Les controller nous permettent d'exposer notre application sous forme d'api
Et les repository pour la communication avec la base de données.

RestaurantController

```java
package org.constantine.resto.controller;

import org.constantine.resto.error.CustomError;
import org.constantine.resto.model.*;
import org.constantine.resto.repository.GradeRepository;
import org.constantine.resto.repository.RestaurantRepository;
import org.constantine.resto.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/restaurants")
    public Page<RestaurantDetail> getAllRestaurants() {
        return restaurantRepository.findAllRestaurantsDetail(PageRequest.of(0, 10, Sort.by("totalRatings").descending().and(Sort.by("ratings").descending())));
    }

    @GetMapping("/restaurants/{restoId}/details")
    public ResponseEntity<?> getRestaurantGradeDetail(
            @PathVariable Long restoId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>( new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

        List<Grade> grades = gradeRepository.findGradesByRestaurant(restaurant.get());
        if (grades.size() == 0)
            return new ResponseEntity<>(
                    new RestaurantDetail(
                            restaurant.get().getId(),
                            restaurant.get().getName(),
                            restaurant.get().getDescription(),
                            restaurant.get().getAddress(),
                            null,
                            null
                            ),
                    HttpStatus.OK);

        return new ResponseEntity<>(
                gradeRepository.findRestaurantDetail(restoId),
                HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restoId}/grades")
    public ResponseEntity<?> getGradesByRestaurantId(
            @PathVariable Long restoId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        return restaurant.<ResponseEntity<?>>
                map(restaurant1 -> new ResponseEntity<>(gradeRepository.findGradesByRestaurant(restaurant1), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK));
    }

    @GetMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> getGradeByRestaurantIdAndVisitorId(
            @PathVariable Long restoId,
            @PathVariable Long clientId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>(new CustomError("Client not found with id " + clientId), HttpStatus.OK);

        Optional<Grade> grade = gradeRepository.findGradeByRestaurantAndVisitor(restaurant.get(), client.get());
        if (!grade.isPresent())
            return new ResponseEntity<>(new CustomError("Grade not found"), HttpStatus.OK);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PostMapping("/restaurants/{restoId}/grades/{clientId}")
    public ResponseEntity<?> graduateRestaurant(
            @PathVariable Long restoId,
            @PathVariable Long clientId,
            @Valid @RequestBody GraduateRequest graduateRequest) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restoId);
        if (!restaurant.isPresent())
            return new ResponseEntity<>(new CustomError("Restaurant not found with id " + restoId), HttpStatus.OK);

        Optional<Visitor> client = visitorRepository.findById(clientId);
        if (!client.isPresent())
            return new ResponseEntity<>(new CustomError("Client not found with id " + clientId), HttpStatus.OK);

        Optional<Grade> grade = gradeRepository.findGradeByRestaurantAndVisitor(restaurant.get(), client.get());
        if (grade.isPresent())
            return new ResponseEntity<>(grade, HttpStatus.OK);

        Grade newGrade = new Grade(
                restaurant.get(),
                client.get(),
                graduateRequest.getFoodQuality(),
                graduateRequest.getRoomQuality(),
                graduateRequest.getServiceQuality()
        );

        return new ResponseEntity<>(gradeRepository.save(newGrade), HttpStatus.OK);
    }

    @GetMapping("/restaurants/search")
    public Page<RestaurantDetail> searchRestaurant(
            @RequestParam(defaultValue = "", name = "q") String query,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        return restaurantRepository.searchRestaurants("%"+query+"%", PageRequest.of(page, size, Sort.by("totalRatings").descending().and(Sort.by("ratings").descending())));
    }

}
```

RestaurantRepository

```java
package org.constantine.resto.repository;

import org.constantine.resto.model.Restaurant;
import org.constantine.resto.model.RestaurantDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "g.restaurant.id as id, " +
            "g.restaurant.name as name, " +
            "g.restaurant.description as description, " +
            "g.restaurant.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Grade g " +
            "GROUP BY g.restaurant.id " +
            "HAVING g.restaurant.name LIKE :q")
    Page<RestaurantDetail> searchRestaurants(@Param("q") String query, Pageable pageable);
    @Query("SELECT " +
            "new org.constantine.resto.model.RestaurantDetail(" +
            "g.restaurant.id as id, " +
            "g.restaurant.name as name, " +
            "g.restaurant.description as description, " +
            "g.restaurant.address as address, " +
            "AVG(g.quality) as ratings, " +
            "COUNT(g.quality) as totalRatings) " +
            "FROM Grade g " +
            "GROUP BY g.restaurant.id")
    Page<RestaurantDetail> findAllRestaurantsDetail(Pageable pageable);

}
```

#### Le frontend de l'application

Le frontend utilise les librairies:
* Bootstrap 4 (css et js) : Pour les composants graphiques (navbar, button, formulaires, ...)
* AngularJs : Framework javascript, pour rendre l'application dynamique, envoyer des requetes HTTP à notre api et recevoir les réponses.
* AngularJs ui-router : Pour le routage (navigation entre les pages)
* AngularJs ui-notification : Pour afficher les notifications
* AngularJs ngStorage : Pour stocker des données sur le navigateur

#### Création de l'exécutable .jar

La commande

```
mvn clean & mvn install
```

crée l'exécutable .jar de l'application contenant toute les dépendances nécessaire à l'exécution de l'application.

Lancer l'exécutable avec la commande

```
java -jar <exécutable>
```

Pour changer le numéro de port du server

```
java -jar <exécutable> --server.port=8083
```


Ensuite, dans un navigateur entrer

```
http://localhost:<port>
```

## Partie 2: Déployement de l'application sur le cloud (Openshift)

### La plateforme Openshift

...
