package app

import java.util

import app.config.Routes
import app.domain.{Author, Tweet}
import app.service.TweetService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebFlux
class TweetIntegrationTest extends Specification{

  val service = mock(classOf[TweetService])

  val routes = new Routes(service)
  val webClient: WebTestClient = WebTestClient.bindToRouterFunction(routes.routesFunc()).build()

  val mapper = new ObjectMapper()

  "RoutesHandler" should {

    "return all tweets" in {
      val tweets = List(
        Tweet("hello from #Scala, where are you now #Kotlin", Author("Koen")),
        Tweet("Hello #ToThePoint", Author("Koen")))

      val tweetFlux = Flux.fromIterable[Tweet](asJavaCollection(tweets))
      when(service.tweets()).thenReturn(tweetFlux)

      val list = webClient
        .get
        .uri("/tweets")
        .accept(MediaType.APPLICATION_JSON)
        .exchange
        .expectStatus.isOk
        .expectBody(classOf[java.util.List[java.util.LinkedHashMap[String, Any]]]).returnResult().getResponseBody



      asScalaIterator(list.iterator())
        .toList
        .map { linkedMap =>
          val scalaMap = mapAsScalaMap(linkedMap)
          Tweet(scalaMap("text").asInstanceOf[String], Author(scalaMap("author").asInstanceOf[util.LinkedHashMap[String, String]].get("handle")))
        } must beEqualTo(tweets)

    }
  }


}
