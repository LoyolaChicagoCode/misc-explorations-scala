name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "3.0.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.4.0-M8",
  "org.scalatest"  %% "scalatest"   % "3.2.9"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.15.4" % Test
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
