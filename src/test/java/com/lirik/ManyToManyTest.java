package com.lirik;

import com.lirik.entity.chat.Chat;
import com.lirik.entity.chat.UserChat;
import com.lirik.entity.users.User;
import com.lirik.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ManyToManyTest {

    @Test
    void checkManyToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, 8L);

        Chat chat = session.get(Chat.class, 1L);

        UserChat userChat = UserChat.builder()
//                                    .createdAt(Instant.now())
//                                    .createdBy(user.getUserName())
                                    .build();

        userChat.setUser(user);
        userChat.setChat(chat);

        session.persist(userChat);

        session.getTransaction().commit();
    }

}
