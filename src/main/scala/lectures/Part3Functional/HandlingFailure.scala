package lectures.Part3Functional

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {

  def failure(): Try[String] = Failure(new RuntimeException)
  def success(): Try[String] = Success("ok")

  def fallback = failure().orElse(success())

  def aSuccess = Success(3)
  assert(aSuccess.map(x => x * 5) == Success(15))
  println(aSuccess.flatMap(x => Success(x +4)) )
  assert(aSuccess.filter(_ %2 == 0).isFailure)

  // Exercise: connect to server

  val hostname = "localhost"
  val port = "8080"
  def renderHtml(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val rand = new Random(System.nanoTime())
      if (rand.nextBoolean()) "<html> whatever "+ url + "... </html>"
      else throw new RuntimeException("Connection interuptred")
    }
  }

  class HttpService {
    val rand = new Random(System.nanoTime())
    def getConnection(host:String, port: String): Connection = {
      if (rand.nextBoolean()) new Connection
      else throw new RuntimeException("port unavailable")
    }
  }

  // exercise: print html iff no exceptions
  val service = new HttpService()

  val times = 1 to 30
  for {
    i <-times
  } yield {
    println("iter " + i)

     Try(service.getConnection(hostname, port)).flatMap(x => Try(x.get("abc")))
  }.foreach(renderHtml)
}
