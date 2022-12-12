package com.lirik;

import com.lirik.entity.companies.Company;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class AddAndDeleteCompanyTest {

    @Test
    void addUserToNewCompany() {

        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company facebook = Company.builder()
                                  .name("Facebook")
                                  .build();

        User sveta = User.builder()
                         .userName("sveta@gmail.com")
                         .build();

        facebook.addUser(sveta);

        session.persist(facebook);

        session.getTransaction().commit();
    }

    @Test
    void deleteCompany() {

        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 2);

        session.remove(company);

        session.getTransaction().commit();
    }
}
