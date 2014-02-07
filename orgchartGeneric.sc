// TODO genericity...why?

/**
 * data Node[A] = P(name: String) | OU(name: String, children: List[Node[A]])
 */
sealed trait Node[+A] {
  def map[B](f: A => B): Node[B]
}
case class P[A](data: A) extends Node[A] {
  def map[B](f: A => B) = P(f(this.data))
}
case class OU[A](data: A, children: List[Node[A]]) extends Node[A] {
  def map[B](f: A => B) = OU(f(this.data), this.children.map(_.map(f)))
}

val p = P("George")
assert { p.data == "George" }

val cs =   OU("CS",   List(P("Sekharan"), P("Rom"), P("Thiruvathukal")))
val math = OU("Math", List(P("Jensen"), P("Doty"), P("Giaquinto")))
val cas =  OU("CAS",  List(P("Andress"), P("Andrade"), cs, math ))

val luc =  OU("luc",  List(cas))



luc.map(_.length)
