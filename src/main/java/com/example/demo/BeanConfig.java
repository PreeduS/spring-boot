/*package com.example.demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//https://www.devglan.com/spring-boot/spring-boot-hibernate-5-example

// spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl


//
// spring.jpa.properties.hibernate.current_session_context_class = org.springframework.orm.hibernate5.SpringSessionContext


@Configuration
@Component
public class BeanConfig {

	//@PersistenceContext
	@Autowired
	private EntityManager entityManager;
	//private EntityManagerFactory entityManagerFactory;


	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManager.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
		}
 
	    return entityManager.unwrap(SessionFactory.class);
	}
}*/