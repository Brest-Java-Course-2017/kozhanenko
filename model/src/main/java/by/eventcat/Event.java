package by.eventcat;

import java.util.Objects;

/**
 *
 */
public class Event {
    private int eventId;
    private String eventName;
    private EventPlace eventPlace;
    private Category category;

    public Event(){}

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventPlace getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(EventPlace eventPlace) {
        this.eventPlace = eventPlace;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                Objects.equals(eventName, event.eventName) &&
                Objects.equals(eventPlace, event.eventPlace) &&
                Objects.equals(category, event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, eventPlace, category);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventPlace=" + eventPlace +
                ", category=" + category +
                '}';
    }
}
