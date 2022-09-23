package exercises

abstract class Maybe[+T] {
  def map[V](f: T=> V): Maybe[V]
  def flatMap[V](f: T => Maybe[V]): Maybe[V]
  def filter(predicate: T => Boolean): Maybe[T]
}

case object Void extends Maybe[Nothing] {
  override def map[V](f: Nothing => V): Maybe[V] = Void

  override def filter(predicate: Nothing => Boolean): Maybe[Nothing] = Void

  override def flatMap[V](f: Nothing => Maybe[V]): Maybe[V] = Void
}

case class Something[+T](elem: T) extends Maybe[T] {
  override def map[V](f: T => V): Maybe[V] = {
    Something(f(elem))
  }

  override def filter(predicate: T => Boolean): Maybe[T] = {
    if (predicate(elem) == false) Void
    else this
  }

  override def flatMap[V](f: T => Maybe[V]): Maybe[V] = {
    f(elem)
  }

}

object MaybeTest extends App {

  val something3 = Something(3)
  println(something3.map(_*2))
  println(something3.flatMap(x => Something(x + " is the loneliest number")))
  println(something3.filter(_ % 2 == 0))
  println(something3.filter(_ != 3))
  println(something3.filter(_ % 3 == 0))

}
