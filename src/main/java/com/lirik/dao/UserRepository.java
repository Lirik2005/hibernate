package com.lirik.dao;

import com.lirik.entity.users.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
