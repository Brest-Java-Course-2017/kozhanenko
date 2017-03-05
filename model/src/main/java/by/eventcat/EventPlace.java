package by.eventcat;

import java.util.Objects;

/**
 * EventPlace class
 */
public class EventPlace {
    private int eventPlaceId;
    private String eventPlaceName;
    private String eventPlaceAddress;
    private int categoryId;

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPlace that = (EventPlace) o;
        return eventPlaceId == that.eventPlaceId &&
                categoryId == that.categoryId &&
                Objects.equals(eventPlaceName, that.eventPlaceName) &&
                Objects.equals(eventPlaceAddress, that.eventPlaceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventPlaceId, eventPlaceName, eventPlaceAddress, categoryId);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
                "eventPlaceId=" + eventPlaceId +
                ", eventPlaceName='" + eventPlaceName + '\'' +
                ", eventPlaceAddress='" + eventPlaceAddress + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
