package com.library.repository;

import com.library.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    @Query(value = "select * from users left join savedBooks on id = savedBooks.userId left join books on savedBooks.bookId = books.id where users.name = '?1';",
            nativeQuery = true)
    User auth(String name);

    @Query(value = "insert into users(name, password) values ('?1', '?2')",
            nativeQuery = true)
    void createAccount(String name, String password);

    @Query(value = "update users set password = '?2' where id = ?1",
            nativeQuery = true)
    void changePassword(int userId, String password);

}
