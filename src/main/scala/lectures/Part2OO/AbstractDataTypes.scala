package lectures.Part2OO

object AbstractDataTypes extends App{

  abstract class Person {
    val name: String
    val age: Int
  }

  trait Climber {
    def discipline: String
  }

  trait Runner {
    def kmPerWeek: Int
  }

  class Athlete(val name: String, val age: Int, val sport: String) extends Person {

  }

  class MountainAthlete(athlete: Athlete)
    extends Athlete(athlete.name, athlete.age, athlete.sport) with Climber with Runner {
    override def discipline: String = "alpine"

    override def kmPerWeek: Int = 40
  }

  val kf = new MountainAthlete(new Athlete("k", 31, "all around"))

  // the code above makes me unhappy. i don't yet understand how to express myself
  // here is the example from the lecture

  abstract class Animal {
    val creatureType:String
    def eat: String
  }

  class Dog(food:String) extends Animal {
    override val creatureType: String = "K9"
    override def eat: String = s"${food}"
  }

  trait Carnivore {
    def eat(animal: Animal): String
    // traits can have concrete methods
    def preferedMeal : String = "fresh meat"
  }

  class Crocidle extends Animal with Carnivore {
    override val creatureType: String = "croc"

    override def eat: String = "everything"

    override def eat(animal: Animal): String = s"i'm a croc eating a ${animal.creatureType} that eats ${animal.eat}"
  }

  val croc = new Crocidle
  println(croc.eat(new Dog("poo")))

  // General conceptual difference between abstract class and trait:
  // abstract class => thing, trait => behavior or characteristic
}
