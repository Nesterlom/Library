package com.library;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {

    private final static Inputer inputer = Inputer.getInstance();
    private final static Scanner scan = new Scanner(System.in);
    private final List<Book> books;
    private int count = 1;

    public Searcher(List<Book> books) {
        this.books = books;
    }

    private void search(Matcher matcher, Book book) {
        if (matcher.find()) {
            if (count == 1) {
                System.out.println("I think you want to find this books:");
            }

            String str = String.format("%d. %s, author - %s. Id - %d", count, book.getName(), book.getAuthor(), book.getId());
            System.out.println(str);
            count++;
        }
    }

    public void chooseStrategyAndFindBook() {
        int method = inputer.input();

        switch (method) {
            case 1 -> {
                findByName();
            }
            case 2 -> {
                findByAuthor();
            }
            case 3 -> {
                findByYear();
            }
            default -> {
                System.out.println("Bad input, please try again.");
            }
        }
    }

    public void findByName() {
        System.out.println("Enter name of book: ");
        String bookName = scan.next();
        Pattern pattern = Pattern.compile(bookName);
        count = 1;

        for (Book book : books) {
            Matcher matcher = pattern.matcher(book.getName());
            search(matcher, book);
        }
        if (count == 1) {
            System.out.println("No results.");
        }
    }

    public void findByAuthor() {
        System.out.println("Enter author: ");
        String author = scan.next();
        count = 1;

        Pattern pattern = Pattern.compile(author);

        for (Book book : books) {
            Matcher matcher = pattern.matcher(book.getAuthor());
            search(matcher, book);
        }
        if (count == 1) {
            System.out.println("No results.");
        }
    }

    public void findByYear() {
        System.out.println("Enter year of publishing: ");
        String year = scan.next();
        Pattern pattern = Pattern.compile(year);
        count = 1;

        for (Book book : books) {
            Matcher matcher = pattern.matcher(String.valueOf(book.getYear()));
            search(matcher, book);
        }
        if (count == 1) {
            System.out.println("No results.");
        }
    }

}
