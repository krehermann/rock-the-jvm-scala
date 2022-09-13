package exercises

import javax.swing.plaf.multi.MultiListUI
import scala.annotation.tailrec

object ListTest extends App {


  val covList = new ConsCovList[String]("hi", EmptyCovList)
  assert(covList.head == "hi")
  val covList2 = covList.add("my").add("name").add("is")
  assert(covList2.head == "is")

  println("a str " + covList2.toString)

  val intList = new ConsCovList[Int](4, EmptyCovList).add(3).add(2).add(1)
  println(intList.toString)

  val f = intList.filter( v => v % 2 == 0 )
  println("filtered " + f.toString)

  val doubled = intList.map(v1 => 2 * v1)
  println("new doubled" + doubled.toString)

  val flattend = intList.flatMap(v1 =>
      new ConsCovList[Int](v1 + 100, EmptyCovList).add(v1)
  )
  println(flattend.toString)
}

abstract class MyCovList[+A] {
  def head: A
  def tail: MyCovList[A]
  def isEmpty: Boolean
  def add[B>:A](elem: B): MyCovList[B]
  def map[B](transformer: A => B): MyCovList[B]
  //def filter(myPredicate: MyPredicate[A]): MyCovList[A]
//  def filter(filter: Function1[A,Boolean]):MyCovList[A]

  def filter(filter: A=> Boolean): MyCovList[A]

  def flatMap[B](transformer: A => MyCovList[B]): MyCovList[B]
  def ++[B >: A](l2: MyCovList[B]): MyCovList[B]

}

class ConsCovList[+A](start:A, end: MyCovList[A]) extends MyCovList[A] {
  override def head: A = start
  override def tail: MyCovList[A] = end
  override def isEmpty: Boolean = false
  override def add[B >: A](elem: B): MyCovList[B] = new ConsCovList(elem, this)
  override def map[B](transformer: A => B): MyCovList[B] = {
    new ConsCovList[B](transformer(this.start),end.map(transformer))
  }

  override def toString(): String = {
    def helper(l: MyCovList[A], accumulation:String):String = {
      if (l.isEmpty) s"${accumulation} ]"
      else helper(l.tail, s"${accumulation} ${l.head}")
    }
    helper(this, "[")
  }
  override def filter(f: A => Boolean): MyCovList[A] = {
    if (f(start)) new ConsCovList(start, end.filter(f))
    else end.filter(f)
  }
  override def ++[B>:A]( l2:MyCovList[B]): MyCovList[B] = {
    new ConsCovList(start, end ++ l2)
  }
  override def flatMap[B](transformer: A => MyCovList[B]): MyCovList[B] = {
    transformer(this.start) ++ end.flatMap(transformer)
  }
}



object EmptyCovList extends MyCovList[Nothing] {
  override def head: Nothing = ???
  override def tail: MyCovList[Nothing] = ???
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](elem: B): MyCovList[B] = new ConsCovList[B](elem, this)
  //override def filter(myPredicate: MyPredicate[Nothing]): MyCovList[Nothing] = EmptyCovList
  override def filter(filter: Nothing => Boolean): MyCovList[Nothing] = EmptyCovList
  override def map[B](transformer: Nothing => B): MyCovList[B] = EmptyCovList
  override def flatMap[B](transformer: Nothing => MyCovList[B]): MyCovList[B] = EmptyCovList
  override def ++[B >: Nothing](l2: MyCovList[B]): MyCovList[B] = l2
}

