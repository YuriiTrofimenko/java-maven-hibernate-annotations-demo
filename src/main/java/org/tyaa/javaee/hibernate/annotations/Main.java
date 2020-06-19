package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.Repository;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        // EntityManager em = sessionFactory.createEntityManager();
        Session session =
                sessionFactory.openSession();
        System.out.println("Hello Hibernate Annotations!");

        /* Role r = new Role();
        r.setTitle("admin");
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();

        User u = new User();
        u.setFirstName("f1");
        u.setLastName("l1");
        u.setAge(20);
        u.setRole(r);

        UserDetails userDetails = new UserDetails("Lorem ipsum dolor");
        userDetails.setUser(u);
        u.setUserDetails(userDetails);

        em.getTransaction().begin();
        em.persist(userDetails);
        em.persist(u);
        em.getTransaction().commit();

        User fromDbUser = em.find(User.class, u.getId());
        System.out.println(fromDbUser.getFirstName());
        System.out.println(fromDbUser.getLastName());
        System.out.println(fromDbUser.getRole().getTitle());
        System.out.println(fromDbUser.getUserDetails().getText());

        System.out.println("***");

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

        users.get(0).getRepositories().add(repositories.get(1));
        repositories.get(1).getUsers().add(users.get(0));
        em.getTransaction().begin();
        em.persist(users.get(0));
        em.getTransaction().commit();

        em.find(User.class, users.get(0).getId())
                .getRepositories().forEach(repository -> {
            System.out.println(repository.getData());
        });

        repositories.get(1).getUsers().forEach(user -> {
            System.out.println(user.getFirstName());
        }); */

        List<Role> roles = null;
        User user = null;
        try {
            session.beginTransaction();
            String newRole = "content manager";
            // String newRole = "content manager'); DELETE FROM Roles;";// "content manager";
            // String newRole = "content manager'); DELETE FROM `Roles`; INSERT INTO `Roles` (`title`) VALUES ('crack!";
            // INSERT INTO Roles (title) VALUES ('content manager')
            // DELETE FROM Roles
            // INSERT INTO Roles (title) VALUES ('content manager'); DELETE FROM Roles;
            // content manager'); DELETE FROM Roles;

            // INSERT INTO Roles (title) VALUES ('content manager'); DELETE FROM Roles; INSERT INTO Roles (title) VALUES ('crack!')
            // INSERT INTO Roles (title) VALUES ('content manager'); DELETE FROM Roles; INSERT INTO Roles (title) VALUES ('crack!
            // content manager'); DELETE FROM Roles; INSERT INTO Roles (title) VALUES ('crack!
            // SQL
            //SQLQuery queryInsert =
                    //session.createSQLQuery("INSERT INTO Roles (title) VALUES (?)");
                    // session.createSQLQuery("INSERT INTO `Roles` (`title`) VALUES ('" + newRole + "')");
            //queryInsert.setParameter(1, "simple");
            //queryInsert.executeUpdate();

            // JPQL / HQL
            /* Query query =
                    // session.createQuery("SELECT r FROM Role AS r INNER JOIN FETCH r.users as users");
                    session.createQuery("SELECT DISTINCT r FROM Role AS r LEFT OUTER JOIN FETCH r.users as u");
            roles = query.getResultList();

            Query query2 =
                    session.createQuery("SELECT r FROM Role AS r WHERE r.title='admin'");
            System.out.println(((Role)query2.getSingleResult()).getUsers().size()); */

            /* Хотим получить список всех ролей, используя только Java без помощи SQL, JPQL, HQL */
            // Создаем строитель, при помощи оторого можно:
            // 1. создавать основу запроса, указывающую, к какой сущности будет запрос;
            // 2. объекты условий запроса
            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Создаем основу - объект запроса, удобный для конструирования (указания деталей запроса)
            CriteriaQuery<Role> cr = cb.createQuery(Role.class); // An Empty Raw Query Object
            // Добавляем к "конструктор" фрагмент запроса "FROM"
            Root<Role> root = cr.from(Role.class); // FROM Roles
            // Добавляем к "конструктор" фрагмент запроса "SELECT"
            cr.select(root); // SELECT *
            // Получаем в "конструкторе" CriteriaQuery запрос вида "SELECT * FROM Roles"
            // Создаем типизированный объект запроса, который можно выполнить
            // (из объекта-конструктора запросов CriteriaQuery)
            TypedQuery<Role> query = session.createQuery(cr);
            // Query query = session.createQuery(cr);
            // Отправляем запрос к БД на выполнение -
            // получаем список результатов - объектов Role
            query.getResultList().forEach(
                    (role -> {
                        System.out.println("Role:");
                        System.out.println(role.getTitle());
                        System.out.println("Users:");
                        role.getUsers().forEach(user1 -> System.out.println(user1.getFirstName()));
                        System.out.println("***");
                    }
                    )
            );
            /* query.getResultList().forEach(
                    (role ->
                            System.out.println(((Role)role).getTitle())
                    )
            ); */
            /* ((List<Role>)query.getResultList()).forEach(
                    (role ->
                            System.out.println(role.getTitle())
                    )
            ); */

            System.out.println("***");
            CriteriaQuery<Role> cr2 = cb.createQuery(Role.class);
            Root<Role> root2 = cr2.from(Role.class);
            // На этот раз добавляем в объект-конструктор кроме фрагмента SELECT
            // еще и фрагмент WHERE (WHERE Roles.title <> 'guest')
            cr2.select(root)
                    .where(cb.not(cb.equal(root.get("title"), "admin")));
            TypedQuery<Role> query2 = session.createQuery(cr2);
            query2.getResultList().forEach(
                    (role -> {
                        System.out.println("Role:");
                        System.out.println(role.getTitle());
                        System.out.println("Users:");
                        role.getUsers().forEach(user1 -> System.out.println(user1.getFirstName()));
                        System.out.println("***");
                    }
                    )
            );
            System.out.println("***");
            System.out.println("***");
            CriteriaQuery<User> cu = cb.createQuery(User.class);
            Root<User> userRoot = cu.from(User.class);
            // List<Predicate> predicates = new ArrayList<>();
            cu.select(userRoot);
            // Добавляем в консруктор фрагмент INNER JOIN Roles ON (Users.role_id = Roles.id),
            // чтобы заполнять поля role в объектах User
            userRoot.fetch("role");
            cu.where(cb.equal(root.get("id"), 1L));
            TypedQuery<User> userTypedQuery = session.createQuery(cu);
            user = userTypedQuery.getSingleResult();
            System.out.println(user.getFirstName() + " - " + user.getRole().getTitle());

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
        // System.out.println(roles.size());
        // roles.forEach((role -> System.out.println(role.getTitle())));

        // em.close();
        // sessionFactory.close();
    }
}
