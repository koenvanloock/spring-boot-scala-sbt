package app.service

import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import app.domain.{HashTag, Tweet}
import app.repository.TweetRepository
import org.reactivestreams.Publisher
import org.springframework.stereotype.Service

import scala.collection.JavaConverters
import akka.stream.scaladsl.{Sink, Source}

@Service
class TweetService(tr: TweetRepository, am: ActorMaterializer) {

  def tweets(): Publisher[Tweet] = tr.findAll()

  def hashtags(): Publisher[HashTag] =
    Source
      .fromPublisher(tweets())
      .map(t => JavaConverters.asScalaSet(t.hashtags).toSet)
      .reduce((a, b) => a ++ b)
      .mapConcat(identity)
      .runWith(Sink.asPublisher(true)) {
        am
      }
}

