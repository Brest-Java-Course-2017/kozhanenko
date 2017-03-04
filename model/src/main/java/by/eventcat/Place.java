package by.eventcat;

import java.util.Objects;

/**
 * Created by andrei on 4.3.17.
 */
public class Place {
    private int placeId;
    private String name;
    private String address;

    public Place(){}

    public int getEventPlaceId() {
        return placeId;
    }

    public void setEventPlaceId(int eventPlaceId) {
        this.placeId= eventPlaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return placeId == place.placeId &&
                Objects.equals(name, place.name) &&
                Objects.equals(address, place.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, name, address);
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
