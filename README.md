# [PoC] Request and Response based on streams

After a discussion with a friend, I decided to try to implement a small project to cover the Requests & Response Http project with additional requirements.
The aim is to be aware of concepts, pros & cons, and limitations to drive projects with correct architecture principles or learn from misunderstandings.

### General goals
* deeper understand reactive architecture
* better understanding of Kafka & environment
* try to find solutions for problems in that process

### Initial planned solutions 
* try to implement reactiveness with [Reactive manifesto](https://www.reactivemanifesto.org/)
  * **scalable**
      * I assume there is infinity number of producers & consumers
      * using consumer groups
  * **message-drive**
    * based on Kafka
      * using topics with partitions
      * aim for `exactly one processing`
      * guaranty of atomic processing events
          * consider Outbox pattern
  * **independence/autonomy** of consumers 
    * consider sharing/local data per node for optimization

### Development tools
* using [docker-compose](https://docs.docker.com/compose/)
* using [AKHQ](https://github.com/tchiotludo/akhq) for monitoring kafka

## [ADR](doc/adr/)

For future retrospective perspective, I would like to full records my problems, perspectives and solutions.

## Today I Learned

This is a little longer list as was initial to this PoC
* till now, I read
    * [Transaction outbox pattern](https://microservices.io/patterns/data/transactional-outbox.html)
    * [Exactly one processing](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/)

[03.04.2022]

* [Choreography Sagas in DDD - Chain of Integration Events?](https://stackoverflow.com/questions/63597403/choreography-sagas-in-ddd-chain-of-integration-events)
* [Pattern: Saga](https://microservices.io/patterns/data/saga.html)
* [Java for the Haters in 100 Seconds](https://www.youtube.com/watch?v=m4-HM_sCvtQ)


[05.04.2022]

* [The Top 5 Redis-Based Java Objects](https://redisson.org/articles/the-top-5-redis-based-objects-used-in-java.html)
* [A Guide to Redis with Redisson](https://www.baeldung.com/redis-redisson)
* [Mono switchIfEmpty vs defaultIfEmpty](https://tedblob.com/mono-switchifempty-vs-defaultifempty/)

[09.04.2020]

* Whole list of related solutions to: [Topic not present in metadata after 60000 ms](https://stackoverflow.com/questions/63714401/org-apache-kafka-common-errors-timeoutexception-topic-not-present-in-metadata-a)
* [My Client Won’t Connect to My Apache Kafka Cluster in Docker](https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/)
  * [Example of local Kafka configuration](https://github.com/confluentinc/cp-all-in-one)
