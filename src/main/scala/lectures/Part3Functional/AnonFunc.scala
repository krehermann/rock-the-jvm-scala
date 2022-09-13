package lectures.Part3Functional

object AnonFunc extends App {

  // anonymous function (LAMBDA)
  val doubler: Int => Int = x => x * 2

  // multiple params in a a lambda
  val adder: (Int, Int) => Int = (a,b) => a+b
  assert(adder(5,6)==11)

  // curly braces with lambdas
  val stringToInt = { (str:String) =>
    str.toInt
  }

  // underscore syntactic sugar

  val underscoreInc: Int => Int = _ +1
  val underscoreAdder: (Int, Int) => Int = _ + _

  assert(underscoreAdder(11,19)==30)
}
