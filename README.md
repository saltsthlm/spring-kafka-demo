> It's like a central hub for data/message/event brokerage.

## Core concepts

- cluster
    - controller (server that manages the cluster; if it fails, another one can take over)
    - broker
        - responsible for receiving messages from producers, assigning offsets, committing messages to disk, and responding to consumers' fetch requests and serving messages
        - usually 3, for redundancy
- producers (aka publishers, aka senders; other apps producing data and sending it to Kafka)
- consumers (aka receivers; other apps consuming data from Kafka)
- topics: messages categorized by domain
    - partitions: a topic can be split into partitions; each partition is primarily handled by one broker called the leader (though replicated on others), and can be read by a separate consumer, which allows high throughput
        - events (aka records, aka messages)
            - keys: further categorization of messages; all messages with the same key will be in the same partition
            - offset: basically the index of a message in the message queue; messages have a strict order and must be read in that order, so the offset helps consumers know where they are; the controllers know which consumer is on what offset

## Difference between Kafka and RabbitMQ and others
- real-time data streams instead of bigger sporadic events
- bigger throughput
- each producer is responsible for and handles the routing for its messages (in terms of partition); this allows the server cluster to not do that much work, which allows for higher throughout
- Kafka is "fanout" by default
- Kafka is better for large constant streams of data that is easy to process, less good for less frequent, sporadic events that need more processing
- [https://youtu.be/w8xWTIFU4C8?si=2x8OkLoHpt4fHGlv](https://youtu.be/w8xWTIFU4C8?si=2x8OkLoHpt4fHGlv)


## Examples
- **stream processing**:
    - bank transaction stream: a stream of events representing financial transactions; could be processed by a consumer, e.g. for fraud detection
    - geolocation updates from mobile phones
- **data integration**: microservice logs: collecting logs from multiple microservices; could be processed by a consumer that normalizes them and stores them in a central system
- **pub/sub messaging**: e-commerce order processing: publishing new orders to notify different services (inventory, payment, delivery)


## Important links
- Documentation and quick start: [https://kafka.apache.org/documentation/#quickstart_download](https://kafka.apache.org/documentation/#quickstart_download)
- Docker image (contains quick start): [https://hub.docker.com/r/apache/kafka](https://hub.docker.com/r/apache/kafka)
- Spring Kafka: [https://docs.spring.io/spring-kafka/reference/index.html](https://docs.spring.io/spring-kafka/reference/index.html)


## Useful commands

- start a Kafka container named "broker" and map the main broker port
  `docker run -d --name broker -p 9092:9092 apache/kafka:latest`

- from inside a broker, check the status of the cluster
  `./kafka-metadata-quorum.sh --bootstrap-server localhost:9092 describe --status`
  *(note: the scripts are in /opt/kafka/bin)*

- create a topic called "test-topic"
  `./kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092`

- print metadata about the "test-topic" topic
  `./kafka-topics.sh --describe --topic test-topic --bootstrap-server localhost:9092`

- get all messages in the "test-topic" topic
  `./kafka-console-consumer.sh --topic test-topic --from-beginning --bootstrap-server localhost:9092`

