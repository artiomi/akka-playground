package my.study.akkaplayground.configuration;

import akka.actor.typed.ActorSystem;
import my.study.akkaplayground.actors.RootBehaviour;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAkkaConfiguration {

    @Bean
    public ActorSystem<RootBehaviour.RootActor> rootBehaviourActorSystem() {
        return ActorSystem.create(RootBehaviour.create(), "root-behaviour");
    }


}
