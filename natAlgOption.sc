import scalaz.Cofree
import scalaz.Functor
import scalaz.Equal
import scalaz.std.anyVal._   // for assert_=== to work on basic values
import scalaz.std.option._   // for Option as Functor instance
import scalaz.syntax.equal._ // for assert_===
import scalaz.syntax.functor._ // for map

/*
 * Using Option as endofunctor in category Scala types for F-algebra:
 */

type Algebra[S[_], A, B] = A => S[B] => B




/**
 * Algebra for carrier object Int in category Scala types.
 * Note that this is nonrecursive. It
 */
def toInt: Algebra[Option, Unit, Int] = _ => {
  case None    => 0
  case Some(n) => n + 1
}

/**
 * Fixed point of NatF (recursive type based on NatF)
 * as carrier object for initial algebra.
 */
type Nat = Cofree[Option, Unit]
val zero:   Nat = Cofree((), None)
val one:    Nat = Cofree((), Some(zero))
val two:    Nat = Cofree((), Some(one))
val three:  Nat = Cofree((), Some(two))

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

type Coalgebra[S[_], A] = A => S[A]


/**
 * Coalgebra for carrier object Int in category Scala types.
 */
def fromInt: Coalgebra[Option, Int] = (n: Int) => {
  require { n >= 0 }
  if (n == 0)
    None
  else
    Some(n - 1)
}

/**
 * Now we can create instances by unfolding the coalgebra from a starting value.
 */
Cofree.unfoldC(0)(fromInt).cata(toInt) assert_=== 0




Cofree.unfoldC(3)(fromInt).cata(toInt) assert_=== 3




Cofree.unfoldC(1)(fromInt)

println("yahoo")

