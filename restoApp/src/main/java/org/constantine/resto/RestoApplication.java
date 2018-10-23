package org.constantine.resto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestoApplication {

    public static void main(String[] args) {
		SpringApplication.run(RestoApplication.class, args);
	}

}
