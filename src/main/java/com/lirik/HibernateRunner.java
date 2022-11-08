package com.lirik;

import com.lirik.entity.users.Birthday;
import com.lirik.entity.users.PersonalInfo;
import com.lirik.entity.users.Role;
import com.lirik.entity.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {

        /**
         * Файл hibernate.cfg.xml в папке resources необходим для настройки и конфигурации SessionFactory. SessionFactory это аналог
         * Connection из JDBC и нужен для открытия соединения с БД
         */

        Configuration configuration = new Configuration();

        /**
         * Запись ниже нужна для того, чтобы Hibernate отслеживал сущность User
         */

        configuration.addAnnotatedClass(User.class);

        /**
         * Запись ниже используется чтобы избавится от аннотации @Convert(converter = BirthdayConverter.class) в нашей Entity, если
         * подобный конвертер используется в большом количестве сущностей
         */

//         configuration.addAttributeConverter(new BirthdayConverter(), true);

        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();   // обязательно открываем транзакцию ВРУЧНУЮ и выполняем необходимы код

            User user = User.builder()
                            .userName("ivan@gmail.com")
                            .personalInfo(PersonalInfo.builder()
                                                      .lastName("Ivanov")
                                                      .firstName("Ivan")
                                                      .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                                                      .build())
                            .info("""
                                          {
                                          "name": "Ivan",
                                          "id": 25
                                          }
                                          """)
                            .role(Role.ADMIN)
                            .build();

            /**
             * Операции persist, merge, remove осуществляются по id юзера
             */

//             session.persist(user);  // Добавляет пользователя в базу данных
//             session.merge(user);  // Делает update юзера в БД. Если в БД такого юзера нет, то метод его туда запишет (saveOrUpdate)
//             session.remove(user); // Удаляет юзера из БД
            User userFromDb = session.get(User.class, "ivan@gmail.com");// Получаем из БД соответствующего юзера
            session.getTransaction().commit(); // обязательно закрываем транзакцию ВРУЧНУЮ после выполнения необходимого кода
        }


    }
}
