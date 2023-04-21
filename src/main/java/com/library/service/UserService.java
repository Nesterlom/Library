package com.library.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class UserService {
    EntityManager entityManager;

    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
