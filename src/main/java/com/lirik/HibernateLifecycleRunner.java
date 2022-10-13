package com.lirik;

import com.lirik.entity.User;
import com.lirik.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateLifecycleRunner {

    public static void main(String[] args) {

        User user = User.builder()  // В данной строке user не связан ни с одной из сессий и находится в состоянии transient
                        .userName("ivan@gmail.com")
                        .lastName("Ivanov")
                        .firstName("Ivan")
                        .build();

        /**
         * Некоторая информация по записям ниже также присутствует в моем классе HibernateRunner
         */

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.persist(user); // В данной строке user в состоянии persistent для session1 и в состоянии transient для session2

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();


                session2.getTransaction().commit();
            }
        }


    }

}
