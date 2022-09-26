package lectures.Part3Functional

object MapsTuples extends App {

  val phoneBook = Map[String,Int](("jim", 123),("JIM", 345),("bob",256))
  println(phoneBook)
  println(phoneBook.map(pair => (pair._1.toLowerCase, pair._2)))

  val alice = new Person("alice")
  val bob = new Person("bob")
  val kim = new Person("kim")
  val george = new Person("george")

  val ppl = List(alice, bob, kim, george)
  val net = new SocialNetwork(ppl)
  println(net.get("alice"))
  val nnet = net.friend(alice, bob)
  println(nnet.get("alice"))


}

  case class Person(val Name:String, Friends: List[Person]= List()) {
    def friend(p:Person): Person = {
      val newFriends = Friends.appended(p)
      new Person(Name, newFriends)
    }
    def unfriend(p:Person): Person = {
      val newFriends = Friends.filter(_.Name != p.Name)
      new Person(Name, newFriends)
    }
    def nFriends(): Int = {
      Friends.length
    }
  }

case class SocialNetwork(people: List[Person])  {
  val network: Map[String, List[Person]] = people.map(p => p.Name -> p.Friends).toMap

  def get(name: String): Person = {
    println(network)
    new Person(name, network.getOrElse(name, List()))
  }
  def add(p:Person): SocialNetwork = {
  new SocialNetwork(people.appended(p))
  }
  def remove(p:Person): SocialNetwork = {
    new SocialNetwork(people.filter(x => x.Name != p.Name))
  }
  def friend(p1:Person, p2: Person) = {
    val updatedNet = network.map(pair => {
      if (pair._1 == p1.Name) pair._1 -> pair._2.appended(p2)
      else if (pair._1 == p2.Name) pair._1 -> pair._2.appended(p1)
      else pair._1 -> pair._2
    })
    println(updatedNet)
    val x = updatedNet.toList.map(pair => new Person(pair._1,pair._2))
    println(x)
    new SocialNetwork(x)

    //(n:String, f:List[Person]) => new Person(n,f)))
  }
}