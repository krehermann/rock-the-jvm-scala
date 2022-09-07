package lectures.Part2OO

// lightweight data structure require reimplementing boilerplate for example
// companion, eq, toString...
object CaseClass extends App {

  case class Person(name:String, age:Int)

  val jim = new Person("jim", 34)

  // class parameters are fields
  println(jim.name)

  // 2. free, sensible toString
  println(jim.toString)

  // 3. equals & hashCode
  assert(jim == jim)
  val otherJim = new Person("jim", 34)
  assert(jim == otherJim)

  // 4. copy method
  val jimCopy = jim.copy()
  assert(jim == jimCopy)
  val jimAged = jim.copy(age = 44)
    assert(jim != jimAged)

  // 5. case classes have a companion object
  val genericPerson = Person
  val otherGenericPerson = Person
  assert(genericPerson == otherGenericPerson)
  // also a factory method. apply method makes class callable like a function
  val mary = Person.apply("mary", 23)
  val mary2 = Person("mary", 23) // recall that apply does not need to explicitly called
  // the apply does the same as the constructor. in practice, we don't use `new` for case classes
  assert(mary == mary2)


  // 6. CC are serializable
  // 7. pattern matching

  // 8. such a thing as a case object, but with companion
  case object UnitedKingdom {
    def name: String = "UK"
  }

  // Case classes can be the implementation of abstract classes, which provides
  // a lot of free features to the implementation, esp the serialization and equals.
}
