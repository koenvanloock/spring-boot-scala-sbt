name := "bootUpTheStairs"


val scalaMajorVersion = "2.12"
scalaVersion := scalaMajorVersion + ".4"

resolvers += "org.springframework.boot" at "http://maven.springframework.org/milestone"


val springBootReactorVersion = "2.0.0.M6"
val akkaVersion = "2.5.6"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-webflux" % springBootReactorVersion,
  "org.springframework.boot" % "spring-boot-starter-test" % springBootReactorVersion,
  "org.springframework.boot" % "spring-boot-starter-data-mongodb-reactive" % springBootReactorVersion,
  "com.typesafe.akka" % s"akka-actor_$scalaMajorVersion" % akkaVersion,
  "com.typesafe.akka" % s"akka-testkit_$scalaMajorVersion" % akkaVersion,
  "com.typesafe.akka" % s"akka-stream_$scalaMajorVersion" % akkaVersion,
  "org.specs2" %% "specs2-core" % "4.0.0" % Test,
  "org.specs2" %% "specs2-junit" % "4.0.0" % Test,
  "org.mockito" % "mockito-all" % "1.9.5" % Test

)

mainClass in (Compile, run) := Some("app.BootApplicationRunner")
