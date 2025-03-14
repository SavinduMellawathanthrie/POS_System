package com.backend.SpringbootBackend.Utilities;

import com.backend.SpringbootBackend.Configuration.AuthController;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilities {

    private static final Logger LOGGER = Logger.getLogger(Utilities.class.getName());
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

    public static String keyGenerator(){
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        LOGGER.log(Level.WARNING, "Secret Key."+base64Key);
        return base64Key;
    }
    // Intentionally left blank
}


