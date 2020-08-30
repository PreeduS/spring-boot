package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
//@EnableJpaRepositories
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    public ObjectMapper getObjectMapper(){ 
      return new ObjectMapper();
     
	}
	
	/*@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory entityManagerFactory){  
		return entityManagerFactory.getSessionFactory();  
	} 
*/
 //https://www.baeldung.com/hibernate-5-spring
 /*   @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      
 
        return sessionFactory;
    }
*/

	

	// spring.jpa.properties.hibernate.current_session_context_class=org.springframework.orm.hibernate5.SpringSessionContext

	//
}


