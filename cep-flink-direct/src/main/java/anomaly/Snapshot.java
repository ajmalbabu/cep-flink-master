package anomaly;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

public class Snapshot implements Serializable {

    public static final long serialVersionUID = 1L;


    private ArrayList<Event> events = new ArrayList<>();
    private Duration dataCleanupDuration = Duration.ofSeconds(5);
    private long eventOffset = 0;

    public Snapshot() {
    }

    public static Snapshot empty() {
        return new Snapshot();
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public Snapshot removeOlderEvents(long checkpointTimestamp) throws Exception {
        Snapshot snapshot = empty();
        events.stream().filter(event -> (event.createTime().toEpochMilli() + dataCleanupDuration.toMillis()) >= checkpointTimestamp).forEach(snapshot::add);
        snapshot.setEventOffset(this.eventOffset);
        return snapshot;
    }

    public void add(Event event) {
        events.add(event);
    }

    public long nextEventOffset() {
        return ++eventOffset;
    }

    public void setEventOffset(long eventOffset) {
        this.eventOffset = eventOffset;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "events=" + events +
                ", eventOffset=" + eventOffset +
                ", dataCleanupDuration=" + dataCleanupDuration +
                '}';
    }
}
