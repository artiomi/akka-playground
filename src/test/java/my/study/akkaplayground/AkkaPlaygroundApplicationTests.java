package my.study.akkaplayground;

import akka.actor.typed.ActorSystem;
import my.study.akkaplayground.actors.RootBehaviour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AkkaPlaygroundApplicationTests {

    @Autowired
    private ActorSystem<RootBehaviour.RootActor> rootBehaviourActorSystem;

    @Test
    void testInitRootActor() {
    	rootBehaviourActorSystem.tell(new RootBehaviour.RootActor("test root"));
        rootBehaviourActorSystem.tell(new RootBehaviour.RootActor("person"));
        rootBehaviourActorSystem.tell(new RootBehaviour.RootActor("person"));


    }

}
