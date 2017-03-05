package by.eventcat;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for EventPlace model
 */
public class EventPlaceTest {
    @Test
    public void getEventPlaceId() throws Exception {
        EventPlace eventPlace = new EventPlace();
        eventPlace.setEventPlaceId(11);
        assertEquals(eventPlace.getEventPlaceId(), 11);
    }

    @Test
    public void getEventPlaceName() throws Exception {
        EventPlace eventPlace = new EventPlace();
        eventPlace.setEventPlaceName("КИНОТЕАТР БАЛАРУСЬ");
        assertEquals(eventPlace.getEventPlaceName(), "КИНОТЕАТР БАЛАРУСЬ");
    }

    @Test
    public void getEventPlaceAddress() throws Exception {
        EventPlace eventPlace = new EventPlace();
        eventPlace.setEventPlaceAddress("Брест, Советская, 25");
        assertEquals(eventPlace.getEventPlaceAddress(), "Брест, Советская, 25");
    }

    @Test
    public void getCategoryId() throws Exception {
        EventPlace eventPlace = new EventPlace();
        eventPlace.setCategoryId(12);
        assertEquals(eventPlace.getCategoryId(), 12);
    }

}