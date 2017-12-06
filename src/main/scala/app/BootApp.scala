package app

import app.domain.{Author, Tweet}
import app.repository.TweetRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux

@SpringBootApplication
class BootApp {
  @Bean
  def init(tr: TweetRepository): ApplicationRunner = args => {
    val viktor = Author("viktorklang")
    val jonas = Author("jboner")
    val josh = Author("starbuxman")
    val tweets = Flux.just(
      Tweet("Woot, Konrad will be talking about #Enterprise #Integration done right! #akka #alpakka", viktor),
      Tweet("#scala implicits can easily be used to model Capabilities, but can they encode Obligations easily?\n\n* Easy as in: ergonomically.", viktor),
      Tweet("This is so cool! #akka", viktor),
      Tweet("Cross Data Center replication of Event Sourced #Akka Actors is soon available (using #CRDTs, and more).", jonas),
      Tweet("a reminder: @SpringBoot lets you pair-program with the #Spring team.", josh),
      Tweet("whatever your next #platform is, don't build it yourself. \n\nEven companies with the $$ and motivation to do it fail. a LOT.", josh)
    )
    tr
      .deleteAll()
      .thenMany(tr.saveAll(tweets))
      .thenMany(tr.findAll())
      .subscribe((t: Tweet) => println(
        s"""=====================================================
           |@${t.author.handle} ${t.hashtags}
           |${t.text}
         """.stripMargin
      ))
  }
}
