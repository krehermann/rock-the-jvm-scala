package lectures.part1Basics

object Functions extends App {

  def func(name: String, age: Int): String = {
    name + " " + age
  }

  println(func("joe",21))

  // when a loop is wanted, use recursive function.
  def repeat(say: String, n: Int): String = {
  if (n == 1) {
    say
  } else {
    say + repeat(say, n-1)
  }

  }
  println(repeat("i'm great", 3))

  // Exercises
  // 1. greeting function (name, age) => "hi, i'm <name> and i'm <age>"
  // 2. Factorial function 1*2*3..*n
  // 3. Fibonacci f(1) =1 f(2) = 1 f(n) = f(n-1) + f(n-2)
  // 4. test if number is prime

  def greet(name: String, age: Int): String = {
    "hi, i'm " + name + " and i'm " + age
  }
  assert(greet("bob",4) == "hi, i'm bob and i'm 4")

  def factorial(n: Int): Int = {
    if (n == 1) 1
    else n*factorial(n-1)

  }

 assert(factorial(4) == 4 * 3 * 2 *1)

  def fib(n: Int): Int = {
    if (n==0 || n ==1 ) 1
    else fib(n-1) + fib(n-2)

  }

  assert(fib(5) == 8)

  def prime(n:Int): Boolean = {
    def hasMod(k: Int, d:Int): Boolean = {
      if (d == 1) false
      else if (k % d == 0 ) true
      else {
        hasMod(k, d-1)
      }
    }
    if (n <=3 ) true
    else hasMod(n, n-1)

  }
  println("prime(3) " + prime(3) )
  println("prime(4) " + prime(4))

  println("prime(11) " + prime(11))
  println("prime(21) " + prime(21))

}
