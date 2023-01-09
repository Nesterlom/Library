package com.library;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

import java.util.List;

//Here are some actions connected with database.
public class DBWorker {
    private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private final String URL = System.getenv("my_sql_url");
    private final String USERNAME = System.getenv("my_sql_username");
    private final String PASSWORD = System.getenv("my_sql_pass");
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private volatile static DBWorker worker;
    private final Inputer inputer = Inputer.getInstance();

    {
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUsername(System.getenv("my_sql_username"));
        dataSource.setUrl(System.getenv("my_sql_url"));
        dataSource.setPassword(System.getenv("my_sql_pass"));
    }

    public synchronized static DBWorker getInstance() {
        if (worker == null) {
            worker = new DBWorker();
        }

        return worker;
    }

    private boolean isBookInSavedBooks(int bookId, int userId) {
        String sql = String.format("select bookId from savedBooks where bookId = %d and userId = %d", bookId, userId);

        try {
            Integer id = (Integer) jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }


    public int auth(String name, String password) throws SQLException {
        //we check password and name, that entered user, with names and passwords in database.
        try {
            String sql = String.format("select * from users where name = '%s'", name);
            User user = (User) jdbcTemplate.queryForObject(sql, new UserMapper());

            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId();
            }
        } catch (EmptyResultDataAccessException e) {
        }

        return -1;
    }

    public List<Book> getSavedBooks(int limit, int offset, int userId) {
        String sql = String.format("select * from books join savedBooks on savedBooks.bookId = books.id where userId = %d limit %d offset %d;", userId, limit, offset);

        return jdbcTemplate.query(sql, new BookMapper());
    }

    public List<Book> getBooks() {
        String query = String.format("select * from books");

        return jdbcTemplate.query(query, new BookMapper());
    }

    public List<Book> getBooks(int limit, int offset) {
        String query = String.format("select * from books limit %s offset %d", limit, offset);

        return jdbcTemplate.query(query, new BookMapper());
    }


    public void setNewPassword(String userName, String newPassword) throws SQLException {
        String query = String.format("update users set password = '%s' where name = '%s'", passwordEncoder.encode(newPassword), userName);

        jdbcTemplate.execute(query);
    }

    public void findBook() throws SQLException {
        //In this method we use a regular expressions to find book.
        String sql = "select * from books";

        Searcher searcher = Searcher.getInstance();
        searcher.chooseStrategyAndFindBook();
    }

    public boolean saveBook(int userId) throws SQLException {
        int bookId = inputer.input();
        if (!isBookInSavedBooks(bookId, userId)) {
            try {
                String sql = String.format("insert into savedBooks(userId, bookId) values (%d, %d)", userId, bookId);
                jdbcTemplate.execute(sql);
                return true;
            } catch (DataIntegrityViolationException e) {
            }
        }
        return false;
    }

    public boolean deleteBook(int userId) throws SQLException {
        int bookId = inputer.input();
        if (isBookInSavedBooks(bookId, userId)) {
            String sql2 = String.format("delete from savedBooks where userId = %d and bookId = %d", userId, bookId);
            jdbcTemplate.execute(sql2);
            return true;
        }
        return false;
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
