name := "misc-explorations-scala"

version := "0.0.1"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
                                