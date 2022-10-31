package com.example.fuelqueueapplication.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Use the Test class to test the logics. TEST_CLASS: DateTimeOperationTest.java
 **/

public class DateTimeOperations {

    // Globals
    final static String dateFormat = "dd-MM-yyyy HH:mm:ss";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    Date date = new Date();

    // Get the Date in "06-11-2017 12:26:18" format.
    public String getDate() {
        return formatter.format(date);
    }

    //TODO: When String time given output separate hours and minutes
    public DateTimeModel stringTimeToHoursAndMinutes(String time) {
        String dateArray[] = time.split(" ");
        DateTimeModel dateTimeModel = new DateTimeModel();
        dateTimeModel.setHours(dateArray[0]);
        dateTimeModel.setMinutes(dateArray[1]);
        return dateTimeModel;
    }

    //When String Datetime given output Date
    public DateTimeModel stringDateTimeToDateOnly() {
        DateTimeModel dateTimeModel = new DateTimeModel();
        // Implement the logic
        return dateTimeModel;
    }

    //When String Datetime given output Time
    public DateTimeModel stringDateTimeToTime() {
        DateTimeModel dateTimeModel = new DateTimeModel();
        // Implement the logic
        return dateTimeModel;
    }

    //get the time differance
    public String getDateDifferance(String start_date, String end_date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Date d1 = sdf.parse(start_date);
        Date d2 = sdf.parse(end_date);

        long difference_In_Time = d2.getTime() - d1.getTime();

        long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;

        long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;

        long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

        long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;

        if (difference_In_Minutes == 0) {
            return difference_In_Seconds + "S";
        } else if (difference_In_Hours == 0) {
            return difference_In_Minutes + "M " + difference_In_Seconds + "S";
        } else if (difference_In_Days == 0) {
            return difference_In_Hours + "H " + difference_In_Minutes + "M "+difference_In_Seconds + "S";
        } else {
            return difference_In_Days + "D " + difference_In_Hours + "H " + difference_In_Minutes + "M "
                    + difference_In_Seconds + "S";
        }
    }

}
