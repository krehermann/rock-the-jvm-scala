package lectures.Part3Functional

import scala.util.Random

object Sequences extends App{

// Range is useful sequence. `to` => inclusive
  // until => exclusive
  val inc : Seq[Int] = 1 to 10
  inc.foreach(println)

  val excl: Seq[Int] = 1 until 10
  excl.foreach(println)


  def writeTime(seq: Seq[Int], n: Int): Double = {
  val randomGen = Random
  val measurements = for {
    //val start = System.nanoTime()
    k <- 1 to n
  } yield {
    val start = System.nanoTime()
    seq.updated(randomGen.nextInt(seq.length), randomGen.nextInt())
    System.nanoTime() - start
  }
    measurements.sum/n
  }

  val len = 100000
  val v = (1 to len).toVector
  val l = (1 to len).toList
  //println(v)
  //println(l)

  println("l write time ", writeTime(l, 1000))
  println("v write time ", writeTime(v, 1000))

}

