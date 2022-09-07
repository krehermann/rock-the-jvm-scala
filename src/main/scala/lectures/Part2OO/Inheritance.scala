package lectures.Part2OO

object Inheritance {
  // seal allows extension only in this file
  sealed class Animal {
    def numberLegs = 4
    def eats = s"nomnom"
    final def cantTouchThis = "hammer time"
  }
  // single class inheritance
  class Cat extends Animal


  // constructors
  class Person(name:String, age:Int) {
    def this(name: String) = this(name,0)
  }
  class Adult(name:String, age:Int, isMarried: Boolean) extends Person(name, age)

  class Dog(override  val numberLegs:Int = 4) extends Animal {
    override def eats = s"dog eat dog"
  }

  def main(args: Array[String]): Unit = {
    val cat = new Cat
    assert(cat.eats == "nomnom")
    val dog = new Dog
    assert(dog.eats == "dog eat dog")
    val threeLeggedDog = new Dog(3)
    assert(threeLeggedDog.numberLegs == 3)
  }
}
