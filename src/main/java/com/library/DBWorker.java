package com.library;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

//Here are some actions connected with database.
public class DBWorker {
    private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private final String URL = System.getenv("my_sql_url");
    private final String USERNAME = System.getenv("my_sql_username");
    private final String PASSWORD = System.getenv("my_sql_pass");
    private volatile static DBWorker worker;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

    private boolean checkBook(int bookId) {
        String sql = "select id from books";
        List<Integer> ids = jdbcTemplate.query(sql, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("id");
            }
        });

        if (ids.contains(bookId)) {
            return true;
        }
        return false;
    }

    public int auth(String name, String password) throws SQLException {
        //we check password and name, that entered user, with names and passwords in database.
        try {
            String sql = String.format("select * from users where name = '%s'", name);
            User user = (User) jdbcTemplate.queryForObject(sql, new UserMapper());

            if (passwordEncoder.matches(password, user.getPassword())) {
                return user.getId();
            }
        } catch (EmptyResultDataAccessException e) {}
        return -1;
    }

    public void showSavedBooks(int userId) throws SQLException {
        //In this method we get JSON file from database, which can say about books that user has saved.
        if (userId > 0) {
            String sql = String.format("select bookId from savedBooks where userId = %d", userId);

            List<Integer> ids = jdbcTemplate.query(sql, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt("bookId");
                }
            });

            if (ids.isEmpty()) {
                System.out.println("you dont have saved books");
            } else {
                for (Integer id : ids) {
                    String query = String.format("select * from books where id = %d", id);
                    Book book = (Book) jdbcTemplate.queryForObject(query, new BookMapper());
                    String str;
                    if (book.getYear() > 0) {
                        str = String.format("%d. %s, author - %s", book.getId(), book.getName(), book.getAuthor());
                    } else {
                        str = String.format("%d. %s, author - %s. Year - ", book.getId(), book.getName(), book.getAuthor(), book.getYear());
                    }

                    System.out.println(str);
                }
            }
        }
    }

    public void showBooks() throws SQLException {
        //In this method we print 10 books in console.
        boolean programIsOn = true;
        int limit = 10;
        int offset = 0;
        String str;

        while (programIsOn) {
            String query = String.format("select * from books limit %s offset %d", limit, offset);
            int count = 0;
            List<Book> books = jdbcTemplate.query(query, new BookMapper());
            for (Book book : books) {
                int year = book.getYear();
                if (year != 0) {
                    str = String.format("id - %d. %s, author - %s, year - %d", book.getId(), book.getName(), book.getAuthor(), year);
                } else {
                    str = String.format("id - %d. %s, author - %s", book.getId(), book.getName(), book.getAuthor());
                }
                System.out.println(str);
                count++;
            }
            if (count > 0) {
                System.out.print("""
                        Are you want to see some more books?
                        1.Yes.
                        2.No.
                        """);
                int action = inputer.input();

                switch (action) {
                    case 1 -> {
                        limit += 10;
                        offset += 10;
                    }
                    case 2 -> {
                        programIsOn = false;
                    }
                    default -> {
                        System.out.println("Bad input, please try again.");
                        programIsOn = false;
                    }
                }
            } else {
                System.out.println("Books are over.");
                programIsOn = false;
            }
        }
    }


    public void setNewPassword(String userName, String newPassword) throws SQLException {
        String query = String.format("update users set password = '%s' where name = '%s'", newPassword, userName);

        jdbcTemplate.execute(query);
        System.out.println("Password was changed.");
    }

    public void findBook() throws SQLException {
        //In this method we use a regular expressions to find book.
        String sql = "select * from books";
        List<Book> books = jdbcTemplate.query(sql, new BookMapper());

        Searcher searcher = new Searcher(books);
        searcher.chooseStrategyAndFindBook();
    }

    public void saveBook(int userId) throws SQLException {
        System.out.println("Enter id of a book that you want to add:\n" +
                "(if you dont know the id you can type '0' to close this operation and check id in the 'Find book'.)");
        int bookId = inputer.input();
        if (bookId != 0) {
            if (checkBook(bookId)) {
                String sql2 = String.format("insert into savedBooks(userId, bookId) values (%d, %d)", userId, bookId);
                jdbcTemplate.execute(sql2);
                System.out.println("You have saved book.");
            } else {
                System.out.println("There are no books with such id.");
            }
        }
    }

    public void deleteBook(int userId) throws SQLException {
        System.out.println("Enter id of a book that you want to delete or press '0' to exit:");
        int bookId = inputer.input();
        if (bookId != 0) {
            if (checkBook(bookId)) {
                String sql2 = String.format("delete from savedBooks where userId = %d and bookId = %d", userId, bookId);
                jdbcTemplate.execute(sql2);
                System.out.println("You have delete book.");
            } else {
                System.out.println("There are no books with such id.");
            }
        }
    }

    public void createNewAccount(String userName, String password) throws SQLException {
        //Here we add new user to database.
        String sql = "select name from users";
        List<String> names = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("name");
            }
        });

        if (names.contains(userName)) {
            System.out.println("User with such name already exists.");
        } else {
            String sql2 = String.format("insert into users(name, password) values ('%s', '%s')", userName, passwordEncoder.encode(password));
            jdbcTemplate.execute(sql2);
            System.out.println("New account was created");
        }
    }
}
