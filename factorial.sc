import scala.language.postfixOps
import scala.util.control.Exception._

/** Straightforward recursive definition of factorial. */
val fac: Int => Int = n => if (n <= 0) 1 else n * fac(n - 1)
assert { fac(5) == 120 }

/**
 * This tail-recursive version of `fac` runs in constant space. It
 * translates directly to a while loop, where the accumulator `acc`
 * is the value computed so far.
 *
 * The annotation ensures that all recursive calls are in tail position,
 * that is, the last step before a branch of the function returns.
 * In particular, if we apply this annotation to the `fac` method above,
 * we will get an error!
 */
@scala.annotation.tailrec
val facAcc: (Int, Int) => Int = { case (n, acc) => if (n <= 0) acc else facAcc(n - 1, acc * n) }
assert { facAcc(5, 1) == 120 }

/** Same as above but with guarded pattern matching. */
@scala.annotation.tailrec
val facAcc2: (Int, Int) => Int = { 
  case (n, acc) if n <= 0 => acc
  case (n, acc)           => facAcc2(n - 1, acc * n)
}
assert { facAcc2(5, 1) == 120 }

/** Nonrecursive function whose fixpoint in the first argument is factorial. */
val preFac: (Int => Int) => (Int => Int) = g => (n => if (n <= 0) 1 else n * g(n - 1))

// We can now compute parts of factorial by supplying a sufficiently
// long chain of nested preFac invocations.
val one = Function.const(1)_

assert { preFac(one)(5)                                                 == 5 }
assert { preFac(preFac(one))(5)                                         == 5 * 4 }
assert { preFac(preFac(preFac(one)))(5)                                 == 5 * 4 * 3 }
assert { preFac(preFac(preFac(preFac(one))))(5)                         == 5 * 4 * 3 * 2 }
assert { preFac(preFac(preFac(preFac(preFac(one)))))(5)                 == 5 * 4 * 3 * 2 * 1 }
assert { preFac(preFac(preFac(preFac(preFac(preFac(one))))))(5)         == 5 * 4 * 3 * 2 * 1 * 1 }

// It's OK if the chain is longer than necessary.
assert { preFac(preFac(preFac(preFac(preFac(preFac(preFac(one)))))))(5) == 5 * 4 * 3 * 2 * 1 * 1 }

object ctx {
  /** Fixpoint formed using recursion in the language. */
  val fac2: Int => Int = preFac(fac2) // n => if (n <= 1) 1 else n * fac2(n - 1)
  assert { fac2(5) == 120 }
}

// other attempts at worksheet top-level fail
val fac3: Int => Int = preFac(fac3) // preFac's first argument g is null
assert { failing(classOf[NullPointerException]) opt fac3(5) isEmpty }

lazy val fac4: Int => Int = preFac(fac4) // StackOverflowError
assert { catching(classOf[StackOverflowError]) opt fac4(5) isEmpty }

/**
 * Fixpoint combinator to generalize ad-hoc recursion.
 *
 * Y(f) = f(Y(f))
 * Y[T]: (T => T) => T
 *
 * In strict languages like Scala we need an explicit second argument:
 *
 * Y(f)(n) = f(Y(f))(n)
 * Y[A, R]: ((A => R) => (A => R)) => (A => R)
 *
 * Assume f has one or more occurrences of g (like fac above).
 *
 * f = g => n => ...g...
 *
 * Idea: when we wrap Y around f, g will be bound to Y wrapped around f.
 * This means as long as there is still an occurrence of g left during evaluation,
 * f has access to a Y wrapped around f that it can unravel. When we reach the
 * recursive base case, there is no more Y wrapped around f, and we evaluate
 * the remaining expression directly.
 *
 * Example:
 *
 *   Y(preFac)(3)
 * = preFac(Y(preFac))(3)                                                    by def of Y
 * = (if (n <= 0) 1 else n * g(n - 1))[g := Y(preFac), n := 3]               by def of preFac
 * = if (3 <= 0) 1 else 3 * Y(preFac)(2)                                     by substitution
 * = 3 * Y(preFac)(2)                                                        by def of if
 * = 3 * preFac(Y(preFac))(2)                                                by def of Y
 * = 3 * 2 * Y(preFac)(1)                                                    ...
 * = 3 * 2 * preFac(Y(preFac))(1)                                            by def of Y
 * = 3 * 2 * 1 * Y(preFac)(0)                                                ...
 * = 3 * 2 * 1 * preFac(Y(preFac))(0)                                        by def of Y
 * = 3 * 2 * 1 * (if (n <= 0) 1 else n * g(n - 1))[g := Y(preFac), n := 0]   by def of preFac
 * = 3 * 2 * 1 * 1                                                           by def of if
 */
def Y[A, R]: ((A => R) => (A => R)) => (A => R) = f => n => f(Y(f))(n)

// forming the fixpoint explicitly using our Y combinator
assert { Y(preFac)(5) == 120 }

/** Example where A and R are different: A = List[B], R = Int. */
def preLength[B](g: List[B] => Int)(xs: List[B]) = xs match {
  case Nil => 0
  case _ :: ys => 1 + g(ys)
}

// need to supply item type for compatibility under left-to-right type inference
assert { Y(preLength[Int])(List(1, 2, 3, 4, 5)) == 5 }

// Software engineering benefit of making recursion explicit and separating
// it from the actual functionality: can inject additional behaviors for
// each recursive invocation, e.g., call trace:
def trace[A, R](f: A => R)(arg: A): R = {
  println("arg = " + arg)
  val result = f(arg)
  println("res = " + result)
  result
}

println("call trace for length(List(1, 2, 3, 4, 5))")
assert { Y(trace[List[Int], Int]_ compose preLength[Int])(List(1, 2, 3, 4, 5)) == 5 }

println("call trace for fac(5)")
assert { Y(trace[Int, Int]_ compose preFac)(5) == 120 }

println("■")
