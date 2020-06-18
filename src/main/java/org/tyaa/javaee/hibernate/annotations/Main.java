package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.User;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        EntityManager em = sessionFactory.createEntityManager();
        System.out.println("Hello Hibernate Annotations!");

        User u = new User();
        u.setFirstName("f1");
        u.setLastName("l1");
        u.setAge(20);
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        User fromDbUser = em.find(User.class, 1L);
        System.out.println(fromDbUser.getFirstName());
        System.out.println(fromDbUser.getLastName());

        em.close();
        sessionFactory.close();
    }
}
