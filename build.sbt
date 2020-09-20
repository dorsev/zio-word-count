import scala.util.Try
scalaVersion in ThisBuild := "2.12.11"
val zio = "dev.zio" % "zio" % "1.0.0"
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = Project("hello-world", file("."))
  .settings(Seq(
    organization := "io.bigpanda",
    name := "example",
    libraryDependencies += "dev.zio" %% "zio" % "1.0.1",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  ))
