package com.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements RowMapper {
    User user = new User();
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        do{
            if(rs.getInt("books.id") != 0) {
                Book book = new Book();
                book.setId(rs.getInt("books.id"));
                book.setName(rs.getString("books.name"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                user.addBook(book);
            }

            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        } while(rs.next());

        return user;
    }
}
