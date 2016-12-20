package anomaly;

import org.apache.flink.streaming.api.checkpoint.CheckpointedAsynchronously;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.Instant;
import java.util.List;

public class EventStreamSource extends RichSourceFunction<Event> implements CheckpointedAsynchronously<Snapshot> {

    private volatile boolean isRunning = true;

    private SampleData sampleData = new SampleData();
    private Snapshot snapshot = Snapshot.empty();
    private boolean stateWasRestored = false;


    private int numberOfRuns = 1;

    @Override
    public void run(SourceContext<Event> ctx) throws Exception {

        while (isRunning) {

            Thread.sleep(300);

            // Output collection and state update should be atomic
            synchronized (ctx.getCheckpointLock()) {

                collectIfSnapshotWasRestored(ctx);

                collectNextEvents(ctx);


            }

//            simulateFailure();
        }
    }

    /**
     * If snap shot was restored because of a failure, then valid events in the snapshots should be collected and sent.
     */
    private void collectIfSnapshotWasRestored(SourceContext<Event> ctx) throws Exception {

        if (stateWasRestored) {
            snapshot.removeOlderEvents(Instant.now().toEpochMilli()).getEvents().stream().forEach(ctx::collect);
            stateWasRestored = false;
        }
    }

    private void collectNextEvents(SourceContext<Event> ctx) {

        // Get the events from the offset. Offset is in the snapshot.
        long nextOffset = snapshot.nextEventOffset();
        List<Event> events = sampleData.nextEvents(nextOffset);
        events.stream().forEach(event -> {
            // Output collection
            ctx.collect(event);
            // State update
            snapshot.add(event);
        });
    }

    /**
     * Call this method to simulate a failure. So that resiliency of the solution by restarting from
     * the check point can be tested.
     */
    private void simulateFailure() {
        if (numberOfRuns++ % 20 == 0) {
            throw new RuntimeException("Break the process.");
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }


    @Override
    public Snapshot snapshotState(long checkpointId, long checkpointTimestamp) throws Exception {

        System.out.println("Take source SnapShot with checkpointId: " + checkpointId + " and CheckpointTimestamp: " + checkpointTimestamp);

        return snapshot.removeOlderEvents(checkpointTimestamp);
    }

    @Override
    public void restoreState(Snapshot snapshot) throws Exception {

        System.out.println("\n **************** Restore check pointed snapshot: " + snapshot + "\n ************");

        this.snapshot = snapshot;
        stateWasRestored = true;
    }


}
