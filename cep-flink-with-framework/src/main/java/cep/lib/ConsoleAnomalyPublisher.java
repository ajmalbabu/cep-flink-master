package cep.lib;

import java.io.Serializable;

public class ConsoleAnomalyPublisher implements AnomalyPublisher, Serializable {

    public static final long serialVersionUID = 1L;

    @Override
    public void publishAnomaly(Events events) {
        System.out.println("\n **************** Anomaly detected for " + events + " *********\n");
    }
}
