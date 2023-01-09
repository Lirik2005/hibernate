package com.lirik;

import com.lirik.entity.users.Payment;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunnerNew {

    @Transactional  // управляет транзакцией, чтобы не обрабатывать exception
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            User user = null;

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                User user1 = session.find(User.class, 1L);

                session.getTransaction().commit();
            }
        }
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                User user = session2.find(User.class, 1L);

                session2.getTransaction().commit();
            }
        }
    }
}