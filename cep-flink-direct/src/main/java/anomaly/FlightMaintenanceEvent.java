package anomaly;

import java.io.Serializable;
import java.time.Instant;

public class FlightMaintenanceEvent implements Event, Serializable {

    public static final long serialVersionUID = 1L;

    private Instant createTime;
    private int flightNumber;

    public FlightMaintenanceEvent() {
        createTime = Instant.now();
    }

    public FlightMaintenanceEvent(int flightNumber) {
        this();
        this.flightNumber = flightNumber;
    }

    public FlightMaintenanceEvent(Instant createTime, int flightNumber) {
        this.createTime = createTime;
        this.flightNumber = flightNumber;
    }

    public FlightMaintenanceEvent copy() {
        return new FlightMaintenanceEvent(Instant.now(), this.flightNumber);
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }


    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightMaintenanceEvent)) return false;

        FlightMaintenanceEvent that = (FlightMaintenanceEvent) o;

        return flightNumber == that.flightNumber;

    }

    @Override
    public int hashCode() {
        return flightNumber;
    }

    @Override
    public String toString() {
        return "\n\rFlightMaintenanceEvent{" +
                "createTime=" + createTime() +
                ", flightNumber=" + flightNumber +
                '}';
    }

    @Override
    public Integer key() {
        return flightNumber;
    }

    @Override
    public EventType getEventType() {
        return EventType.MAINTENANCE_EVENT;
    }

    @Override
    public Instant createTime() {
        return createTime;
    }


}
