package com.library;

import com.library.entity.Book;
import com.library.service.Inputer;
import com.library.service.Searcher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;
import java.util.List;

public class BookRepository {
    private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    private final Inputer inputer = Inputer.getInstance();

    private volatile static BookRepository bookRepository;

    {
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUsername(System.getenv("my_sql_username"));
        dataSource.setUrl(System.getenv("my_sql_url"));
        dataSource.setPassword(System.getenv("my_sql_pass"));
    }

    public synchronized static BookRepository getInstance() {
        if (bookRepository == null) {
            bookRepository = new BookRepository();
        }

        return bookRepository;
    }



    private boolean isBookInSavedBooks(int bookId, int userId) {
        String sql = String.format("select bookId from savedBooks where bookId = %d and userId = %d", bookId, userId);

        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }

    public BooksContainer getSavedBooks(int pageNumber, int userId) {
        String sql = String.format("select * from books join savedBooks on savedBooks.bookId = books.id where userId = %d limit %d offset %d;", userId, pageNumber * 10, pageNumber * 10 - 10);
        BooksContainer booksContainer = new BooksContainer();
        List<Book> books = jdbcTemplate.query(sql, new BookMapper());

        booksContainer.setBooks(books);
        booksContainer.setPageNumber(pageNumber);
        booksContainer.setAmountOfPages( (int) Math.ceil(getAmountOfSavedBooks(userId) / 10.0));

        return booksContainer;
    }

    public int getAmountOfSavedBooks(int userId){
        String sql = String.format("select count(books.id) from books join savedbooks on books.id = savedBooks.bookId join users on users.id = savedbooks.userId where users.id = %d", userId);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getAmountOfBooks(){
        return jdbcTemplate.queryForObject("select count(id) from books", Integer.class);
    }

    public List<Book> getBooks() {
        String query = "select * from books";

        return jdbcTemplate.query(query, new BookMapper());
    }

    public List<Book> getBooksByName(String name) {
        String query = "select * from books where name like '%" + name + "%'";

        return jdbcTemplate.query(query, new BookMapper());
    }

    public List<Book> getBooksByAuthor(String author) {
        String query = "select * from books where author like '%" + author + "%'";

        return jdbcTemplate.query(query, new BookMapper());
    }

    public List<Book> getBooksByYear(int year) {
        String query = String.format("select * from books where year = %d", year);

        return jdbcTemplate.query(query, new BookMapper());
    }

    public BooksContainer getBooks(int pageNumber) {
        String sql = String.format("select * from books limit %s offset %d", pageNumber * 10, pageNumber * 10 - 10);
        BooksContainer booksContainer = new BooksContainer();
        List<Book> books = jdbcTemplate.query(sql, new BookMapper());

        booksContainer.setBooks(books);
        booksContainer.setPageNumber(pageNumber);
        booksContainer.setAmountOfPages( (int) Math.ceil(getAmountOfBooks() / 10.0));

        return booksContainer;
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

    public void saveBook(int userId, int bookId) {
        if (!isBookInSavedBooks(bookId, userId)) {
            try {
                String sql = String.format("insert into savedBooks(userId, bookId) values (%d, %d)", userId, bookId);
                jdbcTemplate.execute(sql);
            } catch (DataIntegrityViolationException e) {
            }
        }

    }

    public void deleteBook(int userId, int bookId){
        if (isBookInSavedBooks(bookId, userId)) {
            String sql = String.format("delete from savedBooks where userId = %d and bookId = %d", userId, bookId);
            jdbcTemplate.execute(sql);
        }
    }
}
