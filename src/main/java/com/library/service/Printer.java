package com.library.service;

import com.library.BookRepository;
import com.library.entity.Book;

import java.util.List;

public class Printer {
    private final BookRepository bookRepository = BookRepository.getInstance();
    private final Inputer inputer = Inputer.getInstance();
    private volatile static Printer printer;

    private int pageNumber;

    private Printer() {
    }

    public synchronized static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }

        return printer;
    }

    public void findBookChooseBar() {
        System.out.print("""
                Chose method of finding a book:
                1.By book name.
                2.By author.
                3.By year.
                """);
    }

    public void authChoseBar() {
        System.out.print("""
                Choose action:
                1.Enter account.
                2.End program.
                3.Create new account.
                """);
    }

    public void mainChoseBar() {
        System.out.print("""
                Choose action:
                0.Show saved books.
                1.Show books from database.
                2.Find book.
                3.Save book.
                4.Delete book.
                5.Exit from account.
                6.Change password.
                7.End program.
                """);
    }

    private void print(List<Book> books, int amountOfPages) {
        if (books.isEmpty()) {
            System.out.println("No books.");
        } else {
            books.forEach(this::printBook);
            System.out.println(String.format("You are on %d page. General amount of pages is %d", pageNumber, amountOfPages));

            System.out.print("""
                    1.Next page.
                    2.Previous page.
                    Print anything to exit.
                    """);

            int action = inputer.input();
            switch (action) {
                case 1 -> {
                    pageNumber++;
                    showBooks(pageNumber);
                }
                case 2 -> {
                    pageNumber--;
                    showBooks(pageNumber);
                }
            }
        }
    }

    public void showBooks(int pageNumber) {
        if (pageNumber >= 1) {
            this.pageNumber = pageNumber;

            BooksContainer booksContainer = bookRepository.getBooks(pageNumber);
            print(booksContainer.getBooks(), booksContainer.getAmountOfPages());
        }
    }

    public void printBookSaved(boolean wasBookSaved){
        if(wasBookSaved){
            System.out.println("You have saved book.");
        } else{
            System.out.println("Something went wrong or you exited.");
        }
    }

    public void printBookDeleted(boolean wasBookDeleted){
        if(wasBookDeleted){
            System.out.println("You have delete book.");
        } else{
            System.out.println("Something went wrong or you exited.");
        }
    }

    public void showSavedBooks(int pageNumber, int userId) {
        if (pageNumber >= 1) {
            this.pageNumber = pageNumber;

            BooksContainer booksContainer = bookRepository.getSavedBooks(pageNumber, userId);
            print(booksContainer.getBooks(), booksContainer.getAmountOfPages());
        }
    }

    public void printBook(Book book) {
        System.out.printf("id - %d. %s, author - %s, year - %d\n",
                book.getId(), book.getName(), book.getAuthor(), book.getYear());
    }

}