
# Word Count - part #1

* Ask for path
* Read file
* Count words
* Print length


---

# Demo

```scala
import zio._
import zio.console._
import scala.io.Source
object wordCount extends zio.App {
   def run(args: List[String]) =
    myAppLogic.exitCode

    val myAppLogic =
    for {
      _ <- putStrLn(
        "Hello! What path do you want to word count? please enter full path")
    fullPath <- getStrLn
    } yield fullPath

  def countWords(str: String):Int = ???
  def readFileAsString(path: String): String = ???
} 

```


---

# How to read lines from a file ? 

```scala
    import java.io.BufferedReader
    import java.io.FileInputStream
    import java.io.InputStreamReader

  def readFileAsString(path: String): String = {
    var string = ""

    val fstream = new FileInputStream(path)
    val br = new BufferedReader(new InputStreamReader(fstream))
    var strLine: String = null
    while ({
      ({ strLine = br.readLine; strLine }) != null
    }) { 
      string += strLine
    }

    string
  }

```


---

# Where's the bug?

---

# introducing bracket


```scala
  import java.io.BufferedReader
  import java.io.FileInputStream
  import java.io.InputStreamReader
  import zio._
  import zio.console._
  def readFileAsString(path: String): Task[String] = {
    var string = ""
    var strLine: String = null

    Task(new BufferedReader(new InputStreamReader(new FileInputStream(path))))
      .bracket(inputStream =>UIO(inputStream.close)) {
        br =>
          {
            while ({
              ({ strLine = br.readLine; strLine }) != null
            }) { 
              string += strLine
            }
            Task(string)
          }
      }
  }
```

---

# Demo

* How can we know this worked ? 

---

# Zip Right

```scala
  def readFileAsString(path: String): Task[String] = {
    var string = ""
    var strLine: String = null

    Task(new BufferedReader(new InputStreamReader(new FileInputStream(path))))
      .bracket(inputStream => UIO(println("closing")) *> UIO(inputStream.close)) {
        br =>
          {
            while ({
              ({ strLine = br.readLine; strLine }) != null
            }) { 
              string += strLine
            }
            Task(string)
          }
      }
  }
```

---

# Full code

```scala
  object wordCount extends zio.App {

  def run(args: List[String]) =
    myAppLogic.exitCode

     def readFileAsString(path: String): Task[String] = {
    var string = ""
    var strLine: String = null

    Task(new BufferedReader(new InputStreamReader(new FileInputStream(path))))
      .bracket(inputStream => UIO(println("closing")) *> UIO(inputStream.close)) {
        br =>
          {
            while ({
              ({ strLine = br.readLine; strLine }) != null
            }) { 
              string += strLine
            }
            Task(string)
          }
      }
  }

  def countWords(str: String): UIO[Int] = UIO(str.split(" ").length)

  val myAppLogic =
    for {
      _ <- putStrLn(
        "Hello! What path do you want to word count? please enter full path")
      fullPath <- getStrLn
      contents <- readFileAsString(fullPath)
      count <- countWords(contents)
      _ <- putStrLn(count.toString)
    } yield count

}

```


---

# Summary

* Unpure code is wrapped in ZIO.effect
* Pure values are wrapped in UIO

---

# Scheduling & retrying

* error handling
* Scheduling & retrys

---


```scala
ZIO.effectTotal(println(1)).orDie
ZIO.effectTotal(zio.console.putStrLn("hello world").repeatN(10))
zio.console.putStrLn("heloo world").repeat(Schedule.forever)
zio.console.putStrLn("heloo world").repeat(Schedule.recurs(10) andThen Schedule.spaced(1.second))
```

---