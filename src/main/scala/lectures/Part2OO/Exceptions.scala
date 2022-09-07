package lectures.Part2OO

object Exceptions extends App {

  def oom: String = {
    throw new OutOfMemoryError("this is oom")
  }

  def so:String = {
    throw new StackOverflowError("stack overflow")
  }

  val oomer = try {
    oom
  } catch {
    case e: OutOfMemoryError => s"caught oom"
  }

  assert(oomer == "caught oom")

  val soer = try {
    so
  } catch {
    case e: StackOverflowError => s"caught SO error"
  }

  assert(soer == "caught SO error")

  class Overflow extends Exception
  class Underflow extends Exception
  class ImpossibleMath extends Exception
  class Calculator()  {
    def add(a: Int, b:Int):Int ={
        val overFlowGuard = (Int.MaxValue - a)
        if (b > overFlowGuard) throw new Overflow
        a+b
    }
    def subtract(a:Int, b:Int): Int = {
      val underflowGuard = Int.MinValue - a
      if (b > underflowGuard) throw new Underflow
      a - b
    }
    def divide(a:Int, b:Int):Int = {
      if (b == 0) throw new ImpossibleMath
      a / b
    }
    def multiply(a:Int, b:Int) = {
      val overflowGuard = Int.MaxValue / a
      if (b > overflowGuard) throw new Overflow
      a *b
    }
  }

  val calculator = new Calculator
  assert(calculator.add(2,3) == 5)
  assert(calculator.subtract(2,3) == -1)
  assert(calculator.divide(4,2)==2)
  assert(calculator.multiply(2,3)==6)
  val addOver:Int = try {
    calculator.add(Int.MaxValue-2,6)
  } catch {
    case e: Overflow => {
      println("caught overflow")
      Int.MaxValue
    }
  }
  assert(addOver == Int.MaxValue)

  val subUnder = try {
    calculator.subtract(Int.MinValue + 2, 6)
  } catch {
    case e: Underflow => {
      println("caught underflow")
      Int.MinValue
    }
  }
  assert(subUnder == Int.MinValue)
}

