package cep.flight.anomaly.event;


import cep.lib.Event;

import java.io.Serializable;
import java.time.Instant;

public class FlightEvent implements Event, Serializable {
    public static final long serialVersionUID = 1L;

    public final static String FLIGHT_EVENT_KAFKA_TOPIC_OFFSET = "flightEventKafkaTopicOffset";

    private Instant createTime;
    private int flightNumber;
    private String originAirport;
    private String destinationAirport;

    public FlightEvent() {
        createTime = Instant.now();
    }

    public FlightEvent(int flightNumber, String originAirport, String destinationAirport) {
        this();
        this.flightNumber = flightNumber;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public FlightEvent(Instant createTime, int flightNumber, String originAirport, String destinationAirport) {
        this.createTime = createTime;
        this.flightNumber = flightNumber;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public FlightEvent copy() {

        return new FlightEvent(Instant.now(), this.flightNumber, this.originAirport, this.destinationAirport);
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightEvent)) return false;

        FlightEvent flightEvent = (FlightEvent) o;

        if (flightNumber != flightEvent.flightNumber) return false;
        if (originAirport != null ? !originAirport.equals(flightEvent.originAirport) : flightEvent.originAirport != null)
            return false;
        return destinationAirport != null ? destinationAirport.equals(flightEvent.destinationAirport) : flightEvent.destinationAirport == null;

    }

    @Override
    public int hashCode() {
        int result = flightNumber;
        result = 31 * result + (originAirport != null ? originAirport.hashCode() : 0);
        result = 31 * result + (destinationAirport != null ? destinationAirport.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\n\rFlightEvent{" +
                "createTime=" + createTime +
                ", flightNumber=" + flightNumber +
                ", originAirport='" + originAirport + '\'' +
                ", destinationAirport='" + destinationAirport + '\'' +
                '}';
    }

    @Override
    public Integer key() {
        return flightNumber;
    }

    @Override
    public EventType getEventType() {
        return EventType.FLIGHT_EVENT;
    }

    @Override
    public Instant createTime() {
        return createTime;
    }
}
