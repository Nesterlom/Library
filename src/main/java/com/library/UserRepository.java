package com.library;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class UserRepository {
    // for users table
    private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private volatile static UserRepository userRepository;

    {
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUsername(System.getenv("my_sql_username"));
        dataSource.setUrl(System.getenv("my_sql_url"));
        dataSource.setPassword(System.getenv("my_sql_pass"));
    }

    public synchronized static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }

        return userRepository;
    }

    public int auth(String name, String password) throws SQLException {
        //we check password and name, that entered user, with names and passwords in database.
        try {
            //String sql = String.format("select * from users where name = '%s'", name);
            String sql = String.format("select * from users join savedBooks on id = savedBooks.userId join books on savedBooks.bookId = books.id where users.name = '%s';", name);
            User user = (User) jdbcTemplate.queryForObject(sql, new UserMapper());

            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId();
            }
        } catch (EmptyResultDataAccessException e) {}

        return -1;
    }

    public void setNewPassword(int userId, String newPassword) throws SQLException {
        String query = String.format("update users set password = '%s' where id = '%d'", passwordEncoder.encode(newPassword), userId);

        jdbcTemplate.execute(query);
    }

    public boolean createNewAccount(String userName, String password) throws SQLException {
        //Here we add new user to database.
        String sql = String.format("select name from users where name = '%s'", userName);
        String name = jdbcTemplate.queryForObject(sql, String.class);

        if (!name.isEmpty()) {
            return false;
        } else {
            String sql2 = String.format("insert into users(name, password) values ('%s', '%s')", userName, passwordEncoder.encode(password));
            jdbcTemplate.execute(sql2);
            return true;
        }
    }
}
