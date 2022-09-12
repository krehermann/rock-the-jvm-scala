package exercises

import javax.swing.plaf.multi.MultiListUI
import scala.annotation.tailrec

abstract class MyList {

  /*
  head => first element of list
  tail => remainder of list
  isEmpty => is list empty
  add(int) => new list with this element
  toString => string representation of the list
   */

  def head(): Int
  def tail: Int
//  def tail(elems: Int) MyList
  def isEmpty: Boolean
  def append(n: Int): MyList
  def toString(): String
}

class SimpleListImpl(a: Array[Int]) extends MyList {
  override def head(): Int = a(0)

  override def tail: Int = a(a.length-1)

  override def isEmpty: Boolean = a.length == 0

  override def append(n: Int): MyList = {
    //val b = new Array[Int](n)

    new SimpleListImpl(a.appended(n))
  }

  override def toString: String = a.toString

}

// solution
// key insight is that tail is supposed to return a list

abstract class MyLinkedList {
  def head: Int
  def tail: MyLinkedList
  def isEmpty: Boolean
  def add(n:Int) : MyLinkedList
}

object Empty extends MyLinkedList {
  // no sensible return value. ??? means "nothing"
  override def head: Int = ???

  override def tail: MyLinkedList = ???

  override def isEmpty: Boolean = true

  override def add(n: Int): MyLinkedList = new Cons(n,this)

}

class Cons(val head: Int, val tail: MyLinkedList) extends MyLinkedList {
  override def isEmpty: Boolean = false // by construction there is a head

  override def add(n: Int): MyLinkedList = new Cons(n, this)
}

object ListTest extends App {
  val l = new Cons(1, Empty)
  assert(l.head == 1)
  assert(l.tail == Empty)

  val l2 = l.add(4)
  assert(l2.head == 4)
  assert(l2.tail == l)

  val covList = new ConsCovList[String]("hi", EmptyCovList)
  assert(covList.head == "hi")
  val covList2 = covList.add("my").add("name").add("is")
  assert(covList2.head == "is")

  println("a str " + covList2.toString)

  val intList = new ConsCovList[Int](4, EmptyCovList).add(3).add(2).add(1)
  println(intList.toString)
  /*
  val f = intList.filter(new MyPredicate[Int] {
    override def is(elem: Int): Boolean = {elem %2 == 0}
  })

  */
  val f = intList.filter(new Function1[Int, Boolean] {
    override def apply(v1: Int): Boolean = {
      v1 % 2 == 0
    }
  })
  println("filtered " + f.toString)

  /*
  val doubled = intList.map( new MyTransformer[Int,Int] {
        override def transform(input: Int): Int = {input * 2}
      })

   */
  val doubled = intList.map(new Function1[Int, Int] {
    override def apply(v1: Int): Int = 2 * v1
  })
  println("new doubled" + doubled.toString)

  /*
  val flattend = intList.flatMap(new MyTransformer[Int, MyCovList[Int]] {
    override def transform(input: Int): MyCovList[Int] = {
      new ConsCovList[Int](input+100, EmptyCovList).add(input)
    }
  })
  println(flattend.toString)
}

   */

  val flattend = intList.flatMap(new Function1[Int, MyCovList[Int]] {
    override def apply(v1: Int): MyCovList[Int] = {
      new ConsCovList[Int](v1 + 100, EmptyCovList).add(v1)
    }
  })
  println(flattend.toString)
}
// make a generic list
/*
abstract class MyCovariantLinkedList[+A] {
  def head : A
  def tail : MyCovariantLinkedList[A]
  def isEmpty: Boolean
  def add[B>:A](element B) : MyCovariantLinkedList[B]
}

class CovariantList[+A,B>:A](head:B, tail:CovariantList[A]) extends MyCovariantLinkedList[A] {
  override def head: B = head
  override def tail: CovariantList[A] = tail

  override def isEmpty: Boolean = false

  override def add[B>:A](elem:B): CovariantList[B] = new CovariantList[A](elem, this)


}
*/

abstract class MyCovList[+A] {
  def head: A
  def tail: MyCovList[A]
  def isEmpty: Boolean
  def add[B>:A](elem: B): MyCovList[B]
  def map[B](transformer: A => B): MyCovList[B]
  //def filter(myPredicate: MyPredicate[A]): MyCovList[A]
  def filter(filter: Function1[A,Boolean]):MyCovList[A]
  //def flatMap[B](myTransformer: MyTransformer[A, MyCovList[B]]): MyCovList[B]

  def flatMap[B](transformer: Function1[A,MyCovList[B]]): MyCovList[B]
  def ++[B >: A](l2: MyCovList[B]): MyCovList[B]

}

class ConsCovList[+A](start:A, end: MyCovList[A]) extends MyCovList[A] {
  override def head: A = start

  override def tail: MyCovList[A] = end

  override def isEmpty: Boolean = false

  override def add[B >: A](elem: B): MyCovList[B] = new ConsCovList(elem, this)

  override def map[B](transformer: A => B): MyCovList[B] = {
    // this implementation is correct, but very verbose.
    // i missed the key insight that map is fundamentally generative when operating
    // on a Cons because, mapping breaksdown to transforming the first element
    // and then mapping the remaining tail.

    new ConsCovList[B](transformer(this.start),end.map(transformer))
    /*
    @tailrec
    def helper(input:MyCovList[A], accumulation:MyCovList[B]): MyCovList[B] = {
      if (input.isEmpty) accumulation
      else helper(input.tail, new ConsCovList[B](transformer.transform(input.head), accumulation))
    }
    @tailrec
    def reverse(l: MyCovList[B], accumulation: MyCovList[B]):MyCovList[B] = {
      if (l.isEmpty) accumulation
      else reverse(l.tail, new ConsCovList[B](l.head, accumulation))
    }

    val x = helper(this, EmptyCovList)
    reverse(x, EmptyCovList)
    */

  }

