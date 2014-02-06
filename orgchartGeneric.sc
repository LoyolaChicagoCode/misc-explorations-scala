// TODO genericity...why?

/**
 * data Node[A] = P(name: String) | OU(name: String, children: List[Node[A]])
 */
sealed trait Node
case class P(name: String) extends Node
case class OU(name: String, children: List[Node]) extends Node

val p = P("George")
assert { p.name == "George" }

val cs =   OU("CS",   List(P("Sekharan"), P("Rom"), P("Thiruvathukal")))
val math = OU("Math", List(P("Jensen"), P("Doty"), P("Giaquinto")))
val cas =  OU("CAS",  List(P("Andress"), P("Andrade"), cs, math ))
val luc =  OU("luc",  List(cas))
