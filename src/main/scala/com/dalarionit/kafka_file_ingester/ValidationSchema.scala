package com.dalarionit.kafka_file_ingester

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.text
import io.circe._
import io.circe.parser._
import io.circe.Codec
import io.circe.generic.semiauto._

final case class ValidationRule(
  field: String,
  `type`: String,
  required: Boolean
)

final case class ValidationSchema(rules: Seq[ValidationRule]):
  def validate(json: Json): Boolean =
    rules.forall { rule =>
      val cursor = json.hcursor.downField(rule.field)
      cursor.focus match
        case Some(value) =>
          (rule.`type`, value) match
            case ("string", _)  if value.isString  => true
            case ("int", _)     if value.isNumber && value.asNumber.exists(_.toInt.isDefined) => true
            case ("boolean", _) if value.isBoolean => true
            case _ => false
        case None =>
          !rule.required
    }

object ValidationSchema {
  given Codec[ValidationRule] = deriveCodec
  given Codec[ValidationSchema] = deriveCodec

  def loadSchema(path: Path): IO[ValidationSchema] =
    Files[IO].readAll(path)
      .through(text.utf8.decode)
      .compile
      .string
      .flatMap { schema =>
        parse(schema).flatMap(_.as[List[ValidationRule]]) match
          case Right(rules) => IO.pure(ValidationSchema(rules))
          case Left(error) =>
            IO.raiseError(new Exception(s"Failed to parse schema: $error"))
      }
}
