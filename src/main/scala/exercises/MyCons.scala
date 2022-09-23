package exercises

object MyCons extends App {
  val l = new Cons(1, Empty)
  assert(l.head == 1)
  assert(l.tail == Void)

  val l2 = l.add(4)
  assert(l2.head == 4)
  assert(l2.tail == l)
}


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


// object to denote a list comprised of nothing
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



