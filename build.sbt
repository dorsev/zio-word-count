import scala.util.Try
scalaVersion in ThisBuild := "2.12.11"
val zio = "dev.zio" % "zio" % "1.0.0"
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = Project("hello-world", file("."))
  .settings(
    Seq(
      organization := "io.bigpanda",
      name := "example",
      libraryDependencies += "dev.zio" %% "zio" % "1.0.1",
      libraryDependencies += "dev.zio" %% "zio-test" % "1.0.1" % "test",
      libraryDependencies += "dev.zio" %% "zio-test-sbt" % "1.0.1" % "test",
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    ))
