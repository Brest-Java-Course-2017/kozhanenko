package by.eventcat;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * EventTest
 */
public class EventTest {
    @Test
    public void getEventId() throws Exception {
        Event event = new Event();
        event.setEventId(1);
        assertEquals(event.getEventId(), 1);
    }

    @Test
    public void getEventName() throws Exception {
        Event event = new Event();
        event.setEventName("Выставка камней");
        assertEquals(event.getEventName(), "Выставка камней");
    }

    @Test
    public void getEventPlace() throws Exception {
        Event event = new Event();
        event.setEventPlace("Cinema");
        assertEquals(event.getEventPlace(), "Cinema");
    }

    @Test
    public void getCategory() throws Exception {
        Event event = new Event();
        Category category = new Category();
        event.setCategory(category);
        assertEquals(event.getCategory(), category);

    }

}