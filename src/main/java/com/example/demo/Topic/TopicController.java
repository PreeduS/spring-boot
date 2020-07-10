package com.example.demo.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/topic")
    public Topic addTopic( @RequestBody Topic topic) {
  
        return topicService.addTopic(topic);
    }

    public void temp (){
        /*
        @Autowired
        SessionFactory sessionFactory;

        //try
        Session session = sessionFactory.openSession();
        session.beginTransaction()
        session.save(obj)

        sessionFactory.getTranscation().commit();

        //catch

        // session.get(Class.class,id)

        

        
        // ---
        Query query = session.createQuery("SELECT ... FROM ... WHERE id=:id");
        query.setParameter("id",id);
        Obj obj = (Obj)query.getSingleResult();

        // ---

            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hqlUpdate = "update Customer c set c.name = :newName where c.name = :oldName";
            // or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setParameter( "newName", newName )
                    .setParameter( "oldName", oldName )
                    .executeUpdate();
            tx.commit();
            session.close();


        // -

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.set(root.get("fname"), user.getName());
            criteria.set(root.get("lname"), user.getlastName());
            criteria.where(builder.equal(root.get("id"), user.getId()));
            session.createQuery(criteria).executeUpdate();



        // ---


        */
        // 1:00:00
    }
}