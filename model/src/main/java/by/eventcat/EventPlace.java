package by.eventcat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * EventPlace model
 */
@Entity
@Table(name = "event_place")
public class EventPlace {

    private int eventPlaceId;

    private String eventPlaceName;

    private String description;

    private String img_urls;

    private String address;

    private String contacts;

    private String coordinates;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(String img_urls) {
        this.img_urls = img_urls;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPlace that = (EventPlace) o;
        return eventPlaceId == that.eventPlaceId &&
                Objects.equals(eventPlaceName, that.eventPlaceName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(img_urls, that.img_urls) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contacts, that.contacts) &&
                Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventPlaceId, eventPlaceName, description, img_urls, address, contacts, coordinates);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
                "eventPlaceId=" + eventPlaceId +
                ", eventPlaceName='" + eventPlaceName + '\'' +
                ", description='" + description + '\'' +
                ", img_urls='" + img_urls + '\'' +
                ", address='" + address + '\'' +
                ", contacts='" + contacts + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}
