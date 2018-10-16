package org.constantine.resto;

import org.constantine.resto.model.*;
import org.constantine.resto.repository.GradeRepository;
import org.constantine.resto.repository.RestaurantRepository;
import org.constantine.resto.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
public class RestoApplication implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final VisitorRepository visitorRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public RestoApplication(RestaurantRepository restaurantRepository, VisitorRepository visitorRepository, GradeRepository gradeRepository) {
        this.restaurantRepository = restaurantRepository;
        this.visitorRepository = visitorRepository;
        this.gradeRepository = gradeRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(RestoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        // Cleaning All Tables
        restaurantRepository.deleteAllInBatch();
        visitorRepository.deleteAllInBatch();

        // Populating Tables d = Double.parseDouble(String.format("%.3f", d));
        Restaurant restaurant01 = new Restaurant(null, "DAR ES-SOLTANE", "Resto 01 description", new Address("23, rue Hamlaoui", "25001", "Constantine"));
        Restaurant restaurant02 = new Restaurant(null, "TIDDIS", "Resto 02 description", new Address("Avenue Ali-Zaamouche", "25002", "Constantine"));
        Restaurant restaurant03 = new Restaurant(null, "CAFE NEDJMA", "Resto 03 description", new Address("1, avenue Rahmani-Achour", "25003", "Constantine"));
        Restaurant restaurant04 = new Restaurant(null, "RESTAURANT DE L'HOTEL CIRTA", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant05 = new Restaurant(null, "QSAR", "Resto 04 description", new Address("Cité des Arcades Romaines", "25004", "Constantine"));
        Restaurant restaurant06 = new Restaurant(null, "JANNAH RESTAURANT", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant07 = new Restaurant(null, "DAR DIAF", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant08 = new Restaurant(null, "BUON GUSTO", "Resto 04 description", new Address("Sid Mabrouk inférieur", "25004", "Constantine"));
        Restaurant restaurant09 = new Restaurant(null, "MAGIC FOOD", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant10 = new Restaurant(null, "LA QUEEN FOOD", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant11 = new Restaurant(null, "IGHERSEEN", "Resto 04 description", new Address("58, rue Arbi Ben Mehdi", "25004", "Constantine"));
        Restaurant restaurant12 = new Restaurant(null, "PATISSERIE CASSONADE", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant13 = new Restaurant(null, "PATISSERIE DELICE CONSTANTINE", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant14 = new Restaurant(null, "PIZZERIA LA STRESA", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant15 = new Restaurant(null, "CAFE BEYROUTH", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant16 = new Restaurant(null, "CAFETERIA SNACK OXUGENE", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant17 = new Restaurant(null, "CHICKEN LAND", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant18 = new Restaurant(null, "AL BAIK", "Resto 04 description", new Address("N3", "25004", "Constantine"));
        Restaurant restaurant19 = new Restaurant(null, "METRO FOOD", "Resto 04 description", new Address("rue 04", "25004", "Constantine"));
        Restaurant restaurant20 = new Restaurant(null, "LES PLATANES", "Resto 04 description", new Address("Cité Kouthil Lakhdar", "25004", "Constantine"));

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Collections.addAll(restaurants, restaurant01, restaurant02, restaurant03, restaurant04, restaurant05,
                restaurant06, restaurant07, restaurant08, restaurant09, restaurant10, restaurant11, restaurant12,
                restaurant13, restaurant14, restaurant15, restaurant16, restaurant17, restaurant18, restaurant19, restaurant20);

        Visitor client01 = new Visitor(null, "Mahamet Habibou", "Mahamet2018", "password");
        Visitor client02 = new Visitor(null, "Souley Ladan", "Souley2018", "password");
        Visitor client03 = new Visitor(null, "Boubacar Moussa", "Moussa2018", "password");
        Visitor client04 = new Visitor(null, "Karimou Seyni", "Karimou2018", "password");
        Visitor client05 = new Visitor(null, "Arzika Sabiou", "Arzika2018", "password");
        Visitor client06 = new Visitor(null, "Mainassara Moussa", "Mainassara2018", "password");
        Visitor client07 = new Visitor(null, "Boubacar Bako", "Bako2018", "password");
        Visitor client08 = new Visitor(null, "Aboubacar Sani", "Sani2018", "password");
        Visitor client09 = new Visitor(null, "Chegou Bosso", "Chegou2018", "password");
        Visitor client10 = new Visitor(null, "Badam Mamane", "Badam2018", "password");

        ArrayList<Visitor> clients = new ArrayList<>();
        Collections.addAll(clients, client01, client02, client03, client04, client05, client06, client07, client08, client09, client10);

        Grade grade01 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(1L, null, null, null),
                10,
                14,
                12
        );
        Grade grade02 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(2L, null, null, null),
                12,
                16,
                14
        );
        Grade grade03 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );
        Grade grade04 = new Grade(
                new Restaurant(2L, null, null, null),
                new Visitor(4L, null, null, null),
                17,
                14,
                13
        );
        Grade grade05 = new Grade(
                new Restaurant(2L, null, null, null),
                new Visitor(5L, null, null, null),
                17,
                16,
                17
        );
        Grade grade06 = new Grade(
                new Restaurant(2L, null, null, null),
                new Visitor(3L, null, null, null),
                18,
                15,
                15
        );
        Grade grade07 = new Grade(
                new Restaurant(2L, null, null, null),
                new Visitor(9L, null, null, null),
                17,
                14,
                17
        );
        Grade grade08 = new Grade(
                new Restaurant(2L, null, null, null),
                new Visitor(8L, null, null, null),
                15,
                16,
                17
        );
        Grade grade09 = new Grade(
                new Restaurant(7L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );
        Grade grade10 = new Grade(
                new Restaurant(10L, null, null, null),
                new Visitor(5L, null, null, null),
                11,
                14,
                13
        );
        Grade grade11 = new Grade(
                new Restaurant(10L, null, null, null),
                new Visitor(2L, null, null, null),
                10,
                13,
                10
        );
        Grade grade12 = new Grade(
                new Restaurant(10L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                12
        );
        Grade grade13 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(1L, null, null, null),
                10,
                14,
                12
        );
        Grade grade14 = new Grade(
                new Restaurant(8L, null, null, null),
                new Visitor(2L, null, null, null),
                12,
                16,
                14
        );
        Grade grade15 = new Grade(
                new Restaurant(8L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );Grade grade16 = new Grade(
                new Restaurant(8L, null, null, null),
                new Visitor(1L, null, null, null),
                10,
                14,
                12
        );
        Grade grade17 = new Grade(
                new Restaurant(8L, null, null, null),
                new Visitor(2L, null, null, null),
                12,
                16,
                14
        );
        Grade grade18 = new Grade(
                new Restaurant(8L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );
        Grade grade19 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(1L, null, null, null),
                10,
                14,
                12
        );
        Grade grade20 = new Grade(
                new Restaurant(1L, null, null, null),
                new Visitor(2L, null, null, null),
                12,
                16,
                14
        );
        Grade grade21 = new Grade(
                new Restaurant(6L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );
        Grade grade22 = new Grade(
                new Restaurant(6L, null, null, null),
                new Visitor(1L, null, null, null),
                10,
                14,
                12
        );
        Grade grade23 = new Grade(
                new Restaurant(6L, null, null, null),
                new Visitor(2L, null, null, null),
                12,
                16,
                14
        );
        Grade grade24 = new Grade(
                new Restaurant(17L, null, null, null),
                new Visitor(3L, null, null, null),
                9,
                13,
                11
        );




        ArrayList<Grade> grades = new ArrayList<>();
        Collections.addAll(grades, grade01, grade02, grade03, grade04, grade05, grade06, grade07, grade08, grade09, grade10,
                grade11, grade12, grade13, grade14, grade15, grade16, grade17, grade18, grade19, grade20, grade21, grade22,
                grade23, grade24);

        //restaurant01.getGrades().addAll(grades);

        restaurantRepository.saveAll(restaurants);
        visitorRepository.saveAll(clients);
        gradeRepository.saveAll(grades);


	}

}
