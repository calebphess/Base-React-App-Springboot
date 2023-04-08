package com.zetech.thingapp.thingapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

/*  
This is the starting point of our application, here we do lots of wizardy that I will summarize below:
	- building out the dev environment with .env so development is easier
	- actually starting the spring application/server
*/

@SpringBootApplication
public class ThingappApplication {

	public static void main(String[] args) {
		if (System.getenv("SPRING_PROFILES_ACTIVE") == null || System.getenv("SPRING_PROFILES_ACTIVE").equals("dev")) {
            Dotenv dotenv = Dotenv.configure().directory("server/thingapp").load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        }
		
		SpringApplication.run(ThingappApplication.class, args);
	}

}
