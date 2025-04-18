# kafka-file-ingester

**A lightweight Scala 3 tool to ingest structured text files (CSV, NDJSON) line by line into Kafka.**

Useful for:
- Data onboarding & ingestion pipelines
- Replacing legacy batch imports with streaming logic
- Demoing fs2, Kafka, and typelevel ecosystem in a real-world use case

---

## 🚀 Features
- Stream-based file reading (with `fs2`)
- Logging of each line as it is ingested
- Kafka Producer stub for local development (can be swapped for real `fs2-kafka` integration)
- Easy configuration via environment variables
- Minimal dependencies, clean modular structure

---

## 🔧 Usage

### 1. Prepare a test file:
Place a file `input.csv` inside the `/data` directory:
```bash
mkdir -p data && echo "foo\nbar\nbaz" > data/input.csv
```

### 2. Run the app:
```bash
sbt run
```
Or with custom path/topic:
```bash
INPUT_FILE=data/input.csv KAFKA_TOPIC=my-topic sbt run
```

---

## 📁 Project Structure
```
src/
├── Main.scala           // Entry point, loads config, starts ingestion
├── FileIngester.scala   // Streaming logic to read and process file lines
└── KafkaProducerStub.scala // Stub simulating Kafka publishing (to be replaced)
```

---

## 📦 Dependencies
- Scala 3
- cats-effect 3
- fs2-core + fs2-io
- log4cats + slf4j + logback

---

## 🔜 TODO / Ideas
- [ ] Swap stub with `fs2-kafka` integration
- [ ] Add JSON validation and transformation layer (e.g. circe)
- [ ] Add retry / error handling / skip logic
- [ ] Dockerize with `docker-compose.yml` + Kafka broker
- [ ] Add property-based tests and schema samples

---

## 📝 License
MIT License – see LICENSE file.

---

> Made with ❤️ using Scala 3, for clean data flows and composable ingestion pipelines.
