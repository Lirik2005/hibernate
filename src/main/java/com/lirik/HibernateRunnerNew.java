package com.lirik;

import com.lirik.entity.users.Payment;
import com.lirik.util.HibernateUtil;
import com.lirik.util.TestDataImporter;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunnerNew {

    @Transactional  // управляет транзакцией, чтобы не обрабатывать exception
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            TestDataImporter.importData(sessionFactory);
            session.setDefaultReadOnly(true);
            session.beginTransaction();

            Payment payment = session.find(Payment.class, 1L);

            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }
    }
}