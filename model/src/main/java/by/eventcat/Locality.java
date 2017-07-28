package by.eventcat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Model to store settlements that covers application
 */
@Entity
@Table(name = "our_locations")
public class Locality {

    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long locationId;

    @Column(name = "city_name")
    private String cityName;

    @ManyToMany(mappedBy = "localities")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public Locality() {
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        Locality locality = (Locality) o;
        return locationId == locality.locationId &&
                Objects.equals(cityName, locality.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, cityName);
    }

    @Override
    public String toString() {
        return "Locality{" +
                "locationId=" + locationId +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
