import zio._
import zio.console._
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

object wordCount extends zio.App {
  def run(args: List[String]) =
    myAppLogic.exitCode

  val myAppLogic =
    for {
      _ <- putStrLn(
        "Hello! What path do you want to word count? please enter full path")
      fullPath <- getStrLn
    } yield fullPath

  def countWords(str: String): Int = ???

  def readFileAsString(path: String): Task[String] = {
    var string = ""
    var strLine: String = null

    Task(new BufferedReader(new InputStreamReader(new FileInputStream(path))))
      .bracket(inputStream =>UIO(inputStream.close)) {
        br =>
          {
            while ({
              ({ strLine = br.readLine; strLine }) != null
            }) { // Print the content on the console
              string += strLine
            }
            Task(string)
          }
      }
  }

}
