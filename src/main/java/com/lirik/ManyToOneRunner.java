package com.lirik;

import com.lirik.entity.companies.Company;
import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class ManyToOneRunner {

    public static void main(String[] args) {

        Company google = Company.builder()
                                .name("Google")
                                .build();

        User petr = User.builder()
                        .userName("petr@gmail.com")
                        .personalInfo(PersonalInfo.builder()
                                                  .lastName("Petrov")
                                                  .firstName("Petr")
                                                  .birthDate(new Birthday(LocalDate.of(2009, 3, 16)))
                                                  .build())
                        .company(google)
                        .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                session.persist(google);
                session.persist(petr);

                session.getTransaction().commit();
            }
        }
    }
}
