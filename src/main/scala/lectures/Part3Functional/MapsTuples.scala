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

  val unfriend = nnet.unfriend(alice, bob)
  println("unfriended " + unfriend)

  val conn = nnet.friend(bob,kim)
  println("alice -> kim" , conn.connected(alice,kim))
  println("alice -> bob" , conn.connected(alice,bob))
  println("alice -> george" , conn.connected(alice,george))


}

  case class Person(val Name:String, Friends: List[Person]= List()) {
    def friend(p:Person): Person = {
      if (Friends.length == 0) Person(Name, List(p))
      else Person(Name, Friends.appended(p))
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

  def unfriend(p1: Person, p2: Person): SocialNetwork = {
    val updatedNet = network.map( pair => {
      if (pair._1== p1.Name) pair._1 -> pair._2.filter(friend => friend.Name != p2.Name)
      else  if (pair._1== p2.Name) pair._1 -> pair._2.filter(friend => friend.Name != p1.Name)
      else pair._1 -> pair._2
    })

    SocialNetwork(updatedNet.toList.map(pair => new Person(pair._1,pair._2)))
  }

  def maxFriends(): Person = {
    people.foldLeft(people.head)((x:Person, p: Person) => {
      if (x.Friends.length > p.Friends.length) x
      else p
    })
  }
  def noFriends(): Int = {
    people.count(p => p.Friends.length == 0)
  }

  def connected(p1:Person, p2:Person): Boolean = {

    def bfs(target: Person, toSearch: Set[Person], found: Set[Person]): Boolean = {
     // if (found.isEmpty) false
      println("bfs: target, search, found", target, toSearch, found)
      if (found.contains(target)) true
      else if (toSearch.isEmpty) false
      else {
        val newFound = found  ++ toSearch.toSet
        //val newSearch = toSearch.flatMap(p => p.Friends)
        val pToSearch = toSearch.filter(p => network.getOrElse(p.Name,List()).length > 0) ///this.people.filter(p => toSearch.contains(p)
        println("people to search", pToSearch)
        val newFriends = pToSearch.flatMap(p => p.Friends)
        println("new friends", newFriends)
        bfs(target, newFriends.toSet, newFound)
      }
    }
    /*
    val seed = people.filter(p => p.Name == p1.Name)
    if (seed.length == 0) false
    else {
      val isConnect: Seq[Boolean] = for {
        friend <- seed.head.Friends
      } yield {
        friend.Name == p2.Name
      }

      if (isConnect.filter(_ == true).length > 0) true

    }
    */
     val seed = people.filter(p => p.Name == p1.Name)
    println("seed", seed, "p1", p1)
     bfs(p2, seed.head.Friends.toSet, Set())
  }

 /*   def connected2(p1:Person, p2:Person): Boolean = {
      if (connected(p1,p2)) true
      else
      {
        val conn: Seq[Boolean] = for {

          friend <- p1.Friends
        } yield {
          connected(friend, p2)
        }
        conn.filter(_ == true).length > 0
      }
    }

  */
  }
