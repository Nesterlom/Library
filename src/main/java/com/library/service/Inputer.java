package com.library.service;

import java.util.Scanner;

public class Inputer {
    private static Inputer inputer;

    private Inputer() {
    }

    public int input() {
        //There are some places in Main and DBWorker files, where we need to get some info from keyboard,
        //so this actions here.
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ignored) {
        }
        return -1;
    }

    public static Inputer getInstance() {
        if (inputer == null) {
            inputer = new Inputer();
        }

        return inputer;
    }
}
