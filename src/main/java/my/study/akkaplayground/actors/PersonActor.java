package my.study.akkaplayground.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import my.study.akkaplayground.model.Person;
import my.study.akkaplayground.model.Vehicle;

public class PersonActor extends AbstractBehavior<Person> {

    private final ActorRef<Vehicle> vehicleActorRef;

    private PersonActor(ActorContext<Person> context) {
        super(context);
        vehicleActorRef = context.spawn(VehicleActor.create(), "vehicle-actor");
    }

    public static Behavior<Person> create() {
        return Behaviors.setup(PersonActor::new);
    }

    @Override
    public Receive<Person> createReceive() {
        return newReceiveBuilder()
                .onMessage(Person.class, this::personInfo)
                .build();
    }

    private Behavior<Person> personInfo(Person person) {
        getContext().getLog().info("person call: {}", person);

        vehicleActorRef.tell(vInfo());

        return this;
    }

    private Vehicle vInfo() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationCountry("moldova");
        return vehicle;
    }


}
