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

  intList.foreach(x => println(x))

  var y =0
  intList.foreach(_ => {y = y+1})
  println("len", y)

  //2 13 3 5 1
  val unsorted = new ConsCovList[Int](1,EmptyCovList).add(5).add(3).add(13).add(2)

  println( "unsorted " + unsorted.toString)
  val x = unsorted.sort((x:Int, y:Int) => x < y)
  println("unsort -> sorted " + x.toString)

  val ziped = intList.zipWith(intList, (x:Int ,y:Int) => x +y )
  println("intlist ", intList.toString, " zipped ", ziped.toString)
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

  def foreach(f: A => Unit)

  def length(): Int
  def sort(f: (A,A) => Boolean): MyCovList[A]
  def reverse():MyCovList[A]

  def zipWith[B>:A] (myCovList: MyCovList[B], f:(B,A)=>B ):MyCovList[B]

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

  override def foreach(f: A => Unit): Unit = {
    // note to self, i'm very tempted to use helper functions. I found the solution without a helper by
    // 1. writing in imperative style
    // 2. translating to a recursive helper
    // 3. analyzing the helper to translate into a call of foreach. This step is
    // still creative for me -- i knew the i need to call it on tail.head and struggled
    // with the syntax to get that correct
    /*
    f(start)
    f(this.tail.head)
    f(this.tail.tail.head)

    this.tail.foreach(f)

    def helper(l: MyCovList[A]) = {
      f(l.head)
    }
    f(start)
    helper(this.tail)
    */
    f(start)
    if (!this.tail.isEmpty) this.tail.foreach(f)
  }

  override def length(): Int = {
    var y = 0
    this.foreach(_ => {
      y = y + 1
    })
    y
  }
  override def reverse():MyCovList[A] = {
    def helper(l: MyCovList[A], result: MyCovList[A]): MyCovList[A] = {
      if (l.isEmpty) result
      else helper(l.tail, result.add(l.head))
    }
    helper(this, EmptyCovList)
  }
  override def sort(f: (A, A) => Boolean): MyCovList[A] = {
    def merge(l1: MyCovList[A], l2: MyCovList[A], result: MyCovList[A]): MyCovList[A] = {
      println("merge l " + l1 + " merge r " + l2 )
      println("result " + result.toString)
      if (l2.isEmpty && l1.isEmpty) result.reverse()
      else if (l1.isEmpty) {
        merge(EmptyCovList, l2.tail, result.add(l2.head))
      }
      else if (l2.isEmpty) {
        merge(l1.tail, EmptyCovList, result.add(l1.head))
      }

        // add puts element in the front, so pick the larger one
      else if (f(l1.head, l2.head) == true) {
        val temp = result.add(l1.head)
        merge(l1.tail, l2, temp)
      } else {
        val temp = result.add(l2.head)
        merge(l1, l2.tail, temp)
      }
    }

    def partition(l: MyCovList[A]): (MyCovList[A], MyCovList[A]) = {
      def helper(temp: MyCovList[A], firstHalf: MyCovList[A], secondHalf: MyCovList[A]): (MyCovList[A], MyCovList[A]) = {
        println("first half " +  firstHalf.toString + ", "  +firstHalf.length() + ".   temp"+  temp.toString + ", ", temp.length() )
        if (firstHalf.length() == l.length() / 2 || temp.isEmpty) (firstHalf, temp)
        else {
          helper(temp.tail, firstHalf.add(temp.head), EmptyCovList)
        }
      }
        if (l.length() == 1) (l, EmptyCovList)
        else if (l.isEmpty) (EmptyCovList, EmptyCovList)
        else {
          helper(l, EmptyCovList, EmptyCovList)
        }
    }
    def mergesort(l:MyCovList[A]): MyCovList[A] = {


      val p1 = partition(l)
      if (p1._1.length() == 1 && p1._1.length() >= p1._2.length()) merge(p1._1, p1._2, EmptyCovList)
      else {
        val left = mergesort(p1._1)
        println("left " + left.toString)
        val right = mergesort(p1._2)
        println("right " + right.toString)
        merge(left, right, EmptyCovList)
      }
    }
    mergesort(this)
  }

  override def zipWith[B >: A](myCovList: MyCovList[B], f: (B, A) => B): MyCovList[B] = {
    if (myCovList.length() != this.length()) throw new IllegalArgumentException("length must match")
    else new ConsCovList[B](f(myCovList.head, this.start), this.tail.zipWith(myCovList.tail, f))

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

  override def foreach(f: Nothing => Unit): Unit = ???

  override def sort(f: (Nothing, Nothing) => Boolean): MyCovList[Nothing] = ???

  override def length(): Int = 0

  override def reverse(): MyCovList[Nothing] = ???

  override def zipWith[B >: Nothing](myCovList: MyCovList[B], f: (B, Nothing) => B): MyCovList[B] = EmptyCovList
}

