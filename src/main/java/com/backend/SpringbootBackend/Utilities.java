package com.backend.SpringbootBackend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utilities {

    public static int integerInput(String message) {
        Scanner ConsoleReader = new Scanner(System.in);
        int input = 0;
        while (true) {
            try {
                System.out.print(message + " : ");
                input = ConsoleReader.nextInt();
            } catch (Exception e) {
                System.out.println("Enter a valid input! Only integers are allowed here.");
                ConsoleReader.nextLine();
                continue;
            }
            break;
        }
        return input;
    }

    public static String stringInput(String message) {
        Scanner ConsoleReader = new Scanner(System.in);
        System.out.print(message + " : ");
        return ConsoleReader.nextLine();
    }

    public static String entityIDGenerator(Character Char) {
        String entityID;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + "MM" + "dd" + "hh" + "mm" + "ss" + "SSS");
        String Date = currentDateTime.format(formatter);
        entityID = Char.toString() + Date;
        return entityID;
    }


}
