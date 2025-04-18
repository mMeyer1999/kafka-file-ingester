package com.dalarionit.kafka_file_ingester

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.text
import org.typelevel.log4cats.StructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object FileIngester {

  given logger: StructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  def processFile(filePath: Path, topic: String): IO[Unit] = {
    val producer = KafkaProducerStub("default-topic")

    Files[IO].readAll(filePath)
      .through(text.utf8.decode)
      .through(text.lines)
      .evalMap(line =>
        logger.info("Sending line to producer") *> producer.send(line))
      .compile
      .drain
  }
}