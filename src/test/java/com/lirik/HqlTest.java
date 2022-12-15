package com.lirik;

import com.lirik.entity.users.User;
import com.lirik.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HqlTest {

    @Test
    void checkHql() {
        @Cleanup SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        /**
         * Символ u в запросе это всего лишь алианс. То есть в запросе происходит выборка юзеров и класса Юзер (т. е. в HQL мы указываем
         * не название таблицы, а название класса)
         */

        Query<User> query1 = session.createQuery("select u from User u where u.personalInfo.firstName = 'Ivan'", User.class);
        List<User> userList1 = query1.list();

        String name = "Ivan";
        Query<User> query2 = session.createQuery("select u from User u where u.personalInfo.firstName = ?1", User.class);
        List<User> userList2 = query2.setParameter(1, name).list();

        /**
         * Способ написания запроса с именованными параметрами (firstname - именованный параметр и может иметь любое название (firstname,
         * firstName, first_name, name и так далее)) - наиболее эффективный!!!
         */

        Query<User> query3 = session.createQuery("select u from User u where u.personalInfo.firstName = :firstname", User.class);
        List<User> userList3 = query3.setParameter("firstname", name).list();

        session.getTransaction().commit();

    }

}
