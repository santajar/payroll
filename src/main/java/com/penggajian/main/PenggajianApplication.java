package com.penggajian.main;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@SpringBootApplication
public class PenggajianApplication {

	public static void main(String[] args) {
		SpringApplication.run(PenggajianApplication.class, args);
	}
	
	
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
	    return hemf.getSessionFactory();
	}
}
