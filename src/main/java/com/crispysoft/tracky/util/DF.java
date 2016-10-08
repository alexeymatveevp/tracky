package com.crispysoft.tracky.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: david
 * Date: 08.10.2016
 * Time: 15:01
 */
public class DF {

    public static final SimpleDateFormat df = new SimpleDateFormat("MM.dd.yyyy");

    public static Date parseDate(String date) {
        try {
            return df.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
