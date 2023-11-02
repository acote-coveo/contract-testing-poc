# Contract testing POC

This is a POC accomplished during Innovate 2023 - Fall Edition.

The goal was to test the full loop of contract testing using PACT. 

The setup:

1. A Scala client (consumer) that mimics the SearchAPI in our infrastructure.
2. A Java  server (provider) that mimics the micro-service that expose ML models.
3. A Pact broker leveraged to share pacts between consumer/provider.


The flow:

1. Start the broker using `docker-compose up`
2. Write Pacts in the Scala Client
3. Validate the pacts locally against a mock server.
4. Bump the version of the library (in build.sbt)
5. Publish the pacts using `sbt pactPublish`
6. Run the test in the ml-service. If needed, add more state fixtures.
7. Validate the state of the pacts in the broker.
8. Also, you can validate if you can deploy using: `pact-broker can-i-deploy --pacticipant searchapi --version 1.0.0 --broker-base-url http://localhost:9292`