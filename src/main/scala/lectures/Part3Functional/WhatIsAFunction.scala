package lectures.Part3Functional



object WhatIsAFunction extends App{

  // Desire: use functions as first class elements

  val doubler = new MyFunc[Int,Int] {
    override def apply(element: Int): Int = element *2
  }

  assert(doubler(2)==4)

  // function types = FunctionN[A,...]

  val stoa = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }

  //syntatic sugar, easier to read as adder: ((Int,Int) => Int)

  val multiplier: ((Int,Int) => Int) =  new Function2[Int,Int,Int] {
    override def apply(v1: Int, v2: Int): Int = v1*v2
  }

  // ALL SCALA functions are objects

  assert(adder(1,2) == 3)
  assert(multiplier(4,5)==20)

  // Exercise function to concatentate strings
  val concat: ((String,String)=>String) = (v1: String, v2: String) => v1 + v2

  // Exercise: higher order function
  val nestedFuncMultiplier: (Int=> Function1[Int, Int]) = new Function1[Int, Function1[Int,Int]]  {
    override def apply(v1: Int): Int => Int = {
      new Function1[Int,Int] {
        override def apply(v2: Int): Int = v1*v2
      }
    }
  }

  val specialMultiplier: (Int => (Int => Int)) = (v1: Int) => (v2: Int) => v1 * v2


  val multBy10 =specialMultiplier(10)
  assert(multBy10(4) == 40)

  assert(nestedFuncMultiplier(3).apply(4)== 12)
  val multBy5 = nestedFuncMultiplier(5)
  assert(multBy5(7) == 35)
  assert(concat("a","b")=="ab")
}


trait MyFunc[A,B] {
  def apply(element:A) :B
}