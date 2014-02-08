import scalaz.Cofree
import scalaz.Functor
import scalaz.Equal
import scalaz.std.anyVal._     // for assert_=== to work on basic values
import scalaz.std.option._     // for Option as Functor instance
import scalaz.syntax.equal._   // for assert_===
import scalaz.syntax.functor._ // for map

/**
 * An algebra that is part of a F-algebra.
 * @tparam A generic item type of the initial algebra
 * @tparam S branching constructor of the initial algebra
 * @tparam B carrier object of the algebra
 */
type Algebra[A, S[_], B] = A => S[B] => B

/*
 * Using Option as endofunctor in category Scala types for MyList F-algebra.
 */

/**
 * Algebra for carrier object Int in category Scala types.
 * Note that this is nonrecursive.
 */
def length[A]: Algebra[A, Option, Int] = _ => {
  case None    => 0
  case Some(n) => n + 1
}

/**
 * Fixed point of NatF (recursive type based on MyList)
 * as carrier object for initial algebra.
 */
type MyList[A] = Cofree[Option, A]

val nil:    MyList[String] = Cofree("dummy", None)
val list1:  MyList[String] = Cofree("hello", Some(nil))
val list2:  MyList[String] = Cofree("world", Some(list1))
val list3:  MyList[String] = Cofree("good morning", Some(list2))

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
 * Now we can fold the length algebra into instances.
 */
nil.cata(length)  assert_=== 0
list3.cata(length) assert_=== 3

/**
 * Another algebra for carrier object Int but specific item type, also Int.
 */
def sum[A]: Algebra[Int, Option, Int] = v => {
  case None    => 0
  case Some(n) => v + n
}

val list4: MyList[Int] = Cofree(4, Some(Cofree(3, Some(Cofree(2, Some(Cofree(1, Some(Cofree(-1, None)))))))))
list4.cata(sum) assert_=== 10


type Coalgebra[S[_], B] = B => S[B]

/**
 * Coalgebra for carrier object Int in category Scala types.
 */
def from: Coalgebra[Option, Int] = (n: Int) => {
  require { n >= 0 }
  if (n == 0)
    None
  else
    Some(n / 2)
}

/**
 * Now we can create instances by unfolding the coalgebra from a starting value.
 */
Cofree.unfoldC(0)(from).cata(length) assert_=== 0 // Nil
Cofree.unfoldC(8)(from).cata(length) assert_=== 4 // Seq(8, 4, 2, 1)
println("yahoo")

// TODO fashion other examples after this one