package my.study.akkaplayground;

import akka.actor.typed.ActorSystem;
import my.study.akkaplayground.actors.RootBehaviour;
import my.study.akkaplayground.actors.RootCommand;

import java.io.IOException;

public class AkkaPlaygroundApplication {

    public static final String ACT_SYS_ROOT_BEHAVIOUR = "root-behaviour";

    public static void main(String[] args) {
        final ActorSystem<RootCommand> actorSystem = ActorSystem.create(RootBehaviour.create(), ACT_SYS_ROOT_BEHAVIOUR);
        actorSystem.tell(new RootBehaviour.RootActor("AAA-123"));

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            actorSystem.terminate();
        }
    }

}

