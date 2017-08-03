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

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private Set<UserTotals> usersTotalData = new HashSet<>();

    public Locality() {
    }

    public Locality(long locationId) {
        this.locationId = locationId;
    }

    public Locality(long locationId, String cityName) {
        this.locationId = locationId;
        this.cityName = cityName;
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

    public Set<UserTotals> getUsersTotalData() {
        return usersTotalData;
    }

    public void setUsersTotalData(Set<UserTotals> usersTotalData) {
        this.usersTotalData = usersTotalData;
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
