package my.study.akkaplayground.configuration;

import akka.actor.typed.ActorSystem;
import my.study.akkaplayground.actors.RootBehaviour;
import my.study.akkaplayground.actors.RootCommand;

public class SpringAkkaConfiguration {

    public static final String ACTOR_ROOT_BEHAVIOUR = "root-behaviour";

    public ActorSystem<RootCommand> rootBehaviourActorSystem() {
        return ActorSystem.create(RootBehaviour.create(), ACTOR_ROOT_BEHAVIOUR);
    }


}
