package com.lirik;

import com.lirik.entity.companies.Company;
import com.lirik.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class InMemoryBaseTest {

    @Test
    void checkH2() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = Company.builder()
                                 .name("MyCompany")
                                 .build();

        session.persist(company);
        session.getTransaction().commit();
    }
}
