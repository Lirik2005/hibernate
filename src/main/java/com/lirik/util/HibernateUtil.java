package com.lirik.util;

import com.lirik.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {

        /**
         * Что такое Configuration и SessionFactory можно посмотреть в комментариях в моем классе HibernateRunner
         */

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
