package by.eventcat;

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
}
