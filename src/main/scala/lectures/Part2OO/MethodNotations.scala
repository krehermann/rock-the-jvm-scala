package lectures.Part2OO

object MethodNotations extends App {
  class Person(val name:String, val favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie

    // very permission naming scheme
    def +(person: Person): String = s"${this.name} & ${person.name} sittin in a tree"

    def +(nickname:String): String = s"${this.name} (${nickname})"

    def +(yearsToAdd:Int): Person = {
      new Person(this.name, this.favoriteMovie, this.age + yearsToAdd)
    }

    def unary_+(): Person = {
      this + 1
    }
    def learns(subject: String): String = s"${this.name} learns ${subject}"

    def apply(times: Int) = s"${this.name} watches ${this.favoriteMovie} ${times} times"
    def unary_!() = s"unary ! not implemented"

    def apply() = s"apply is a special method than makes a class able to be called like a function"
  }

  val mary = new Person("mary", "inception")
  println(mary.likes("inception"))
  // infix notation/ operation notation for funcs with single parameter
  println(mary likes "inception")
  val tom = new Person("tom", "groundhog day")
  println(mary + tom)
  // + is a method
  println(mary.+(tom))

  //prefix notation of unary operator
  val x = -1
  val y = 1.unary_-
  assert(x==y)

  assert(mary.unary_!() == !mary)

  assert(mary.apply() == mary())

  // Exercises
  // 1. Overload the + operator: person + "nickname" => "name (nickname)"
  assert(mary.+("the little lamb") == "mary (the little lamb)")
  assert(mary + "the little lamb" == "mary (the little lamb)")

  // 2. Add age to Person class. Add unary + op such that (+person).age == person.age + 1
  val oldMary = mary + 50
  assert(oldMary.age == mary.age + 50)
  assert((+mary).age == mary.age + 1 )
  // 3. Add 'learns' method => person.learns("x) == "name learns x"
  assert(mary.learns("calculus") == "mary learns calculus")
  assert((mary learns "how to ride a bike") == "mary learns how to ride a bike")
  // 4. Overload apply method => person.apply(2) == "name watched person.movie 2 times"
  assert(mary.apply(4) == s"${mary.name} watches ${mary.favoriteMovie} 4 times")
  assert(mary(7) == s"${mary.name} watches ${mary.favoriteMovie} 7 times")
}
