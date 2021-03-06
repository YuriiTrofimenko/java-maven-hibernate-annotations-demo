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
    public static void main(String[] args) throws InterruptedException {
        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        // EntityManager em = sessionFactory.createEntityManager();
        Session session =
                sessionFactory.openSession();
        System.out.println("Hello Hibernate Annotations!");

        /* Setup DB Data */
        SetupDb.insertData();

        List<Role> roles = null;
        User user = null;
        try {
            session.beginTransaction();
            String newRole = "content manager";
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
            // Создаем строитель Критериа, при помощи которого можно:
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
            cr = cb.createQuery(Role.class);
            root = cr.from(Role.class);
            // На этот раз добавляем в объект-конструктор кроме фрагмента SELECT
            // еще и фрагмент WHERE (WHERE Roles.title <> 'guest')
            cr.select(root)
                    .where(cb.not(cb.equal(root.get("title"), "admin")));
            TypedQuery<Role> query2 = session.createQuery(cr);
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
    }
}
