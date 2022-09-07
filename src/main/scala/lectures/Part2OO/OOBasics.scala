package lectures.Part2OO

import java.io.Writer
import java.time.Year

object OOBasics extends App {
  val p = new Person("dorthy",37)
  println(p.age)
  println(p.f)

  p.sayHi("jim")
  p.sayHi()

  val w = new Writer("mark", "twain", 1903)
  assert(w.fullName == "mark twain")

  val n = new Novel("tom sawyer", w, 1928)
  assert(n.authorAge()== 119)

  val v2 = n.makeNewRelease(2000)
  assert(v2.releaseYear == 2000)
  assert(v2.author() == w)

  val c = new MutableCounter(7)
  assert(c.Inc().currentVal == 8)
  assert(c.Inc(7).currentVal == 15)
}

// parameterize are not field. need val
// person.age OK, person.name not valid
class Person(name: String, val age:Int) {
  // this is field
  val f = "woman"

  // method
  def sayHi(name:String = this.name) = println(s"Hi, $name")

  // overloading. kinda a terrible example given default above
  def sayHi() = println(s"Hi, $this.name")
}

/*
Exercises: implement Novel & Writer
Writer: first, last name, birth year. method: fullname
Novel: name, release year, author. methods: authorAge, writer, makeNewRelease(releaseYear)
Counter: recieve Int, method for current count, inc/dec, overload inc/dec
 */

class Writer(first:String, last:String, val birthYear: Int) {
  def fullName(): String = this.first + " " + this.last
  //val birthYear = birthYear
}

class Novel(name: String, author: Writer, val releaseYear: Int) {
  def authorAge(): Int = { Year.now.getValue - author.birthYear}
  def author(): Writer = {this.author}
  def makeNewRelease(releaseYear:Int): Novel = {
    new Novel(this.name, this.author(), releaseYear)
  }
}

// Interestingly my instinct was to mutate the instance. This is the wrong
// instinct for functional programming. Instead, always implement immutable
// instances and when mutation is desired, return a new instance.
class MutableCounter(seed: Int) {
  var currentVal = seed
  def Inc(): MutableCounter = {
    this.currentVal+=1
    this
  }

  def Inc(stepSize: Int): MutableCounter = {
    this.currentVal += stepSize
    this
  }
}

class ImmutableCounter(val count: Int) {
  def Inc():ImmutableCounter = new ImmutableCounter(this.count + 1)
  def Inc(stepSize: Int):ImmutableCounter = new ImmutableCounter(this.count + stepSize)

}