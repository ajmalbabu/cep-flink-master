package anomaly;

import java.io.Serializable;

public class FlightAnomalyDetector implements AnomalyDetector, Serializable {

    public static final long serialVersionUID = 1L;


    @Override
    public boolean detectAnomaly(Events events) {

        // System.out.println("Checking for anomaly at: " + Instant.now() + " for " + events);

        boolean anomalyFound = false;

        if (events.isAnomalyDetected()) {
            // System.out.println("********  Skip check for these flights, Anomaly ALREADY detected for events with flight number " + events.eventKey());
        } else {

            int flightCount = 0;
            int maintenanceCount = 0;

            for (Event event : events.getElements()) {
                if (event.isFlightEvent()) {
                    flightCount++;
                } else if (event.isMaintenanceEvent()) {
                    maintenanceCount++;
                }
            }

            if (flightCount >= 2 && maintenanceCount >= 1) {
                anomalyFound = true;
            }
        }

        return anomalyFound;


    }

}
