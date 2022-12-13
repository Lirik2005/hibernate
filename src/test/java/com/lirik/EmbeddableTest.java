package com.lirik;

import com.lirik.entity.companies.Company;
import com.lirik.entity.companies.LocaleInfo;
import com.lirik.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class EmbeddableTest {

    @Test
    void embeddableHibernateTest() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 1L);
        company.getLocales().add(LocaleInfo.of("ru", "описание на русском"));
        company.getLocales().add(LocaleInfo.of("en", "english description"));

//        System.out.println(company.getLocales());

        session.getTransaction().commit();
    }

}
