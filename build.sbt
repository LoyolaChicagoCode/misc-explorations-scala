name := "misc-explorations-scala"

version := "0.2"

scalaVersion := "3.3.0"

scalacOptions += "@.scalacOptions.txt"

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core" % "7.4.0-M13",
  "org.scalatest"  %% "scalatest"   % "3.2.16"    % Test,
  "org.scalacheck" %% "scalacheck"  % "1.17.0"    % Test
)

console / initialCommands := """
                             |import scalaz._
                             |import Scalaz._
                             |""".stripMargin
