package com.lirik.util;

import com.lirik.entity.Audit;
import com.lirik.entity.chat.Chat;
import com.lirik.entity.chat.UserChat;
import com.lirik.entity.companies.Company;
import com.lirik.entity.users.Payment;
import com.lirik.entity.users.Profile;
import com.lirik.entity.users.User;
import com.lirik.interceptor.GlobalInterceptor;
import com.lirik.listener.AuditTableListener;
import com.lirik.mapping.Manager;
import com.lirik.mapping.Programmer;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {

        /**
         * Что такое Configuration и SessionFactory можно посмотреть в комментариях в моем классе HibernateRunner
         */

        Configuration configuration = buldConfiguration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        registerListeners(sessionFactory);

        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
//        SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
//
//        EventListenerRegistry eventListenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
//        AuditTableListener auditTableListener = new AuditTableListener();
//        eventListenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
//        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
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
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Audit.class);
        configuration.setInterceptor(new GlobalInterceptor());
        return configuration;
    }
}