 /*
    def reverseHelper(l: MyCovList[A], accumulation: MyCovList[A]): MyCovList[A] = {
      if (l.isEmpty) accumulation
      else reverseHelper(l.tail, new ConsCovList[A](l.head, accumulation))
    }
*/
  override def toString(): String = {
    def helper(l: MyCovList[A], accumulation:String):String = {
      if (l.isEmpty) s"${accumulation} ]"
      else helper(l.tail, s"${accumulation} ${l.head}")
    }
    helper(this, "[")
  }


  /*
  override def filter(myPredicate: MyPredicate[A]): MyCovList[A] = {
    // this implementation is correct, but i missed the insight about the nature
    // of Cons. filtering is applying the predictate on the head and then filtering the tail
    /*
    @tailrec
    def helper(l: MyCovList[A], accumulation: MyCovList[A]):MyCovList[A] = {
      if (l.isEmpty) accumulation
      else if (myPredicate.is(l.head)) helper(l.tail, new ConsCovList[A](l.head, accumulation))
      else helper(l.tail, accumulation)
    }
    @tailrec
    def reverse(l: MyCovList[A], accumulation: MyCovList[A]):MyCovList[A] = {
      if (l.isEmpty) accumulation
      else reverse(l.tail, new ConsCovList[A](l.head, accumulation))
    }

    val r = helper(this, EmptyCovList)
    reverse(r, EmptyCovList)
   */

    if (myPredicate.is(start)) new ConsCovList(start, end.filter(myPredicate))
    else end.filter(myPredicate)

  }


   */


  override def filter(f: A => Boolean): MyCovList[A] = {
    if (f(start)) new ConsCovList(start, end.filter(f))
    else end.filter(f)
  }


  /*
   [1,2] ++ [ 3, 4, 5]
   Cons(1, Cons([2]. [3,4,5])
   Cons(1, Cons(2), Cons(3,[4,5])
   */
  override def ++[B>:A]( l2:MyCovList[B]): MyCovList[B] = {
    new ConsCovList(start, end ++ l2)
  }

  override def flatMap[B](transformer: A => MyCovList[B]): MyCovList[B] = {
    transformer(this.start) ++ end.flatMap(transformer)
  }

/*  override def flatMap[B](myTransformer: MyTransformer[A, MyCovList[B]]): MyCovList[B] = {
  // once more i missed the boat. the implementation is correct, but we can simplify by taking advantage
    // of the structure of Cons. flatmap is a new Cons with the head transformed and concated to the
    // flatmap of the tail
    myTransformer.transform(start) ++ end.flatMap(myTransformer)

    /*
    @tailrec
    def helper(startList: MyCovList[A], temp: MyCovList[B], accumulation:MyCovList[B]): MyCovList[B] = {
      if (startList.isEmpty && temp.isEmpty) accumulation
        // we have an intermediate map to process
      else if (!temp.isEmpty) helper(startList, temp.tail, new ConsCovList[B](temp.head, accumulation))
      else {
        helper(startList.tail, myTransformer.transform(startList.head),accumulation)
      }
    }

    def reverse(l: MyCovList[B], accumulation: MyCovList[B]): MyCovList[B] = {
      if (l.isEmpty) accumulation
      else reverse(l.tail, new ConsCovList[B](l.head, accumulation))
    }

    reverse(helper(this, EmptyCovList, EmptyCovList), EmptyCovList)

     */
  }
 */

}



object EmptyCovList extends MyCovList[Nothing] {
  override def head: Nothing = ???

  override def tail: MyCovList[Nothing] = ???

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](elem: B): MyCovList[B] = new ConsCovList[B](elem, this)

  //override def filter(myPredicate: MyPredicate[Nothing]): MyCovList[Nothing] = EmptyCovList
  override def filter(filter: Nothing => Boolean): MyCovList[Nothing] = EmptyCovList
  override def map[B](transformer: Nothing => B): MyCovList[B] = EmptyCovList

  //override def flatMap[ B](myTransformer: MyTransformer[Nothing, MyCovList[B]]): MyCovList[B] = EmptyCovList
  override def flatMap[B](transformer: Nothing => MyCovList[B]): MyCovList[B] = EmptyCovList
  override def ++[B >: Nothing](l2: MyCovList[B]): MyCovList[B] = l2
}

// generic traits exercises
// 1. MyPredicate[T]: method to test if T meets a condition. test[T] : Boolean
// 2. MyTransformer[A,B]: transform[A]:B
// 3. Extend MyCovList to have methods
//    a. map(transformer[A,A]) : MyCovList[A]
//    b. filter(predicate): MyCovList[A]
//    c. flatMap(transfomer[A,B]): MyCovList[B]

trait MyPredicate[-A] {
  def is(elem: A): Boolean
}

trait MyTransformer[-A,B] {
  def transform(input:A): B
}

//va MyPredicateFunc[A]

/*
class Even[A ] extends MyPredicate[A] {
  override def is[A](elem: A): Boolean = {
    val mod:A =  Math.floorMod(elem,2)
    mod == 0
  }
}
*/

/*
class Doubler[Int,Int] extends MyTransformer[Int, Int] {
  override def transform[Int](a:Int): Int = {a*2}
}
*/
