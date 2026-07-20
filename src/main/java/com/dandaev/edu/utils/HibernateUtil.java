package com.dandaev.edu.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

    static {
        try {
            LOGGER.info("Initializing Hibernate SessionFactory...");
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(com.dandaev.edu.entity.PlayerEntity.class);
            configuration.addAnnotatedClass(com.dandaev.edu.entity.MatchEntity.class);

            sessionFactory = configuration.buildSessionFactory();
            LOGGER.info("Hibernate SessionFactory created successfully.");
        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            LOGGER.info("Closing Hibernate SessionFactory...");
            sessionFactory.close();
            LOGGER.info("Hibernate SessionFactory closed.");
        }
    }
}