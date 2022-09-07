package lectures.part1Basics

object StringOps extends App {

  val s: String = "Learning scala..."

  println(s.charAt(2))
  println(s.substring(1,3))
  println(s.split(" ").toList)

  val notNum = "45"
  val n = notNum.toInt
  println(n)

  // s-intepolators
  val name = "joe"
  val age = 46
  val greeting = s"Hello my name is $name and i'm $age"
  val embeddedExpr = s"hello i'm $name and i was born in ${2022 -age}"

  println(greeting)
  println(embeddedExpr)
}
