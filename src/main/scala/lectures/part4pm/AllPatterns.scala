package lectures.part4pm

import exercises.{ConsCovList, EmptyCovList, MyCovList}

object AllPatterns extends App {

  // constants
  val x: Any = "const"
  val constants = x match {
    case 1 => "number"
    case "const" => "this is const"
    case true => "truth"
    case AllPatterns => "singleton"
  }

  // match anything
  val matchAnything = x match {
    case _ => "matches anything"
  }

  // variable
  val mVar = x match {
     case something => s"I've got $something"
  }

  // tuple
  val tupl = (1,2)
  val mTuple = tupl match {
    case(1,1) =>
    case (something,2) => s"got $something"
  }

  val nestedTuple = (1,(2,3))
  val matchNestedT = nestedTuple match {
    case (_, (2,v)) => s"anything then 2 and $v"
  }
  println(matchNestedT)

  // case classes, constructor pattern
  val myList: MyCovList[Int] = ConsCovList(1,ConsCovList(2, EmptyCovList))
  val matchMyList = myList match {
    case EmptyCovList =>
    case ConsCovList(head,tail) => // head is one, tail is ConsCovList(2, EmptyCovList)
  }

  // list patterns
  val l = List(1,2,3,17)
  val lMatch = l match {
    //extractor
    case List(1,_,_,_) => s"1 is first, four elems, last three anything"
    case List(1,_*) => s"arbitrary list with first elem == 1"
    case 1:: List(_) => "infix of arbitrary List starting with 1"
  }

  // type specific
  val anyType: Any = 2
  val anyMatch = anyType match {
    case list: List[Int] => "matchs a list of ints"
    case i: Int => "match input type Int"
    case _ => "any other type"
  }

  // name binding
  val nameBindingMatch = myList match {
    case nonEmpytList @ ConsCovList(_, _) => s"now i have a handle to `nonEmptyList` object ${nonEmpytList.head}"
  }

  // multi-pattern
  val multipattern = myList match {
    case EmptyCovList | ConsCovList(0,_) => "compound pattern"
  }

  // if guard
  val ifG = myList match {
    case ConsCovList(_,ConsCovList(specialElem, _)) if specialElem %2 == 0 => "special elem is even"
    case _ =>
  }
}
