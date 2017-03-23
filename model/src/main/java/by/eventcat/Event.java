package by.eventcat;

import java.util.Objects;

/**
 * Class Event
 */
public class Event {
    private int eventId;
    private Category category;
    private String eventName;
    private String eventPlace;

    public Event(){}

    public Event(int eventId) {
        this.eventId = eventId;
    }

    public Event(int eventId, Category category, String eventName, String eventPlace) {
        this.eventId = eventId;
        this.category = category;
        this.eventName = eventName;
        this.eventPlace = eventPlace;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId &&
                Objects.equals(category, event.category) &&
                Objects.equals(eventName, event.eventName) &&
                Objects.equals(eventPlace, event.eventPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, category, eventName, eventPlace);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", category=" + category +
                ", eventName='" + eventName + '\'' +
                ", eventPlace='" + eventPlace + '\'' +
                '}';
    }
}
