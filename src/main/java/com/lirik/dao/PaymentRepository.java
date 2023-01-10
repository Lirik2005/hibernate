package com.lirik.dao;

import com.lirik.entity.users.Payment;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }
}
