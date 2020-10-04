package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    private static SessionFactory sessionFactory = null;

    static {
        Configuration cfg = new Configuration();
        cfg.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/hibernate?serverTimezone=UTC&useSSL=false");
        cfg.setProperty("hibernate.connection.username", "root");
        cfg.setProperty("hibernate.connection.password", "root");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        cfg.setProperty("show_sql", "true");
        /*  hibernate.hbm2ddl.auto property values:
            1. update (Update the schema if necessary)
            2. create (create the schema and destroy previous data)
            3. create-drop (create and then destroy the schema at the end of the session)
            4. none (disable ddl handling)
            5. validate (validate the schema , make no changes to the database)
        */
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.addAnnotatedClass(org.tyaa.javaee.hibernate.annotations.entity.User.class);
        cfg.addAnnotatedClass(org.tyaa.javaee.hibernate.annotations.entity.Role.class);
        cfg.addAnnotatedClass(org.tyaa.javaee.hibernate.annotations.entity.UserDetails.class);
        cfg.addAnnotatedClass(org.tyaa.javaee.hibernate.annotations.entity.Repository.class);
        sessionFactory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
