package com.library;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    //In main file we chose actions and give commands to DBWorker, which can communicate with database.
    private static final Inputer inputer = Inputer.getInstance();
    private static final Scanner scan = new Scanner(System.in);
    private static final Printer printer = Printer.getInstance();

    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final BookRepository bookRepository = BookRepository.getInstance();

    private static String name;
    private static String password;
    private static int id = -1;
    private static boolean isAuth = false;
    private static boolean isProgramOn = true;


    public static void main(String[] args) {
        try {
            System.out.println("___Welcome to database-oriented library___");

            while (isProgramOn) {
                while (!isAuth) {
                    printer.authChoseBar();
                    authCaseManager();
                }
                while (isAuth && id > 0) {
                    printer.mainChoseBar();
                    mainCaseManager();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in connection to data base.");
        }
    }

    private static void authCaseManager() throws SQLException {
        switch (inputer.input()) {
            case 1 -> {
                System.out.println("Please enter your username:");
                name = scan.next();
                System.out.println("Please enter your password:");
                password = scan.next();

                if ((id = userRepository.auth(name, password)) > 0) {
                    System.out.println("You have entered account successfully.");
                    isAuth = true;
                } else {
                    System.out.println("Name or password are wrong. Please try once again.");
                }
            }
            case 2 -> {
                System.out.println("See you again!");
                isAuth = true;
                isProgramOn = false;
            }
            case 3 -> {
                System.out.println("Enter your name:");
                name = scan.next();
                System.out.println("Enter your password:");
                password = scan.next();
                System.out.println("Please repeat your password:");
                String password2 = scan.next();

                if (password.equals(password2)) {
                    if (userRepository.createNewAccount(name, password)) {
                        System.out.println("New account was added");
                    } else {
                        System.out.println("User with such name already exists or something went wrong");
                    }
                } else {
                    System.out.println("Your passwords has not matched, please try again.");
                }
            }
            default -> System.out.println("Bad input, please try again.");
        }
    }
    private static void mainCaseManager() throws SQLException {
        switch (inputer.input()) {
            case 0 -> {
                System.out.println("Enter page number: ");
                printer.showSavedBooks(inputer.input(), id);
            }
            case 1 -> {
                System.out.println("Enter page number: ");
                printer.showBooks(inputer.input());
            }
            case 2 -> {
                printer.findBookChooseBar();
                bookRepository.findBook();
            }
            case 3 -> {
                System.out.println("Enter id of a book that you want to add:\n" +
                        "(if you dont know the id you can type '0' to close this operation and check id in the 'Find book'.)");
                printer.printBookSaved(bookRepository.saveBook(id));
            }
            case 4 -> {
                System.out.println("Enter id of a book that you want to delete or press '0' to exit:");
                printer.printBookDeleted(bookRepository.deleteBook(id));
            }
            case 5 -> {
                System.out.println("Disconnect from account ");
                id = -1;
                isAuth = false;
            }
            case 6 -> {
                System.out.println("Enter your old password: ");
                if (scan.next().equals(password)) {
                    System.out.println("Enter your new password: ");
                    String newPassword = scan.next();
                    System.out.println("Repeat your new password");
                    String newPassword2 = scan.next();

                    if (newPassword.equals(newPassword2)) {
                        userRepository.setNewPassword(id, newPassword);
                        System.out.println("Password was changed.");
                    }
                }
            }
            case 7 -> {
                System.out.println("See you again!");
                isAuth = false;
                isProgramOn = false;
            }
            default -> System.out.println("Bad input, please try again.");
        }
    }
}