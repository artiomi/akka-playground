package my.study.akkaplayground.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.study.akkaplayground.model.Person;

public class RootBehaviour extends AbstractBehavior<RootBehaviour.RootActor> {

    private final ActorRef<Person> personActorRef;

    public RootBehaviour(ActorContext<RootBehaviour.RootActor> context) {
        super(context);
        personActorRef = context.spawn(PersonActor.create(), "person-actor");
    }

    public static Behavior<RootActor> create() {
        return Behaviors.setup(RootBehaviour::new);
    }

    @Override
    public Receive<RootActor> createReceive() {
        return newReceiveBuilder()
                .onMessage(RootActor.class, ra -> !isPerson(ra), this::displayName)
                .onMessage(RootActor.class, this::isPerson, this::callPerson)
                .build();
    }

    private RootBehaviour displayName(RootActor param) {
        getContext().getLog().info("Method: {}, message: {}", "displayName", param);
        return this;
    }

    private boolean isPerson(RootActor actor) {
        return "person".equals(actor.getName());
    }

    private RootBehaviour callPerson(RootActor param) {
        Person person = new Person();
        person.setFirstName("test user");

        getContext().getLog().info("Method: {}, message: {}", "callPerson", param);

        personActorRef.tell(person);

        return this;
    }

    @Data
    @NoArgsConstructor
    public static class RootActor {

        private String name;

        public RootActor(String name) {
            this.name = name;
        }

    }

}
