package my.study.akkaplayground.controler;

import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.stream.javadsl.Source;
import my.study.akkaplayground.actors.RootBehaviour;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class SampleController {

    private final ActorSystem<RootBehaviour.RootActor> system;

    public SampleController(ActorSystem<RootBehaviour.RootActor> system) {
        this.system = system;
    }

    @RequestMapping("/")
    public Source<String, NotUsed> index() {
        return Source.repeat("Hello world!").intersperse(";\n").take(10);
    }

    @PostConstruct
    public void setup() {
        Logger log = system.log();
        log.info("Injected ActorSystem Name -> {}", system.name());
        log.info("Property ActorSystemName -> {}", system.name());
    }

}
