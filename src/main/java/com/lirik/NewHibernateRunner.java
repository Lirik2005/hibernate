package com.lirik;

import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class NewHibernateRunner {

    public static void main(String[] args) {

//        User user = User.builder()
//                        .userName("petr@gmail.com")
//                        .personalInfo(PersonalInfo.builder()
//                                                  .lastName("Petrov")
//                                                  .firstName("Petr")
//                                              .birthDate(new Birthday(LocalDate.of(2009, 3, 16)))
//                                                  .build())
//                        .build();
     //   log.info("User entity is in transient state, object {}", user);

        /**
         * Некоторая информация по записям ниже также присутствует в моем классе HibernateRunner
         */

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

           //     session.persist(user);

                session.getTransaction().commit();
            }
        }
    }
}