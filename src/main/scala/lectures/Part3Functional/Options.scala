package lectures.Part3Functional

import java.nio.channels.NonWritableChannelException
import scala.util.Random

object Options extends App{

  val o : Option[Int] = Some(4)
  val n : Option[Int] = None

  println(o, n)

  def unsafe(): String = null
  val result= Option(unsafe())
  // never need to do null checks
  println(result)

  def safe(): String = "this is ok"
  val chainedResult = Option(unsafe()).orElse(Option(safe()))
  println(chainedResult)

  def optFunc() : Option[String] = None
  def backup(): Option[String] = Some("good")

  val chain2 = optFunc().orElse(backup())
  println(chain2)

  // functions on options
  println(o.isEmpty)
  // unsafe, don't use get. for illustration
  println(o.get)

  // map, flatMap, filter
  println(o.map(_*2))
  println(o.filter(_ %2 == 0))
  println(o.flatMap(i => Some(List(i, i*3))))

  // since we have map, flatMap, and filter, we have for comprehensions
  /*
  Exercise: establish connnection given api/config below

     */
  val config: Map[String,String] = Map(
    "host" -> "0.0.0.0",
  "port" -> "80",
  )

  class Connection {
    def connect = "Connected"
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host:String, port:String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  val times = 1 to 30
  val result2 = for {
    i <- times
  } yield {
    val x = Connection(config.getOrElse("host", ""), config.getOrElse("port", "0"))
    println("iter", i)
    x.foreach(y => println(y.connect))
    x.map(x => x.connect == "Connected")
  }

  println("got connections", result2.count(elem => !elem.isEmpty))

  //alternative implemation

  val host = config.get("host") // this is an option
  val port = config.get("port")
  //host.map(h => Connection(h, port))
  for {
    i <- times
  } yield {
    // note that we need flatMap here, not map, which i tried first
    // the distinction is that map applies a function and wraps the result in an option
    // whereas flatMap apply the function on the inner element of the option, if it's not empty
    // and return the result wrapped by an option.

    // this means that using map here is like, reading from innermost
    // p => Connection... : Option(Option(Connection) // func acts on entire type i.e. option and wraps it
    // h => Option(Option(Connection) : Option(Option(Option(Connection)))
    // flatMap is like
    // p => Connection... : Option(Connection) [func acts on inner type, i.e. Connection and wraps it]
    // h => Option(Connection) : Option(Connection)
    val myConnection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
    val status = myConnection.map(c => c.connect)
    println("status " + i + " " + status)
  }

  // now as a chain.
  // given host, get port. given host and port get connection. given connect, get status. for status, print
  println("chain")
  config.get("host").
    flatMap(h => config.get("port")
    .flatMap(p => Connection(h,p))
      .map(c => c.connect)).foreach(println)

  // as for comp
  println("for comp impl")
  val stat = for {
    h <- config.get("host")
    p <- config.get("port")
    c <- Connection(h,p)
  } yield {
    c.connect
  }.foreach(println)
}
