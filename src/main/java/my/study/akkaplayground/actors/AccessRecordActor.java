package my.study.akkaplayground.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

import java.util.Objects;

public class AccessRecordActor {

    private final ActorContext<RootCommand> context;

    private AccessRecordActor(ActorContext<RootCommand> context) {
        this.context = context;
    }

    public static Behavior<RootCommand> create() {
        return Behaviors.setup(ctx -> new AccessRecordActor(ctx).defineBehaviour());
    }

    private Behavior<RootCommand> defineBehaviour() {
        return Behaviors.receive(RootCommand.class)
                .onMessage(RecordRequest.class, this::vehicleInfo)
                .build();
    }

    private Behavior<RootCommand> vehicleInfo(RecordRequest request) {
        context.getLog().info("vehicleInfo: {}", request);
        request.from.tell(new RecordResponse(request.vehicleNumber, context.getSelf(), true));
        return Behaviors.same();
    }

    public static class RecordRequest implements RootCommand {

        private final String vehicleNumber;
        private final ActorRef<RootCommand> from;

        public RecordRequest(String vehicleNumber, ActorRef<RootCommand> from) {
            this.vehicleNumber = vehicleNumber;
            this.from = from;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("RecordRequest{");
            sb.append("vehicleNumber='").append(vehicleNumber).append('\'');
            sb.append(", from=").append(from);
            sb.append('}');
            return sb.toString();
        }

    }

    public static class RecordResponse implements RootCommand {

        private final String vehicleNumber;
        private final ActorRef<RootCommand> from;
        private final boolean canGetIn;

        public RecordResponse(String vehicleNumber, ActorRef<RootCommand> from, boolean canGetIn) {
            this.vehicleNumber = vehicleNumber;
            this.from = from;
            this.canGetIn = canGetIn;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("RecordResponse{");
            sb.append("vehicleNumber='").append(vehicleNumber).append('\'');
            sb.append(", from=").append(from);
            sb.append(", canGetIn=").append(canGetIn);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecordResponse that = (RecordResponse) o;
            return canGetIn == that.canGetIn &&
                    vehicleNumber.equals(that.vehicleNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vehicleNumber, canGetIn);
        }

    }

}
