package by.eventcat;

import javax.persistence.*;
import java.util.Objects;

/**
 * Time Period stores data about event duration period
 */
@Entity
@Table(name = "time_period")
public class TimePeriod {

    @Id
    @Column(name = "time_period_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int timePeriodId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "beginning")
    private long beginning;

    @Column(name = "end")
    private long end;

    public TimePeriod(){}

    public TimePeriod(Event event, long beginning, long end) {
        this.event = event;
        this.beginning = beginning;
        this.end = end;
    }

    public TimePeriod(int timePeriodId, Event event, long beginning, long end) {
        this.timePeriodId = timePeriodId;
        this.event = event;
        this.beginning = beginning;
        this.end = end;
    }

    public int getTimePeriodId() {
        return timePeriodId;
    }

    public void setTimePeriodId(int timePeriodId) {
        this.timePeriodId = timePeriodId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getBeginning() {
        return beginning;
    }

    public void setBeginning(long beginning) {
        this.beginning = beginning;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod that = (TimePeriod) o;
        return timePeriodId == that.timePeriodId &&
                beginning == that.beginning &&
                end == that.end &&
                Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timePeriodId, event, beginning, end);
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "timePeriodId=" + timePeriodId +
                ", event=" + event +
                ", beginning=" + beginning +
                ", end=" + end +
                '}';
    }
}
