package lectures.part1Basics

object ValuesVariablesTypes extends App {
  val x: Int = 42
  println(x)
  // VALS ARE IMMUTABLE!
  // x = 2 // compiler error

  // type of val is optional
  val y = 43
  println(y)

  // types
  // string
  val s : String = "hi"
  println(s)

  val b: Boolean = false
  println(b)

  val c: Char = 'c'

  val shrt : Short = 12
  val lng : Long = 2341548932450L
  val flt: Float = 2.0f
  val dbl: Double = 3.14

  // variables can be reassigned
  var v: Int =4
  println(v)
  v=5
  println(v)
}
