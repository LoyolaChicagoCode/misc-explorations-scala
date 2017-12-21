name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.2.9",
  "org.scalatest"  %% "scalatest"   % "3.0.1"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.13.4" % Test
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
