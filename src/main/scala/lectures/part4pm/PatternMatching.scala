package lectures.part4pm

import scala.util.Random

object PatternMatching extends App{

  val rand = new Random
  val x = rand.nextInt(10)

  val description = x match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case _ => "unknown" // _ = wildcard
  }

  println(x)
  println(description)

  // pattern matching can decompose values of case class
  case class Person(name:String, age: Int)
  val bob = Person("bob",20)

  // cases are matched in order
  val greeting = bob match {
    case Person(n, a) if a < 21 => s"hi, i'm $n and i'm too young to drink a beer"

    case Person(n,a) => s"hi, i'm $n and i'm $a years old"
    case _ => "who am i?"
  }
  println(greeting)

  // pattern match on sealed hierarchies
  sealed class Animal
  case class Dog(breed:String) extends Animal
  case class Parrot(greeting:String) extends Animal
  val animal: Animal = Dog("pit bull")

  animal match {
    case Dog(breed) => println(s"Matched dog $breed")
  }

  /*
  Exercise: function that uses pattern matching take Expr => human readable string

  Sum(Number(2), Number(3),...) => 2+3+...
  Prod(Sum(Number(2), Number(1)), Number(3)) = (2 +1) *3

   */
  trait Expr
  case class Number(n:Int) extends Expr
  case class Sum(e1:Expr, e2:Expr) extends Expr
  case class Prod(e1:Expr, e2:Expr) extends Expr

  def prettyPrint(e: Expr):String = {
    def helper(exp: Expr, result:String):String = {
    val x = exp match {
      case Number(n) => result + s"$n"
      case Sum(e1, e2) => "(" + helper(e1, result) + s"+" + helper(e2,result) + ")"
      case Prod(e1, e2)  =>

        helper(e1, result) + s"*" + helper(e2,result)
    }
      x
    }
    println(helper(e,""))
    helper(e,"")
    }

  def solution(e: Expr):String = e match {
    case Number(n) => s"$n"
    case Sum(e1,e2) => solution(e1) + "+" + solution(e2)
    case Prod(e1, e2) => {
      def wrap(exp: Expr) = exp match {
        case Sum(e1, e2) => "(" + solution(exp) + ")"
        case Prod(e1, e2) => solution(exp)
        case Number(n) => solution(exp)
      }
      wrap(e1) + "*" + wrap(e2)
    }
  }

  prettyPrint(Sum(Number(2),Number(3)))
  prettyPrint( Sum(Sum(Number(2),Number(3)), Sum(Number(7),Number(11))))
  prettyPrint( Sum(Prod(Number(2),Number(3)), Sum(Number(7),Number(11))))
  prettyPrint( Prod(Prod(Number(2),Number(3)), Sum(Number(7),Number(11))))


  println(solution(Sum(Number(2), Number(3))))
  println(solution(Sum(Sum(Number(2), Number(3)), Sum(Number(7), Number(11)))))
    println(solution(Sum(Prod(Number(2), Number(3)), Sum(Number(7), Number(11)))))
    println(solution(Prod(Prod(Number(2), Number(3)), Sum(Number(7), Number(11)))))
  }