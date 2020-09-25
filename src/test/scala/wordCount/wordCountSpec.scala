import zio._
import zio.test.Assertion._
import zio.test._

object WordCountSpec extends DefaultRunnableSpec {
  override def spec =
    suite("WordCountSpec")(
      testM("count words properly") {
        for {
          count <- wordCount.wordCountEnv.countWords("/tmp/words")
        } yield assert(count)(equalTo(2))
      }
    )
}
