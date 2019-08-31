name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "2.12.9"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.2.26",
  "org.scalatest"  %% "scalatest"   % "3.0.5"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.14.0" % Test
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
