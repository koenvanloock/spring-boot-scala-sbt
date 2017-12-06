package app.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty
import scala.collection.JavaConverters

@Document
case class Author(@BeanProperty @Id handle: String)

@Document
case class Tweet(@BeanProperty @Id text: String, @BeanProperty author: Author) {
  @BeanProperty
  lazy val hashtags: java.util.Set[HashTag] = JavaConverters.setAsJavaSet(
    text
      .split(" ")
      .collect {
        case t if t.startsWith("#") => HashTag(t.replaceAll("[^#\\w]", "").toLowerCase())
      }
      .toSet
  )

}
@Document
case class HashTag(@BeanProperty @Id tag: String)

