package com.library;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    //In main file we chose actions and give commands to DBWorker, which can communicate with database.
    private static final Inputer inputer = Inputer.getInstance();

    public static void main(String[] args) {
        try {
            boolean isProgramOn = true;
            boolean isEntered = false;
            boolean isAuth = false;
            String name = ""; // name of user
            String password = "";// users password
            int id = -1;
            Scanner scan = new Scanner(System.in);
            DBWorker worker = DBWorker.getInstance();
            Printer printer = Printer.getInstance();

            System.out.println("___Welcome to database-oriented library___");

            while (isProgramOn) {
                while (!isAuth) {
                    System.out.print("""
                            Choose action:
                            1.Enter account.
                            2.End program.
                            3.Create new account.
                            """);
                    int action = inputer.input();
                    switch (action) {
                        case 1 -> {
                            System.out.println("Please enter your username:");
                            name = scan.next();
                            System.out.println("Please enter your password:");
                            password = scan.next();

                            if ((id = worker.auth(name, password)) > 0) {
                                System.out.println("You have entered account successfully.");
                                isAuth = true;
                                isEntered = true;
                            } else {
                                System.out.println("Name or password are wrong. Please try once again.");
                                name = "";
                                password = "";
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
                                if (worker.createNewAccount(name, password)) {
                                    System.out.println("New account was added");
                                } else {
                                    System.out.println("User with such name already exists or something went wrong");
                                }
                            } else {
                                System.out.println("Your passwords has not matched, please try again.");
                            }
                        }
                        default -> {
                            System.out.println("Bad input, please try again.");
                        }
                    }
                }

                while (isEntered) {
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
                    int action = inputer.input();

                    switch (action) {
                        case 0 -> {
                            printer.showSavedBooks(id);
                        }
                        case 1 -> {
                            printer.showBooks();
                        }
                        case 2 -> {
                            System.out.print("""
                                    Chose method of finding a book:
                                    1.By book name.
                                    2.By author.
                                    3.By year.
                                    """);
                            worker.findBook();
                        }
                        case 3 -> {
                            System.out.println("Enter id of a book that you want to add:\n" +
                                    "(if you dont know the id you can type '0' to close this operation and check id in the 'Find book'.)");
                            if (worker.saveBook(id)) {
                                System.out.println("You have saved book.");
                            } else {
                                System.out.println("Something went wrong or you exited.");
                            }
                        }
                        case 4 -> {
                            System.out.println("Enter id of a book that you want to delete or press '0' to exit:");
                            if (worker.deleteBook(id)) {
                                System.out.println("You have delete book.");
                            } else {
                                System.out.println("Something went wrong or you exited.");
                            }
                        }
                        case 5 -> {
                            System.out.println("Disconnect from account ");
                            isEntered = false;
                            isAuth = false;
                            name = "";
                            password = "";
                        }
                        case 6 -> {
                            System.out.println("Enter your old password: ");
                            String oldPassword = scan.next();
                            if (oldPassword.equals(password)) {
                                System.out.println("Enter your new password: ");
                                String newPassword = scan.next();
                                System.out.println("Repeat your new password");
                                String newPassword2 = scan.next();

                                if (newPassword.equals(newPassword2)) {
                                    worker.setNewPassword(name, newPassword);
                                    System.out.println("Password was changed.");
                                }
                            }
                        }
                        case 7 -> {
                            System.out.println("See you again!");
                            isEntered = false;
                            isProgramOn = false;
                        }
                        default -> {
                            System.out.println("Bad input, please try again.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in connection to data base.");
        }
    }
}
