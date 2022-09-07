package lectures.part1Basics

import scala.annotation.tailrec

object Recursion extends App {
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println("computing factorial of " + n + " need factorial of " + (n - 1))
      val r = n * factorial(n - 1)
      println("computed " + n)
      r
    }
  }

  println(factorial(5))

  // tail recursion
  // implemenentation above results in
  // f(n) = n*f(n-1) = n*(n-1)*f(n-2) = n*(n-1)*...(n-(n-1))*f(n -(n-1))
  // only when we reach step n-1 do we get a concrete value for f()
  // this means we have to store all the intermediate results
  // during our traversal to n-1
  // this bad because it costs a lot of resources
  // 1. holding the intermediate state requires a stack frame per call, risk stack overflow
  // 2. this in turn risk a memory blowout if the f() allocates a lot of memory

  // we seek a solution where we can store the intermediate result explicitly, accumulating
  // at each step in the recursion so we can use the accumulated value rather than
  // allocate a stack frame as out intermediate storage

  // keeping an accumulated value implies we need a variable for that state
  // f(n, runningVal)
  // the key insight is that we need to seed the runningVal on the first call
  // and each succesive call must change the runningVal as approriate for the algorithm
  // for factorial implementation this gives
  def tailRecursiveFactorial(n: Int) = {
    @tailrec
    def trHelper(n: Int, accumulation: Int): Int = {
      if (n == 1) accumulation
      else trHelper(n - 1, accumulation * n)
    }

    trHelper(n, 1)
  }

  println(tailRecursiveFactorial(5))

  // Exercises. Implement tail recursion for
  // 1. Concat a string N times
  // 2. prime finder
  // 3. fibonacci

  def concat(s: String, n: Int): String = {
    @tailrec
    def trHelper(s: String, accumulation: String, n: Int): String = {
      if (n == 1) accumulation
      else trHelper(s, accumulation + "," + s, n - 1)
    }

    trHelper(s, s, n)
  }


  println(concat("hi there", 5))

  def prime(n:Int): Boolean = {
    @tailrec
    def divisorExists(n:Int, divisor: Int, hasDivisor: Boolean): Boolean = {
      if (divisor == 1) false
      else if (hasDivisor) true
      else {
        // println("n, divisor, mod" + n + "," + divisor + "," + n%divisor)
        divisorExists(n, divisor-1, (n % divisor) == 0 )
      }
    }

    !divisorExists(n,n /2,false)
  }

  println("3 is prime?", prime(3))
  println("40 is prime?", prime(40))
  println("37 is prime?", prime(37))

  def fib(n:Int): Int ={
    @tailrec
    // need to keep state for two elements.
    def nthFibElement(n:Int, ultimate: Int, penultimate: Int): Int = {
      if (n<=2) return ultimate
      else nthFibElement(n-1,ultimate+ penultimate, ultimate)
    }
    nthFibElement(n,1,1)
  }

  println(fib(1) + "," + fib(2) + "," + fib(3) + "," + fib(4) + "," + fib(5) + "," + fib(6) + "," + fib(7))
}