package com.library;

import java.util.List;

public class Printer {
    private final DBWorker worker = DBWorker.getInstance();
    private final Inputer inputer = Inputer.getInstance();
    private volatile static Printer printer;
    private boolean programIsOn = true;
    private int limit = 10;
    private int offset = 0;

    private Printer() {
    }

    public synchronized static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }

        return printer;
    }

    private void print(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books.");
            programIsOn = false;
        } else {
            books.forEach(this::printBook);

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
        }
    }

    public void showBooks() {
        limit = 10;
        offset = 0;
        programIsOn = true;

        while (programIsOn) {
            List<Book> books = worker.getBooks(limit, offset);
            print(books);
        }
    }

    public void showSavedBooks(int userId) {
        limit = 10;
        offset = 0;
        programIsOn = true;

        while (programIsOn) {
            List<Book> books = worker.getSavedBooks(limit, offset, userId);
            print(books);
        }
    }

    public void printBook(Book book) {
        System.out.printf("id - %d. %s, author - %s, year - %d\n",
                book.getId(), book.getName(), book.getAuthor(), book.getYear());
    }
}