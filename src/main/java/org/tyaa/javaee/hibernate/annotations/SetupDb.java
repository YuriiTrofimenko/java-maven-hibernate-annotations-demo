package org.tyaa.javaee.hibernate.annotations;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

import org.tyaa.javaee.hibernate.annotations.entity.Repository;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

public class SetupDb {

    public static void insertData() {
        // получение сеанса реализации JPA
        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        EntityManager em = sessionFactory.createEntityManager();
        try {
            // создание первой роли...
            Role r = new Role();
            r.setTitle("admin");
            // ... и ее сохранение в БД
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            // создание первого пользователя ...
            User u = new User();
            u.setFirstName("f1");
            u.setLastName("l1");
            u.setAge(20);
            u.setRole(r);
            // ... , подробностей о нем
            UserDetails userDetails = new UserDetails("Lorem ipsum dolor");
            userDetails.setUser(u);
            u.setUserDetails(userDetails);
            // ... и сохранение их в БД
            em.getTransaction().begin();
            em.persist(userDetails);
            em.persist(u);
            em.getTransaction().commit();
            // поиск первого пользователя и вывод всей связанной с ним информации
            User fromDbUser = em.find(User.class, u.getId());
            System.out.println(fromDbUser.getFirstName());
            System.out.println(fromDbUser.getLastName());
            System.out.println(fromDbUser.getRole().getTitle());
            System.out.println(fromDbUser.getUserDetails().getText());

            System.out.println("***");
            // создание еще троих пользователей,
            // связанной с ними информации,
            // и соханение в БД
            List<User> users = new ArrayList<>();
            List<Repository> repositories = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                users.add(new User());
                User newUser = users.get(i);
                newUser.setFirstName("f1" + i);
                newUser.setLastName("l1" + i);
                newUser.setAge(21 + i);
                newUser.setRole(r);
                UserDetails newUserDetails = new UserDetails("Lorem ipsum dolor text " + i);
                newUserDetails.setUser(newUser);
                newUser.setUserDetails(newUserDetails);
                repositories.add(new Repository("Lorem ipsum dolor data " + i));
                Repository newRepository = repositories.get(i);
                newRepository.getUsers().add(newUser);
                newUser.getRepositories().add(newRepository);
                em.getTransaction().begin();
                em.persist(newUserDetails);
                em.persist(newRepository);
                em.persist(newUser);
                em.getTransaction().commit();
            }
            // Добавление первому пользователю второго репозитория
            users.get(0).getRepositories().add(repositories.get(1));
            repositories.get(1).getUsers().add(users.get(0));
            em.getTransaction().begin();
            em.persist(users.get(0));
            em.getTransaction().commit();
            // Поиск первого пользователя и вывод его репозиториев
            em.find(User.class, users.get(0).getId())
                    .getRepositories().forEach(repository -> {
                System.out.println(repository.getData());
            });
            // Поиск второго репозитррия и вывод его пользователей
            repositories.get(1).getUsers().forEach(user -> {
                System.out.println(user.getFirstName());
            });
        } finally {
            em.close();
        }
    }
}
