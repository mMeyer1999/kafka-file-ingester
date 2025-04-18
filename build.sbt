ThisBuild / scalaVersion     := "3.3.5"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.dalarionit"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-file-ingester",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect"     % "3.5.2",
      "co.fs2"        %% "fs2-core"        % "3.9.3",
      "co.fs2"        %% "fs2-io"          % "3.9.3",

      "org.typelevel" %% "log4cats-slf4j"  % "2.6.0",
      "ch.qos.logback" % "logback-classic" % "1.4.14",

      "com.github.fd4s" %% "fs2-kafka" % "3.1.0",

      "io.circe" %% "circe-core"    % "0.14.6",
      "io.circe" %% "circe-parser"  % "0.14.6",
      "io.circe" %% "circe-generic" % "0.14.6"
    )
  )