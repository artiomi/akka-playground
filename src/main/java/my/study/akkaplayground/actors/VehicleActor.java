package my.study.akkaplayground.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.typed.javadsl.Behaviors;
import my.study.akkaplayground.actors.AccessRecordActor.RecordResponse;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static my.study.akkaplayground.actors.AccessRecordActor.RecordRequest;

public class VehicleActor {

    public static final String ACT_ACCESS_RECORD = "access-record";
    private final ActorContext<RootCommand> context;
    private final ActorRef<RootCommand> accessRecActorRef;

    private VehicleActor(ActorContext<RootCommand> context) {
        this.context = context;
        accessRecActorRef = context.spawn(AccessRecordActor.create(), ACT_ACCESS_RECORD);
    }

    public static Behavior<RootCommand> create() {
        return Behaviors.setup(ctx -> new VehicleActor(ctx).defineBehaviour());
    }

    private Behavior<RootCommand> defineBehaviour() {
        return Behaviors.receive(RootCommand.class)
                .onMessage(VehicleMessage.class, this::vehicleInfo)
                .onMessage(RecordResponse.class, this::propagateResponse)
                .build();
    }

    private Behavior<RootCommand> propagateResponse(RecordResponse response) {
        context.getLog().info("Received response: {}", response);
        return Behaviors.same();
    }

    private Behavior<RootCommand> vehicleInfo(VehicleMessage vehicle) {
        context.getLog().info("Vehicle Actor received request: {}", vehicle);

        CompletionStage<RootCommand> askReply = AskPattern
                .ask(accessRecActorRef, (ActorRef<RootCommand> replyTo)
                                -> new RecordRequest(vehicle.getVehicleNumber(), replyTo),
                        Duration.ofSeconds(5), context.getSystem().scheduler());

        askReply.thenAccept(vehicle.from::tell);

        return Behaviors.same();
    }

    public static class VehicleMessage implements RootCommand {

        private final String vehicleNumber;
        private final ActorRef<RootCommand> from;

        public VehicleMessage(String vehicleNumber, ActorRef<RootCommand> from) {
            this.vehicleNumber = vehicleNumber;
            this.from = from;
        }

        public String getVehicleNumber() {
            return this.vehicleNumber;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("VehicleMessage{");
            sb.append("vehicleNumber='").append(vehicleNumber).append('\'');
            sb.append(", from=").append(from);
            sb.append('}');
            return sb.toString();
        }

    }

}
