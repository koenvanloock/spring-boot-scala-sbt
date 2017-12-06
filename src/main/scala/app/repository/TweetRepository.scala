package app.repository

import app.domain.Tweet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

trait TweetRepository extends ReactiveMongoRepository[Tweet, String]

