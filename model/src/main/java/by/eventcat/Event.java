package by.eventcat;

import java.util.Objects;

/**
 *
 */
public class Event {
    private int eventId;
    private String eventName;
    private Place place;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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
                Objects.equals(place, event.place) &&
                Objects.equals(category, event.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, place, category);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", place=" + place +
                ", category=" + category +
                '}';
    }
}
