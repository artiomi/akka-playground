package my.study.akkaplayground.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import my.study.akkaplayground.actors.AccessRecordActor.RecordResponse;
import my.study.akkaplayground.actors.VehicleActor.VehicleMessage;

public class RootBehaviour {

    private final ActorRef<RootCommand> vehicleActorRef;
    private final ActorContext<RootCommand> context;

    private RootBehaviour(ActorContext<RootCommand> context) {
        this.context = context;
        vehicleActorRef = context.spawn(VehicleActor.create(), "vehicle-actor");

    }

    public static Behavior<RootCommand> create() {
        return Behaviors.setup(ctx -> new RootBehaviour(ctx)
                .messagesBehaviour());
    }

    private Behavior<RootCommand> messagesBehaviour() {
        return Behaviors.receive(RootCommand.class)
                .onMessage(RootActor.class, this::checkVehicleNumber)
                .onMessage(RecordResponse.class, this::propagateResponse)
                .build();
    }

    private Behavior<RootCommand> propagateResponse(RecordResponse response) {
        context.getLog().info("Received response: {}", response);
        return Behaviors.same();
    }


    private Behavior<RootCommand> checkVehicleNumber(RootActor param) {

        context.getLog().info("Method: {}, message: {}", "callPerson", param);
        vehicleActorRef.tell(new VehicleMessage(param.getName(), context.getSelf()));

        return Behaviors.same();
    }

    public static class RootActor implements RootCommand {

        private final String name;

        public RootActor(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("RootActor{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }

    }

}