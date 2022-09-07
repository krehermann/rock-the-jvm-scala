package lectures.Part2OO

object Objects extends App{

  // objects are singletons
  object Mammal {
    val N_EYES = 2
    def canFly: Boolean = false

    // factory methods usual go into the object
    // factory builds new instances
    def from(mother: Mammal, father: Mammal): Mammal=  new Mammal

    // we can use the apply keyword to make the api nice to use
    def apply(mother: Mammal, father:Mammal): Mammal = new Mammal
  }

  assert(Mammal.canFly == false)
  assert(Mammal.N_EYES == 2)

  // singleton by definition
  val m = Mammal
  val j = Mammal
  assert(m == j)

  // Companion
  // Any object, singleton can have a companion, class with same name and scope, that
  // holds instance specific information
  // this means that all access patterns can be implemented via either the object or class

  class Mammal {
    // instance level functionality
  }
  // now instances
  val mInst = new Mammal
  val jInst = new Mammal
  assert(mInst != jInst)

  // using the factory method of the object
  val child = Mammal(mInst, jInst)

  // Scala Applications, what we've been extended this whole time
  // App == object with method
  // def main(args: Array[String]): Unit
}
