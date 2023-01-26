package com.library.repository;

import com.library.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
}
