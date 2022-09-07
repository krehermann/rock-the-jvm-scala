package lectures.Part2OO

object Generics extends App{

  class MyList[A] {}

  val listOfInts = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // variance: inheritance relationship of parameterized types
  class Animal
  class Dog extends Animal
  class Cat extends Animal

  // generics & inheritance
  // 'normal', invariant inheritance
  class InvariantList[A]
  val invariantList: InvariantList[Animal] = new InvariantList[Animal]
  // cannot assign subtype to invariant. this is the only kind of variance
  // in most other languages, like java and c++
  // val brokenInvariance: InvariantList[Animal] = new InvariantList[Cat]

  // covariance. models the typical behavior of subtyping: "if X[A] and B is subtype of A
  // then X[B] can be assigned to X[A]"
  class CovariantList[+A]
  val covariantList: CovariantList[Animal] = new CovariantList[Dog]

  // contravariance. models the inverse of co-variance. "if X[B] and B is subtype of A then
  // X[A] can be assigned to X[b]". Most often used for consumers, like a serializer:
  // if i want to serialize a cat, then i'll happily accept an animal serializer
  class ContraConsumer[-A]
  val contraConsumer: ContraConsumer[Cat] = new ContraConsumer[Animal]

  // Note the existence of covariance and contravariance are due to scala's bend
  // toward immutable types.

  // bounded types
  class Cage[A <: Animal](animal: A)
  val dogCage = new Cage[Dog](new Dog)
  val animalCage = new Cage[Animal](new Animal)

  // covariance introduces a difficult problem that bounds solve.
  // given
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // what happens is we want to add another animal type, like Dog?
  // ???? animalList.add(Dog)
  //...
  // intuitive answer is that this must be legal, since animalList is declared
  // to be compatible with any set of Animals. And that implies that
  // add must return new list whose contained type is a superset of the original
  // this in turn is solved by bounding the args of `add` such that it
  // accepts supertypes of contained type

  class MyCovariantList[+A] {
    def add[B>:A](elem:B): MyCovariantList[B] = ???
  }

  // exercise make a generic list

}
