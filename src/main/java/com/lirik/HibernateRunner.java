package com.lirik;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {

    public static void main(String[] args) {

        /**
         * Файл hibernate.cfg.xml в папке resources необходим для настройки и конфигурации SessionFactory. SessionFactory это аналог
         * Connection из JDBC и нужен для открытия соединения с БД
         */

        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            System.out.println("OK");
        }
    }

}
