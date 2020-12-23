package my.study.akkaplayground;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import my.study.akkaplayground.actors.RootCommand;
import my.study.akkaplayground.actors.VehicleActor;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class AkkaPlaygroundApplicationTest {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testVehicleActor() {
        TestProbe<RootCommand> testProbe = testKit.createTestProbe();
        ActorRef<RootCommand> rootActor = testKit.spawn(VehicleActor.create());
        rootActor.tell(new VehicleActor.VehicleMessage("123", testProbe.getRef()));
        RootCommand message = testProbe.receiveMessage();
        Assert.assertNotNull(message);
        testKit.system().log().info("Message: {}", message);
    }

}
