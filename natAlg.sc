import scalaz.Cofree
import scalaz.Functor
import scalaz.Equal
import scalaz.std.anyVal._   // for assert_=== to work on basic values
import scalaz.syntax.equal._ // for assert_===
import scalaz.syntax.functor._ // for map
sealed trait NatF[+A]
case class Zero[A]() extends NatF[A]
case class Succ[A](n: A) extends NatF[A]
implicit val NatFunctor: Functor[NatF] = new Functor[NatF] {
  def map[A, B](fa: NatF[A])(f: A => B): NatF[B] = fa match {
    case Zero() => Zero()
    case Succ(n) => Succ(f(n))
  }
}

// TODO fashion other algebraic examples after this one

type Nat = Cofree[NatF, Unit]

val zero: Nat = Cofree((), Zero())
val one:  Nat = Cofree((), Succ(Cofree((), Zero())))
val two:  Nat = Cofree((), Succ(Cofree((), Succ(Cofree((), Zero())))))

// TODO inject into Cofree
// generalized catamorphism for Cofree
def cata[S[+_], A, B](g: A => S[B] => B)(s: Cofree[S, A])(implicit S: Functor[S]): B =
  g(s.head)(s.tail map cata(g))

def toInt: Unit => NatF[Int] => Int = _ => {
  case Zero()  => 0
  case Succ(n) => n + 1
}

cata(toInt)(two)