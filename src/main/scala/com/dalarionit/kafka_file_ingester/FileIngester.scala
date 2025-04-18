package com.dalarionit.kafka_file_ingester

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.text
import io.circe.parser
import org.typelevel.log4cats.StructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object FileIngester {

  given logger: StructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  def processFile(filePath: Path, topic: String, schemaPath: Path)(using logger: StructuredLogger[IO]): IO[Unit] = {
    for {
      schema <- ValidationSchema.loadSchema(schemaPath)
      producer = KafkaProducerStub(topic)
      _ <- Files[IO].readAll(filePath)
        .through(text.utf8.decode)
        .through(text.lines)
        .evalMap { line =>
          parser.parse(line) match
            case Left(err) =>
              logger.warn(s"Failed to parse line: $err")
            case Right(json) if !schema.validate(json) =>
              logger.warn(s"JSON validation failed for line: $line")
            case Right(validJson) =>
              logger.info(s"Ingesting valid JSON: ${validJson.spaces2}") *> producer.send(validJson.noSpaces)
        }
        .compile
        .drain
    } yield ()
  }
}