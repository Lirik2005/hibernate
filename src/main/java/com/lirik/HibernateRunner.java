package com.lirik;

import com.lirik.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {

        /**
         * Файл hibernate.cfg.xml в папке resources необходим для настройки и конфигурации SessionFactory. SessionFactory это аналог
         * Connection из JDBC и нужен для открытия соединения с БД
         */

        Configuration configuration = new Configuration();

        /**
         * Запись ниже нужна для того, чтобы Hibernate отслеживал сущность User
         */

        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();   // обязательно открываем транзакцию ВРУЧНУЮ и выполняем необходимы код

            User user = User.builder()
                            .userName("ivan@gmail.com")
                            .firstName("Ivan")
                            .lastName("Ivanov")
                            .birthDate(LocalDate.of(2000, 1, 19))
                            .age(22)
                            .build();
            session.persist(user);

            session.getTransaction().commit(); // обязательно закрываем транзакцию ВРУЧНУЮ после выполнения необходимого кода
        }
    }
}
