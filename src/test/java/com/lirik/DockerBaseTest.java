package com.lirik;

import com.lirik.entity.companies.Company;
import com.lirik.entity.users.User;
import com.lirik.mapping.Language;
import com.lirik.mapping.Manager;
import com.lirik.mapping.Programmer;
import com.lirik.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class DockerBaseTest {

    @Test
    void checkDockerBase() {
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Company google = Company.builder()
                                .name("Google")
                                .build();

        session.persist(google);

        Programmer programmer = Programmer.builder()
                                          .userName("lirik2005@yandex.ru")
                                          .language(Language.JAVA)
                                          .company(google)
                                          .build();
        session.persist(programmer);

        Manager manager = Manager.builder()
                                 .userName("lirik@yandex.ru")
                                 .company(google)
                                 .build();
        session.persist(manager);
        session.flush();

        session.clear();

        Programmer programmer1 = session.get(Programmer.class, 1L);
        User manager1 = session.get(User.class, 2L);

        session.getTransaction().commit();
    }
}
