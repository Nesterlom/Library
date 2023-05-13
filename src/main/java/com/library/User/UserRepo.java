package com.library.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User getUserById(int userId);

    @Query(value = "select * from users", nativeQuery = true)
    List<User> getUsers();

    @Modifying
    @Query(value = "update users set login = ?2 where login = ?1",nativeQuery = true)
    void updateLoginByLogin(String login, String newLogin);

    @Modifying
    @Query(value = "update users set password = ?2 where login = ?1",nativeQuery = true)
    void updatePasswordByLogin(String login, String password);

    @Modifying
    @Query(value = "update users set name = ?2 where login = ?1",nativeQuery = true)
    void updateNameByLogin(String login, String name);

    @Modifying
    @Query(value = "update users set surname = ?2 where login = ?1",nativeQuery = true)
    void updateSurnameByLogin(String login, String surname);

    void deleteById(int userId);

    void deleteByLogin(String login);
}
