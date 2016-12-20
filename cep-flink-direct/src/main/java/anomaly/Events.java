package anomaly;

import java.io.Serializable;
import java.util.ArrayList;

public class Events implements Serializable {

    public static final long serialVersionUID = 1L;

    private ArrayList<Event> elements = new ArrayList<>();
    private boolean anomalyDetected = false;

    public Events() {
    }

    public Events(ArrayList<Event> elements, boolean anomalyDetected) {
        this.elements = elements;
        this.anomalyDetected = anomalyDetected;
    }

    public static Events empty() {
        return new Events();
    }

    public ArrayList<Event> getElements() {
        return new ArrayList<>(elements);
    }


    public void add(Event event) {
        elements.add(event);
    }

    public void setElements(ArrayList<Event> elements) {
        this.elements = elements;
    }

    public boolean isAnomalyDetected() {
        return anomalyDetected;
    }

    public void setAnomalyDetected(boolean anomalyDetected) {
        this.anomalyDetected = anomalyDetected;
    }

    public int size() {
        return elements.size();
    }

    public <T> T eventKey() {
        return (T) elements.stream().findFirst().map(Event::key).get();
    }


    @Override
    public String toString() {
        return "Events{" +
                " elements=" + elements +
                ", anomalyDetected=" + anomalyDetected +
                '}';
    }
}
