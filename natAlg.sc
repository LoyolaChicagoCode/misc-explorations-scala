import scalaz.Cofree
import scalaz.Functor
import scalaz.Equal
import scalaz.std.anyVal._   // for assert_=== to work on basic values
import scalaz.syntax.equal._ // for assert_===
import scalaz.syntax.functor._ // for map

/**
 * Endofunctor in category Scala types for F-algebra:
 *
 * data NatF[+A] = Zero | Succ(n: A)
 *
 * Note that this is isomorphic to Option, but we are
 * defining it here from scratch for pedagogical reasons
 * as a template for other user-defined F-algebras.
 */
sealed trait NatF[+A]
case class Zero[A]() extends NatF[A]
case class Succ[A](n: A) extends NatF[A]

/**
 * Implicit for setting up NatF as a Functor in scalaz.
 */
implicit val NatFunctor: Functor[NatF] = new Functor[NatF] {
  def map[A, B](fa: NatF[A])(f: A => B): NatF[B] = fa match {
    case Zero() => Zero()
    case Succ(n) => Succ(f(n))
  }
}

/**
 * Algebra for carrier object Int in category Scala types.
 * Note that this is nonrecursive. It
 */
def toInt: Unit => NatF[Int] => Int = _ => {
  case Zero    => 0
  case Succ(n) => n + 1
}

/**
 * Fixed point of NatF (recursive type based on NatF)
 * as carrier object for initial algebra.
 */
type Nat = Cofree[NatF, Unit]
val zero:   Nat = Cofree((), Zero())
val one:    Nat = Cofree((), Succ(zero))
val two:    Nat = Cofree((), Succ(one))
val three:  Nat = Cofree((), Succ(two))

/**
 * Catamorphism (generalizeld fold) for Cofree injected into Cofree class
 * (similar to C# extension methods).
 *
 * Note that this is the only place with explicit recursion in this example.
 */
implicit class CofreeCata[S[+_], +A](self: Cofree[S, A]) {
  def cata[B](g: A => S[B] => B)(implicit S: Functor[S]): B =
    g(self.head)(self.tail map { _ cata g })
  // TODO paramorphism
}

/**
 * Now we can fold the toInt algebra into instances.
 */
zero.cata(toInt)  assert_=== 0
three.cata(toInt) assert_=== 3

/**
 * Coalgebra for carrier object Int in category Scala types.
 */
def fromInt(n: Int) = {
  require { n >= 0 }
  if (n == 0)
    Zero()
  else
    Succ(n - 1)
}

/**
 * Now we can create instances by unfolding the coalgebra from a starting value.
 */
Cofree.unfoldC(0)(fromInt)
Cofree.unfoldC(3)(fromInt)

println("yahoo")

// TODO fashion other algebraic examples after this one

