import com.itv.scalapact.plugin._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

enablePlugins(ScalaPactPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "scala-client",
    idePackagePrefix := Some("org.example"),
    libraryDependencies ++= List(
      "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M6",
      "com.softwaremill.sttp.client4" %% "json4s" % "4.0.0-M6",
      "org.json4s" %% "json4s-native" % "4.0.6",

      "io.github.jbwheatley" %% "pact4s-scalatest" % "0.10.0" % "test",
      "org.scalatest" %% "scalatest" % "3.2.17" % "test"
    )
  )


pactBrokerAddress := "http://localhost:9292"
pactContractVersion := "1.0.1"
areScalaPactContracts := false
allowSnapshotPublish := true