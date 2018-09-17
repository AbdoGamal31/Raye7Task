package com.raye7task;

import com.raye7task.utility.TimeUtilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeUnitTest {
    @Test
    public void timeFormatIsCorrect1() {
        String time = TimeUtilities.getFormattedDay("2018-09-13T21:25:30Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        assertEquals("21:25:30", time);
    }

    @Test
    public void timeFormatIsInCorrect() {
        String time = TimeUtilities.getFormattedDay("2018-09-13T19:56:52Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        assertEquals("19:56:50", time);
    }

    @Test
    public void timeFormatIsCorrect2() {
        String time = TimeUtilities.getFormattedDay("2018-09-13T19:26:00Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        assertEquals("19:26:00", time);
    }

    @Test
    public void timeFormatIsInCorrect2() {
        String time = TimeUtilities.getFormattedDay("2018-09-13T16:45:00Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        assertEquals("16:45:02", time);
    }
}
