package com.backend.SpringbootBackend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {

    public static String entityIDGenerator(Character Char) {
        String entityID;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + "MM" + "dd" + "hh" + "mm" + "ss" + "SSS");
        String Date = currentDateTime.format(formatter);
        entityID = Char.toString() + Date;
        return entityID;
    }

    public static String entityUsernameGenerator(String email) {
        return email.substring(0, email.indexOf('@'));
    }

}
