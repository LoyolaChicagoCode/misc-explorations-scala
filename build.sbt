name := "misc-explorations-scala"

version := "0.0.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.5",
  "org.scalatest" % "scalatest_2.10" % "2.0.1-SNAP" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.1" % "test"
)

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin