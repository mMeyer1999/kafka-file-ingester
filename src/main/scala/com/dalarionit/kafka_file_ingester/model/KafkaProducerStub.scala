package com.dalarionit.kafka_file_ingester.model

import cats.effect.IO

case class KafkaProducerStub(topic: String) {
  
  def send(message: String): IO[Unit] = {
    IO.println(s"[KafkaStub] Sending to $topic: $message")
  }
}
