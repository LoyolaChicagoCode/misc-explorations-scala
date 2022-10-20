name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "3.2.0"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Yexplicit-nulls", "-Ysafe-init", "-language:strictEquality")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.4.0-M12",
  "org.scalatest"  %% "scalatest"   % "3.2.14"  % Test,
  "org.scalacheck" %% "scalacheck"  % "1.17.0" % Test
)

console / initialCommands := """
                             |import scalaz._
                             |import Scalaz._
                             |""".stripMargin
