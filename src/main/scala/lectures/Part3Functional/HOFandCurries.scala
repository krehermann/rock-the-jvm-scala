package lectures.Part3Functional

import scala.annotation.tailrec

object HOFandCurries extends App{

 // val superFunc: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???
  // superFunc is function of two parameters that returns another function
  // param1 is an Int
  // param2 is a function of String and f(int) -> bool that returns an Int
  // return type is a function of Int => Int (f(int) -> int)

  // nTimes: function that applies itself to an argument N times
  def nTimes(f:(Int => Int), n: Int, x: Int): Int = {
    @tailrec
    def helper(countdown:Int, accumulation:Int):Int = {
      if (countdown == 0) accumulation
      else helper(countdown-1, f(accumulation))
    }
    if (n == 0) x
    else helper(n-1, f(x))
  }

  @tailrec
  def nTimesRec(f: Int => Int, n: Int, x: Int): Int = {
    if (n == 0) x
    else nTimesRec(f, n-1, f(x))
  }

  def nTimesOp(f: Int => Int, n: Int): Int => Int = {
    println("op n " + n)
    //val identity: Int => Int = _ * 1
    // I got the identity part immediately. I knew that I needed recursion. The
    // missing piece that I didn't see the definition of a lambda lets me
    // declare a parameter, x, whose value maps to the argument of the returned
    // function. that is, I couldn't figure out for myself how to express
    // access to the parameter of returned function of nTimesOp.

    // part of my confusion was reflected in my implementation of the identity
    // _ *1
    if (n == 0) (x:Int) => x
    else (x:Int) => nTimesOp(f, n-1)(f(x))
  }

  val double : Int => Int =  _ * 2

  assert( nTimes(double, 1, 2) == 4)
  assert( nTimes(double, 2, 2) == 8)
  assert( nTimes(double, 2, 3) == 12)
  assert( nTimesRec(double, 2, 3) == 12)
  println(nTimesOp(double,1)(2))
  //assert( nTimesOp(double,1)(2) == 4)
  println(nTimesOp(double, 2)(2))

  println(nTimesOp(double, 3)(2))
  //assert(nTimesOp(double, 2)(2) == 8)

  //assert(nTimesOp(double, 3)(2) == 16)

  // currying and curried functions. named after logician Haskell Curry
  // transform a function of N args into a sequence of N-single arg functions
  // f(a,b,c) -> g(a)(b)(c)

  val superAdder: Int => (Int => Int) = (x:Int) => (y: Int) => x +y
  // f(x)(y) = x + y

  val add3 = superAdder(3)
  assert(add3(9) == 12)

  // alternative declaration; multiple parameter lists
  def formatter(c: String)(x: Double): String = c.format(x)

  val standardFormatter: (Double => String) = formatter("%4.2f")
  val preciseFormatter: (Double => String) = formatter("%10.8f")

  println(standardFormatter(Math.PI))
  println(preciseFormatter(Math.PI))
}
