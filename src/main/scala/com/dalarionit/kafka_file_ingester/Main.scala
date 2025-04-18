package com.dalarionit.kafka_file_ingester

import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream
import fs2.io.file
import fs2.io.file
import fs2.io.file.{Files, Path}
import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.typelevel.log4cats.{StructuredLogger, LoggerFactory}

import scala.concurrent.duration.*

object Main extends IOApp {

  given logger: StructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  override def run(args: List[String]): IO[ExitCode] = {
    val filePath = sys.env.getOrElse("FILE_PATH", "data/input.csv")
    val kafkaTopic = sys.env.getOrElse("KAFKA_TOPIC", "default-topic")

    for {
      _ <- logger.info(s"Starting processing file from $filePath into Kafka topic $kafkaTopic.")
      _ <- FileIngester.processFile(Path(filePath), kafkaTopic)
      _ <- logger.info("File processing completed.")
    } yield ExitCode.Success
  }
}
