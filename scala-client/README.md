# Scala-client

A Scala service that interacts with a Java Service. 

It defines the pact with the Java service using Pact. 

Some useful SBT commands:

```shell
# To clean the local pacts
sbt clean

# To test the pacts
sbt test

# To publish the pacts to the broker
sbt pactPublish
```