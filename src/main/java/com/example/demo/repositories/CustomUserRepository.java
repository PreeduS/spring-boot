package com.example.demo.repositories;

import java.util.Optional;

import com.example.demo.models.User;

interface CustomUserRepository {
    String test();
    //Optional<User> findByUsername(String username); 
    /*
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> root = query.from(Course.class); 

        root.join("topic");
        // root.fetch("topic");     // eager load

        List<Course> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> System.out.println(x.getId() + " - " + x.getName() + " - " + x.getTopic().getName() ) );

    */

    /*

    @Override
    //@SuppressWarnings("unchecked")
    default Optional<User> findByUsername(String username)  {
    */
}