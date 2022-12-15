package com.lirik.util;

import com.lirik.entity.chat.Chat;
import com.lirik.entity.chat.UserChat;
import com.lirik.entity.companies.Company;
import com.lirik.entity.users.Profile;
import com.lirik.entity.users.User;
import com.lirik.mapping.Manager;
import com.lirik.mapping.Programmer;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {

        /**
         * Что такое Configuration и SessionFactory можно посмотреть в комментариях в моем классе HibernateRunner
         */

        Configuration configuration = buldConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buldConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Chat.class);
        configuration.addAnnotatedClass(UserChat.class);
        configuration.addAnnotatedClass(Programmer.class);
        configuration.addAnnotatedClass(Manager.class);
        return configuration;
    }
}
