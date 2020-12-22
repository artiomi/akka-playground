package my.study.akkaplayground.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import my.study.akkaplayground.model.Vehicle;

public class VehicleActor extends AbstractBehavior<Vehicle> {

    private VehicleActor(ActorContext<Vehicle> context) {
        super(context);
    }

    public static Behavior<Vehicle> create() {
        return Behaviors.setup(VehicleActor::new);
    }

    @Override
    public Receive<Vehicle> createReceive() {
        return newReceiveBuilder().onAnyMessage(this::vehicleInfo).build();
    }

    private Behavior<Vehicle> vehicleInfo(Vehicle vehicle) {
        getContext().getLog().info("Vehicle Actor received message: {}", vehicle);
        return this;
    }

}
