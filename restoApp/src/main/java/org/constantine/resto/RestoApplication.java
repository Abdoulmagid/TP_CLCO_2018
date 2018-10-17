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
public class RestoApplication {

    public static void main(String[] args) {
		SpringApplication.run(RestoApplication.class, args);
	}

}
