package ua.taxi.base.utils;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by andrii on 08.06.16.
 */
public class DateUtils {

    public static String HHmm(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("HH.mm.ss a"));
    }

}
