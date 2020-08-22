name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "2.13.3"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.3.2",
  "org.scalatest"  %% "scalatest"   % "3.2.2"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.14.3" % Test
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
