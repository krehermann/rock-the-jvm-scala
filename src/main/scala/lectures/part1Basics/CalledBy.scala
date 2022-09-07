package lectures.part1Basics

object CalledBy extends App {
  def calledByValue(x: Long): Unit = {
    println("by value " + x)
    println("by value " + x)

  }

  def calledByName(x: => Long): Unit = {
    println("by name " + x)
    println("by name " + x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

  calledByName(12L)
}
