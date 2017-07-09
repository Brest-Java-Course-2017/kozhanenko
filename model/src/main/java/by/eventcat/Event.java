package by.eventcat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class Event
 */
@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int eventId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_place_name")
    private String eventPlace;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<TimePeriod> timePeriods = new HashSet<>();

    public Event(){}

    public Event(int eventId) {
        this.eventId = eventId;
    }

    public Event(Category category, String eventName, String eventPlace) {
        this.category = category;
        this.eventName = eventName;
        this.eventPlace = eventPlace;
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

    public Set<TimePeriod> getTimePeriods() {
        return timePeriods;
    }

    public void setTimePeriods(Set<TimePeriod> timePeriods) {
        this.timePeriods = timePeriods;
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
