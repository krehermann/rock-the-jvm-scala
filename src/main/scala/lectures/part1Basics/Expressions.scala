package lectures.part1Basics

object Expressions extends App {
  // expression are evaluated to a value
  val x = 1 +2
  println(x)

  //math
  // + - * / & | ^ << >>

  // comparison
  // == != > >= <=
  println(1==x)

  println(!(1 == x))

  var v = 2
  v += 3 // Side effect! only allowed for vars. -= *= /= ...
  println(v)

  // Instructions (DO) imperative langs, like python
  // Expressions (Value)

  // IF expression
  val aCond = true
  println(if(aCond) 9 else 3)

  // loop. strongly discouraged... it's an imperative pattern
  var i = 0
  while (i < 10){
    println(i)
    i += 1
  }

  // everything is expression in scala

  val weird = (i = 3) // this is an expression that returns Unit ~ void
  println("this is weird " + weird)
  // other example of side effects that return unit: println, while, reassign

  // Code blocks
  val codeBlock = {
    val y = 2
    val z = y +1
    if (z > 2) "hi" else "bye"
  }

  println(codeBlock)

  // Exercises
  // 1. What is the difference between "hello world" and println("hello world")
  //    A. first is string literal, type String.  println produces a side effect, type Unit
  // 2. What are values of
  val ex1 = {
    2 < 3
  }
  assert(ex1 == true)

  val ex2 = {
    if(ex1) 239 else 986
    77
  }
  assert(ex2 == 77)
}
