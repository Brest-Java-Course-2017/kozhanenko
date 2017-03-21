package by.eventcat;

import java.util.Objects;

/**
 * Time Period stores data about event duration period
 */
public class TimePeriod {

    private int timePeriodId;
    private Event event;
    private long beginning;
    private long end;

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
