package com.example.demo.Course;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.criteria.Predicate;

import com.example.demo.Topic.Topic;
import com.example.demo.Topic.TopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriteriaQueryExService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void _updateCourse(String id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //em.getTransaction().begin();
        CriteriaUpdate<Course> criteriaUpdate = cb.createCriteriaUpdate(Course.class);
        Root<Course> root = criteriaUpdate.from(Course.class);

        criteriaUpdate.where(cb.equal(root.get("id"), Integer.parseInt(id)));
        // works without overriding with null
        criteriaUpdate.set("name", "c_name");
        //criteriaUpdate.set("description", null);

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();  

        //em.getTransaction().commit();
        //em.close();

        // https://www.baeldung.com/hibernate-criteria-queries

    }


    @Transactional
    public void _deleteCourse(String id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
 
        CriteriaDelete<Course> criteriaDelete = cb.createCriteriaDelete(Course.class);
        Root<Course> root = criteriaDelete.from(Course.class);

        criteriaDelete.where(cb.equal(root.get("id"), Integer.parseInt(id)));
 

        Query query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();  
 

    }



    public void temp(){
        /*Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);
       
        
        Root<Course> root = criteriaQuery.from(Course.class);

        criteriaQuery.select(root);
        
        Query<Course> query = session.createQuery(criteriaQuery);

        List<Course> list = query.list();

        list.forEach(x -> System.out.println(x.getName()) );
        */

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
            CriteriaQuery<Course> query = cb.createQuery(Course.class);
            Root<Course> root = query.from(Course.class);

            query.select(root);
        

            // query.where(cb.equal(root.get("id"),10));
            // query.where(cb.gt(root.get("id"),10));
 
            List<Predicate> predicates = new ArrayList<Predicate>();

            // predicates.add( cb.gt(root.get("id"),10) );
            // predicates.add( cb.lt(root.get("id"),15) );
            predicates.add( 
                cb.or(
                    cb.and(
                        cb.greaterThanOrEqualTo(root.get("id"),10),
                        cb.lessThanOrEqualTo(root.get("id"),15)
                    ), 
                    cb.equal(root.get("id"),5)
                )
 
            );

            query.where(predicates.toArray(new Predicate[]{}));

            List<Course> list = entityManager.createQuery(query).getResultList();
    
            list.forEach(x -> System.out.println(x.getName()+"_"+ x.getId()) );



            //Session session = entityManager.unwrap(Course.class);
            //session.getSessionFactory()
            //session.beginTransaction() 

    } 



    public void temp2(){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Course> root = query.from(Course.class);

        //Path<Object> idPath = root.get("id");
        //Path<Object> namePath = root.get("name");
        //query.select(cb.array(idPath,namePath));
   
        query.multiselect(root.get("id"),root.get("name"));

        List<Object[]> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> System.out.println((Long)x[0] + " : " + (String)x[1] ) );
    }
    public void temp3(){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<CourseDtoOutput> query = cb.createQuery(CourseDtoOutput.class);
        Root<Course> root = query.from(Course.class);

 
   
        query.select(cb.construct(CourseDtoOutput.class, root.get("id"),root.get("name")));

        List<CourseDtoOutput> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> System.out.println(x.getId() + " - " + x.getName()) );
    }




    public void temp4(){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Course> root = query.from(Course.class);


        query.multiselect(root.get("id"),root.get("name"));


        List<Tuple> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> System.out.println( (Long)x.get(root.get("id")) + " -- " + (String)x.get(root.get("name")) ) );
    }



    public void temp5(){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<Topic> topicRoot = query.from(Topic.class);
        Root<Course> courseRoot = query.from(Course.class);

        query.multiselect(topicRoot, courseRoot);

        Predicate topicRestrictions = cb.equal(topicRoot.get("id"), 1);
        Predicate courseRestrictions = cb.ge(courseRoot.get("id"), 5);

        query.where(cb.and(topicRestrictions, courseRestrictions));


        List<Tuple> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> {
            System.out.println(  
                "Topic: " + 
                ((Topic)x.get(0)).getId() + ", " + ((Topic)x.get(0)).getName() + "\n" +
                "Course: " + 
                ((Course)x.get(1)).getId() + ", " + ((Course)x.get(1)).getName() + "\n"
            );
        
        });
    }


    public void temp6(){
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> root = query.from(Course.class); 

        root.join("topic");
        // root.fetch("topic");     // eager load

        List<Course> list = entityManager.createQuery(query).getResultList();

        list.forEach(x -> System.out.println(x.getId() + " - " + x.getName() + " - " + x.getTopic().getName() ) );


    }
    public void temp6_2(){
        //todo group by
      


    }

    public void temp7(){
        Long id = 2L;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> root = query.from(Course.class); 


        ParameterExpression<Long> idParam = cb.parameter(Long.class); 

        query.where(cb.equal(root.get("id"), idParam));

 
        //SessionFactory sessionFactory = entityManager.unwrap(SessionFactory.class);//.getSessionFactory();
          //SessionFactory sessionFactory = session.getSessionFactory()
        
        //Session session = sessionFactory.openSession();
         
        //Query<Course> sessionQuery = session.createQuery( query )
        TypedQuery<Course> sessionQuery = entityManager.createQuery( query );
        sessionQuery.setParameter( idParam, id );
 

        List<Course> list = sessionQuery.getResultList();
        
        //entityManager.getTransaction().begin();
        //entityManager.getTransaction().commit(); 
 

       // session.close();

        list.forEach(x -> System.out.println(x.getId() + " - " + x.getName() + " - " + x.getTopic().getName() ) );

    }

    public void temp8(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Course> root = query.from(Course.class);

        // query.select(cb.count(root));
        // query.select(cb.max(root.get("id")));
        // countDistinct
        query.select(cb.sum(root.get("id")));


        Long result = entityManager.createQuery(query).getSingleResult();

        System.out.println("result: " +result);
    
    }

}