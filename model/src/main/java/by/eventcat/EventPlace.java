package by.eventcat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * EventPlace model
 */
@Entity
@Table(name = "event_place")
public class EventPlace {

    @Id
    @Column(name = "event_place_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int eventPlaceId;

    @Column(name = "name")
    private String eventPlaceName;

    @Column(name = "description")
    private String description;

    @Column(name = "img_urls")
    private String imgUrls;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "latitude_coordinate")
    private float latitudeCoordinate;

    @Column(name = "longitude_coordinate")
    private float longitudeCoordinate;

    @ManyToMany(mappedBy = "placesAvailable")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

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

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public float getLatitudeCoordinate() {
        return latitudeCoordinate;
    }

    public void setLatitudeCoordinate(float latitudeCoordinate) {
        this.latitudeCoordinate = latitudeCoordinate;
    }

    public float getLongitudeCoordinate() {
        return longitudeCoordinate;
    }

    public void setLongitudeCoordinate(float longitudeCoordinate) {
        this.longitudeCoordinate = longitudeCoordinate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPlace that = (EventPlace) o;
        return eventPlaceId == that.eventPlaceId &&
                Float.compare(that.latitudeCoordinate, latitudeCoordinate) == 0 &&
                Float.compare(that.longitudeCoordinate, longitudeCoordinate) == 0 &&
                Objects.equals(eventPlaceName, that.eventPlaceName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imgUrls, that.imgUrls) &&
                Objects.equals(iconUrl, that.iconUrl) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventPlaceId, eventPlaceName, description, imgUrls, iconUrl,
                address, contacts, latitudeCoordinate, longitudeCoordinate);
    }

    @Override
    public String toString() {
        return "EventPlace{" +
                "eventPlaceId=" + eventPlaceId +
                ", eventPlaceName='" + eventPlaceName + '\'' +
                ", description='" + description + '\'' +
                ", imgUrls='" + imgUrls + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", address='" + address + '\'' +
                ", contacts='" + contacts + '\'' +
                ", latitudeCoordinate=" + latitudeCoordinate +
                ", longitudeCoordinate=" + longitudeCoordinate +
                '}';
    }
}
