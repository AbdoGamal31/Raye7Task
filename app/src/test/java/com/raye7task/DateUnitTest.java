package com.raye7task;

import com.raye7task.utility.TimeUtilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateUnitTest {
    @Test
    public void dateFormatIsCorrect() {
        String date = TimeUtilities.getFormattedDay("2018-09-13T21:25:30Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.dateFormat);
        assertEquals("13/09/2018", date);
    }
    @Test
    public void dateFormatIsIncorrect() {
        String date = TimeUtilities.getFormattedDay("2018-09-13T19:56:52Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.dateFormat);
        assertEquals("2018-09-13", date);
    }
}
