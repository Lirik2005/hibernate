package com.lirik;

import com.lirik.entity.users.Payment;
import com.lirik.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import java.util.Date;

@Slf4j
public class HibernateRunnerNew {

    @Transactional  // управляет транзакцией, чтобы не обрабатывать exception
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Payment payment = session.find(Payment.class, 1L);
                payment.setAmount(payment.getAmount() + 10);

                session.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                AuditReader auditReader = AuditReaderFactory.get(session2);
                Payment oldPayment = auditReader.find(Payment.class, 2L, new Date(1672996251582L));
                System.out.println();

                session2.getTransaction().commit();
            }
        }
    }
}