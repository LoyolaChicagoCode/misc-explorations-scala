/**
 * Straightforward recursive definition of factorial.
 */
val fac: Int => Int = n => if (n <= 0) 1 else n * fac(n - 1)

assert { fac(5) == 120 }

/**
 * Nonrecursive function whose fixpoint in the first argument is factorial.
 */
def preFac(g: Int => Int)(n: Int): Int = if (n <= 0) 1 else n * g(n - 1)

/**
 * Fixpoint formed using recursion in the language.
 */
val fac2: Int => Int = preFac(fac2)

assert { fac2(5) == 120 }

// this will cause a stack overflow
// def fac: Int => Int = preFac(fac)

/**
 * Fixpoint combinator to generalize ad-hoc recursion.
 *
 * Y(f) = f(Y(f))
 * Y[A]: (A => A) => A
 *
 * In strict languages like Scala we need an explicit second argument:
 *
 * Y(f)(n) = f(Y(f))(n)
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
 * = preFac(Y(preFac))(3)                                        by def of Y
 * = (if (n <= 0) 1 else n * g(n - 1))[g := Y(preFac), n := 3]   by def of preFac
 * = if (3 <= 0) 1 else 3 * Y(preFac)(2)                         by substitution
 * = 3 * Y(preFac)(2)                                            by def of if
 * = 3 * preFac(Y(preFac))(2)                                    by def of Y
 * = 3 * 2 * Y(preFac)(1)                                        ...
 * = 3 * 2 * 1 * Y(preFac)(0)
 * = 3 * 2 * 1 * 1
 */

def Y[A]: ((A => A) => (A => A)) => (A => A) = f => n => f(Y(f))(n)

/**
 * forming the fixpoint explicitly using our Y combinator
 */
val fac3 = Y(preFac)

assert { fac3(5) == 120 }

println("■")