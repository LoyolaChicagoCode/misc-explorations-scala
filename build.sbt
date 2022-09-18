name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "3.1.3"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Yexplicit-nulls", "-language:strictEquality")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.4.0-M12",
  "org.scalatest"  %% "scalatest"   % "3.2.13"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.16.0" % Test
)

console / initialCommands := """
                             |import scalaz._
                             |import Scalaz._
                             |""".stripMargin

scalacOptions ++= Seq("-rewrite", "-new-syntax")
