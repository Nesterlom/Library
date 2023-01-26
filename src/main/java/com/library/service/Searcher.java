package com.library.service;

import com.library.BookRepository;
import com.library.entity.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Searcher {
    private final BookRepository bookRepository = BookRepository.getInstance();
    private final static Inputer inputer = Inputer.getInstance();
    private final static Scanner scan = new Scanner(System.in);
    private final Printer printer = Printer.getInstance();
    private volatile static Searcher searcher;

    public synchronized static Searcher getInstance() {
        if (searcher == null) {
            searcher = new Searcher();
        }

        return searcher;
    }

    private Searcher() {
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
        List<Book> books = bookRepository.getBooksByName(bookName);

        if(books.isEmpty()){
            System.out.println("No results.");
        }else{
            books.forEach(printer::printBook);
        }
    }

    public void findByAuthor() {
        System.out.println("Enter author of book: ");
        String author = scan.next();
        List<Book> books = bookRepository.getBooksByAuthor(author);

        if(books.isEmpty()){
            System.out.println("No results.");
        }else{
            books.forEach(printer::printBook);
        }
    }

    public void findByYear() {
        System.out.println("Enter year of publishing: ");
        int year = scan.nextInt();
        try{
        List<Book> books = bookRepository.getBooksByYear(year);

        if(books.isEmpty()){
            System.out.println("No results.");
        }else{
            books.forEach(printer::printBook);
        }}
        catch (InputMismatchException e){
            System.out.println("Wrong input");
        }
    }

}