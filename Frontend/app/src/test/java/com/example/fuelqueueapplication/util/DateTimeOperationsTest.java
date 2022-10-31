package com.example.fuelqueueapplication.util;

import junit.framework.TestCase;

import org.junit.Test;
public class DateTimeOperationsTest extends TestCase {

    DateTimeOperations dateTimeOperations;

    @Test
    public void test_TimeToHoursAndMinutes(){
        dateTimeOperations = new DateTimeOperations();
        DateTimeModel dateTimeModel = dateTimeOperations.stringTimeToHoursAndMinutes("12 40");
        assertEquals("12", dateTimeModel.getHours());
    }

}