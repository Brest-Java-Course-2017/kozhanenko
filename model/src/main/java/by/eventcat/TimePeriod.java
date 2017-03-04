package by.eventcat;

import java.util.Objects;

/**
 * Created by andrei on 4.3.17.
 */
public class TimePeriod {

    private int timePeriodId;
    private int eventId;
    private long beginning;
    private long end;

    public TimePeriod() {
    }

    public int getTimePeriodId() {
        return timePeriodId;
    }

    public void setTimePeriodId(int timePeriodId) {
        this.timePeriodId = timePeriodId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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
                eventId == that.eventId &&
                beginning == that.beginning &&
                end == that.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timePeriodId, eventId, beginning, end);
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "timePeriodId=" + timePeriodId +
                ", eventId=" + eventId +
                ", beginning=" + beginning +
                ", end=" + end +
                '}';
    }
}
