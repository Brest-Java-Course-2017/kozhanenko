package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static by.eventcat.TimeConverter.*;

/**
 * Tests for Time Converter class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class TimeConverterTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TIME_IN_STRING = "2017-03-13 22:49:49";
    private static final long TIME_IN_SECONDS = 1489434589;
    private static final String TIME_IN_STRING1 = "2016-04-12 21:00:00";
    private static final long TIME_IN_SECONDS1 = 1460484000;
    private static final String WRONG_DATE_FORMAT = "2016.04.12 21:00:00";
    private static final String WRONG_DATE_FORMAT1 = "2016-04-12 21:00";
    private static final String WRONG_DATE_FORMAT2 = "2016-04-12 21.00:00";
    private static final String WRONG_DATE_FORMAT3 = "05-03-2012 21:00:00";
    private static final String WRONG_DATE_FORMAT4 = "2016-04-12 1:00:00";
    private static final String WRONG_DATE = "2017-13-13 22:49:49";
    private static final String WRONG_DATE1 = "2017-03-44 22:49:49";
    private static final String WRONG_DATE2 = "2017-03-31 25:49:49";
    private static final String WRONG_DATE3 = "2017-03-13 22:66:49";
    private static final String WRONG_DATE4 = "2017-03-13 22:49:77";
    private static final String WRONG_DATE5_FEB = "2017-02-30 22:49:49";


    @Test
    public void convertTimeFromStringToSecondsTest() throws Exception {
        LOGGER.debug("convertTimeFromStringToSecondsTest()");

        long timeInSeconds = convertTimeFromStringToSeconds(TIME_IN_STRING);
        assertEquals(TIME_IN_SECONDS, timeInSeconds);
        long timeInSeconds1 = convertTimeFromStringToSeconds(TIME_IN_STRING1);
        assertEquals(TIME_IN_SECONDS1, timeInSeconds1);
    }

    @Test
    public void convertTimeFromSecondsToStringTest() throws Exception {
        LOGGER.debug("convertTimeFromSecondsToStringTest()");

        String timeInString = convertTimeFromSecondsToString(TIME_IN_SECONDS);
        assertEquals(TIME_IN_STRING, timeInString);
        String timeInString1 = convertTimeFromSecondsToString(TIME_IN_SECONDS1);
        assertEquals(TIME_IN_STRING1, timeInString1);
    }

    @Test
    public void isValidDateInStringFormatTest() throws Exception {
        LOGGER.debug("isValidDateInStringFormatTest()");

        assertTrue(isValidDateInStringFormat(TIME_IN_STRING));
        assertTrue(isValidDateInStringFormat(TIME_IN_STRING1));
        assertFalse(isValidDateInStringFormat(WRONG_DATE_FORMAT));
        assertFalse(isValidDateInStringFormat(WRONG_DATE_FORMAT1));
        assertFalse(isValidDateInStringFormat(WRONG_DATE_FORMAT2));
        assertFalse(isValidDateInStringFormat(WRONG_DATE_FORMAT3));
        assertFalse(isValidDateInStringFormat(WRONG_DATE_FORMAT4));
    }

    @Test
    public void isValidDateInStringTest() throws Exception {
        LOGGER.debug("isValidDateInStringTest()");

        assertTrue(isValidDateInString(TIME_IN_STRING));
        assertTrue(isValidDateInString(TIME_IN_STRING1));
        assertFalse(isValidDateInString(WRONG_DATE_FORMAT));
        assertFalse(isValidDateInString(WRONG_DATE_FORMAT1));
        assertFalse(isValidDateInString(WRONG_DATE_FORMAT2));
        assertFalse(isValidDateInString(WRONG_DATE_FORMAT3));
        assertFalse(isValidDateInString(WRONG_DATE_FORMAT4));
        assertFalse(isValidDateInString(WRONG_DATE));
        assertFalse(isValidDateInString(WRONG_DATE1));
        assertFalse(isValidDateInString(WRONG_DATE2));
        assertFalse(isValidDateInString(WRONG_DATE3));
        assertFalse(isValidDateInString(WRONG_DATE4));
        assertFalse(isValidDateInString(WRONG_DATE5_FEB));



    }

}