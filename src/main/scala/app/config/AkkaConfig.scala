package app.config

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
  class AkkaConfig {

    @Bean def actorSystem() = ActorSystem.create("bootifulScala")

    @Bean def actorMaterializer() = ActorMaterializer.create(this.actorSystem())
}
