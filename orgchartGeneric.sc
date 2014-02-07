/**
 * data Node[A] = P(name: String) | OU(name: String, children: List[Node[A]])
 */
sealed trait Node[+A] {
  def map[B](f: A => B): Node[B]
  def flatMap[B](f: A => Node[B]): Node[B]
}
case class P[A](data: A) extends Node[A] {
  def map[B](f: A => B) = P(f(data))
  def flatMap[B](f: A => Node[B]) = f(this.data)
}
case class OU[A](data: A, children: Node[A]*) extends Node[A] {
  def map[B](f: A => B) = OU(f(data), children.map(_.map(f)): _*)
  def flatMap[B](f: A => Node[B]) = f(this.data) match {
    case n @ P(d) => n
    case OU(d, cs @ _*) => OU(d, cs ++ children.map(_.flatMap(f)): _*)
  }
}

val p = P("George")
assert { p.data == "George" }

val cs =   OU("CS",   P("Sekharan"), P("Rom"), P("Thiruvathukal"))
val math = OU("Math", P("Jensen"), P("Doty"), P("Giaquinto"))
val cas =  OU("CAS",  P("Andress"), P("Andrade"), cs, math )
val luc =  OU("luc",  cas)

luc.map(_.length)

for {
  s <- cs                 // translates to flatMap
  t <- OU(s, P("Laufer")) // translates to map
} yield (t)

// TODO example to illustrate the benefits of genericity