package lectures.Part3Functional

object HOFandCurries extends App{

 // val superFunc: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = ???
  // superFunc is function of two parameters that returns another function
  // param1 is an Int
  // param2 is a function of String and f(int) -> bool that returns an Int
  // return type is a function of Int => Int (f(int) -> int)

  // nTimes: function that applies itself to an argument N times
  def nTimes(f:(Int => Int), n: Int, x: Int): Int = {
    def helper(countdown:Int, accumulation:Int):Int = {
      if (countdown == 0) accumulation
      else helper(countdown-1, f(accumulation))
    }
    helper(n-1, f(x))
  }

  val double : Int => Int =  _ * 2

  assert( nTimes(double, 1, 2) == 4)
  assert( nTimes(double, 2, 2) == 8)
  assert( nTimes(double, 2, 3) == 12)
}
