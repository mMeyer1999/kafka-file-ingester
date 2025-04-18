package com.dalarionit.kafka_file_ingester

import cats.effect.IO

import com.dalarionit.kafka_file_ingester.util.Ansi._

case class KafkaProducerStub(topic: String) {

  def send(message: String): IO[Unit] = {
    IO.println(s"$green[KafkaStub] Sending to $topic: $message")
  }
}
