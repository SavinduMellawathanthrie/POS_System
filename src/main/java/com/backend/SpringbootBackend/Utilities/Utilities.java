package com.backend.SpringbootBackend.Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class Utilities {

    public static final String[] categoryList = {
            "Men",
            "Women",
            "Kids",
            "Other"
    };

    public static String entityIDGenerator(Character Char) {
        String entityID;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + "MM" + "dd" + "hh" + "mm" + "ss" + "SSS");
        String Date = currentDateTime.format(formatter);
        entityID = Char.toString() + Date;
        return entityID;
    }

    public static String itemIDGenerator(Character Char, List<String> IDs) {
        String itemID = Char.toString()+"00"+(IDs.size()+1);
        try {

            int count = 1;
            List<Integer> list = new ArrayList<>();
            for (String s : IDs) {
                String id = s;
                id = id.substring(1);
                int Count = Integer.parseInt(id);
                list.add(Count);
            }
            list.sort(null);
            for (Integer integer : list) {
                if (integer + 1 != count) {
                    break;
                } else {
                    count++;
                }
            }
            itemID = Char + "00" +count;

        }
        catch (Exception e){

        }
        return itemID;
    }

    public static String entityUsernameGenerator(String email) {
        return email.substring(0, email.indexOf('@'));
    }

    // Intentionally left blank
}


