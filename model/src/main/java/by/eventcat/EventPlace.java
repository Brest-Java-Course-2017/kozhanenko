package by.eventcat;

import java.util.Objects;

/**
 * EventPlace class
 */
public class EventPlace {
    private int eventPlaceId;
    private String eventPlaceName;
    private String eventPlaceAddress;

    public EventPlace(){}

    public int getEventPlaceId() {
        return eventPlaceId;
    }

    public void setEventPlaceId(int eventPlaceId) {
        this.eventPlaceId = eventPlaceId;
    }

    public String getEventPlaceName() {
        return eventPlaceName;
    }

    public void setEventPlaceName(String eventPlaceName) {
        this.eventPlaceName = eventPlaceName;
    }

    public String getEventPlaceAddress() {
        return eventPlaceAddress;
    }

    public void setEventPlaceAddress(String eventPlaceAddress) {
        this.eventPlaceAddress = eventPlaceAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPlace eventPlace = (EventPlace) o;
        return eventPlaceId == eventPlace.eventPlaceId &&
                Objects.equals(eventPlaceName, eventPlace.eventPlaceName) &&
                Objects.equals(eventPlaceAddress, eventPlace.eventPlaceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventPlaceId, eventPlaceName, eventPlaceAddress);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
                "eventPlaceId=" + eventPlaceId +
                ", eventPlaceName='" + eventPlaceName + '\'' +
                ", eventPlaceAddress='" + eventPlaceAddress + '\'' +
                '}';
    }
}
