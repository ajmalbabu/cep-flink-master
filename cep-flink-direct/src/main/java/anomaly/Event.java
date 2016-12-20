package anomaly;

import java.time.Instant;

import static anomaly.Event.EventType.FLIGHT_EVENT;
import static anomaly.Event.EventType.MAINTENANCE_EVENT;


public interface Event {

    <T> T key();

    EventType getEventType();

    Instant createTime();

    enum EventType {

        FLIGHT_EVENT, MAINTENANCE_EVENT
    }

    default boolean isFlightEvent() {
        return getEventType() == FLIGHT_EVENT;
    }


    default boolean isMaintenanceEvent() {
        return getEventType() == MAINTENANCE_EVENT;
    }


}
