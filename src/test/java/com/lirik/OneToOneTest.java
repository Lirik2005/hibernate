package com.lirik;

import com.lirik.entity.users.Profile;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class OneToOneTest {

    @Test
    void checkOneToOne() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

//        User user = User.builder()
//                        .userName("test24@gmail.com")
//                        .build();

        Profile profile = Profile.builder()
                                 .language("ru")
                                 .street("Street, 12")
                                 .build();
//        profile.setUser(user);
//
//        session.persist(user);
        session.getTransaction().commit();
    }

    @Test
    void getUserTest() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, 8L);
        System.out.println(user);

        session.getTransaction().commit();
    }

}
