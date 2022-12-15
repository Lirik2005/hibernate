package com.lirik;

import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class LoggerRunner {

    /**
     * Можно инициализировать Logger как указано ниже, а можно использовать соответствующую аннотацию от Lombok, которая называется @Slf4j
     */

//    private static final Logger log = LoggerFactory.getLogger(LoggerRunner.class);
    public static void main(String[] args) {

//        User user = User.builder()
//                        .userName("ivan@gmail.com")
//                        .build();

        /**
         * Фигурные скобки это наш параметр user. Сколько параметров мы передаем в сообщение, столько фигурных скобок у нас будет
         */

   //     log.info("User entity is in transient state, object {}", user);

        /**
         * Ниже приведены все варианты логирования, которые создают сообщения определенного уровня приоритета
         * trace --> debug --> info --> warn --> error
         */

//        log.trace("User entity is in transient state");
//        log.debug("User entity is in transient state");
//        log.warn("User entity is in transient state");
//        log.error("User entity is in transient state");

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try (session) {
                Transaction transaction = session.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

            //    session.persist(user);
          //      log.trace("User is in persistent state: {}, session {}", user, session);

                session.getTransaction().commit();
            }
         //   log.warn("User is in detached state: {}, session is closed {}", user, session);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
