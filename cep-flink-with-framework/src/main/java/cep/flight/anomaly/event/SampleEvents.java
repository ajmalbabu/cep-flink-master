package cep.flight.anomaly.event;

import cep.flight.anomaly.event.FlightEvent;
import cep.flight.anomaly.event.FlightMaintenanceEvent;
import cep.lib.Event;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SampleEvents implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<FlightEvent> sampleFlightEvents = Arrays.asList(new FlightEvent(100, "DFW", "HOU"),
            new FlightEvent(200, "HNL", "STL"), new FlightEvent(300, "SFO", "SAN"), new FlightEvent(400, "PHX", "FLL"),
            new FlightEvent(500, "DEN", "COS"), new FlightEvent(600, "NYC", "NJC"), new FlightEvent(700, "JFK", "MIA"),
            new FlightEvent(800, "ORL", "LAS"), new FlightEvent(900, "MDW", "ORD"), new FlightEvent(1000, "SJC", "POR")
    );

    private List<FlightMaintenanceEvent> sampleFlightMaintenanceEvents = Arrays.asList(new FlightMaintenanceEvent(100), new FlightMaintenanceEvent(200),
            new FlightMaintenanceEvent(300), new FlightMaintenanceEvent(400), new FlightMaintenanceEvent(500), new FlightMaintenanceEvent(600),
            new FlightMaintenanceEvent(700), new FlightMaintenanceEvent(800), new FlightMaintenanceEvent(900), new FlightMaintenanceEvent(1000)
    );

    public FlightEvent randomFlightEvent() {
        return sampleFlightEvents.get(new Random().nextInt(10)).copy();
    }

    public FlightMaintenanceEvent randomMaintenanceEvent() {
        return sampleFlightMaintenanceEvents.get(new Random().nextInt(10)).copy();
    }

    public List<Event> nextEvents(long offset) {

        // In reality the offset should be used to determine the next event. This example randomly generates
        // event so that there is a possibility to generate some anomaly because of the randomness. Since we
        // don't have a real event feed.
        if (new Random().nextBoolean()) {
            return Arrays.asList(randomFlightEvent());
        } else {
            return Arrays.asList(randomMaintenanceEvent());
        }
    }

}

