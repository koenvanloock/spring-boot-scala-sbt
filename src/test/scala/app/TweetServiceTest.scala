package app

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.testkit.TestProbe
import app.domain.{Author, HashTag, Tweet}
import app.repository.TweetRepository
import app.service.TweetService
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import reactor.core.publisher.Flux

import scala.collection.JavaConverters

@RunWith(classOf[JUnitRunner])
class TweetServiceTest extends Specification {


  "Tweetservice" should {

    val system = ActorSystem.create("mySys")
    implicit val materializer = ActorMaterializer.create(system)
    val repo = Mockito.mock(classOf[TweetRepository])
    val service = new TweetService(repo, materializer)

    "return all hashtags" in {
      val tweets = List(
        Tweet("hello from #Scala, where are you now #Kotlin", Author("Koen")),
        Tweet("Hello #ToThePoint", Author("Koen")))

      Mockito.when(repo.findAll()).thenReturn(Flux.fromIterable[Tweet](JavaConverters.asJavaCollection(tweets)))

      val probe = new TestProbe(system)

      Source.fromPublisher(service.hashtags()).to(Sink.actorRef(probe.ref, "completed")).run()

      probe.expectMsgType[HashTag] must beEqualTo(HashTag("#scala"))
      probe.expectMsgType[HashTag] must beEqualTo(HashTag("#kotlin"))
      probe.expectMsgType[HashTag] must beEqualTo(HashTag("#tothepoint"))
    }

  }
}
