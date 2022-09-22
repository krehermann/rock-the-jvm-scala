package lectures.Part3Functional

object MapFlatMapFilter extends App{
  val l = List(1,2,3)
  println(l)

  println(l.head)
  println(l.tail)

  println(l.map(_ +1))
  println(l.map(_ *2))

  println(l.map(_ + " is a number"))

  println(l.filter(_ %2 ==0))

  val toPair = (x: Int) => List(x, x*x)
  println(l.flatMap(toPair))
  println(l.flatMap((x:Int) => List(x,x * x)))

  val numbers = List(1,2,3,4)
//  val chars = List('a', 'b','c','d')
  val chars = List("a","b","c","d")

  // make all combinations: this would be 2 loops in imperative lang
  // in functional lang use flatmap(map)
  // and this extended in general to flatmap(flatmap(...(map)..))
  def combinations(l1: List[Int], l2: List[String], accu: List[String]) : List[String] = {
    if (l2.length== 0 ) accu
    else {
      val k = accu.appendedAll(numbers.map((x: Int) => (x + l2.head)))
      combinations(l1, l2.tail, k)
    }
  }

  def altComb = numbers.flatMap((x: Int) => chars.map(_ + x))
  println("altComb ", altComb)

  def altComb2 = chars.flatMap((s:String) => numbers.map(s + _))
  println(("altComb2 ", altComb2))

  println(numbers.zipAll(chars, 0, "x"))

  println(combinations(numbers,chars, List()))

  // for comprehensions
  // the chain of flatMaps above can be difficult to read as it growing. an equivalent
  // and prefered style is for ... yield, i.e for comprehension
  val colors = List("purple", "orange")
  val forComp = for {
    c <- chars
    n <- numbers
    col <- colors
  } yield n + c + "-" + col

  println(forComp)

  // for comprehension accept operators in their defn

  val forCompEven = for {
    c <- chars
    n <- numbers if n % 2 == 0
    col <- colors
  } yield n + c + "-" + col
  println(forCompEven)
}

