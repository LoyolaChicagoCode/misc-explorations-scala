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

val org =
  OU(("The Outfit", 50),
    P(("CEO", 140)),
    P(("Assistant to CEO", 60)),
    OU(("Retail Dept", 70),
      P(("Dir of Retail", 120)),
      P(("Asst Dir of Retail", 90)),
      P(("Retail Clerk", 50))
    ),
    OU(("IT Dept", 130),
      P(("Dir of IT", 110)),
      P(("IT Specialist", 85))
    )
  )

assert { org.map(_._1.length).data == 10 }

for {
  s <- org                 // translates to flatMap
  t <- OU(s, P("Auditor")) // translates to map
} yield t

def incBy(perc: Float)(num: Int): Int = scala.math.round(num.toFloat * (100 + perc) / 100)

val orgAfterRaise = org map { case (name, salary) => (name, incBy(2.5f)(salary)) }

assert { orgAfterRaise.children(0).asInstanceOf[P[(String, Int)]].data._2 == 144 }

val orgSanitized = orgAfterRaise map { _._1 }

assert { orgSanitized.isInstanceOf[Node[String]] }

println("■")