package app.config

import app.domain.{HashTag, Tweet}
import app.service.TweetService
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.reactive.function.server.RouterFunctions._
import org.springframework.web.reactive.function.server.RequestPredicates._
import org.springframework.web.reactive.function.server.ServerResponse._


@Configuration
class Routes(tweetService: TweetService) {

  @Bean
  def routesFunc() =
    route(GET("/tweets"), _ => ok().body(tweetService.tweets(), classOf[Tweet]))
      .andRoute(GET("/hashtags/unique"), _ => ok().body(tweetService.hashtags(), classOf[HashTag]))

}