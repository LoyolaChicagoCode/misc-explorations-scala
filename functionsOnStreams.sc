/*
 * Streams are lazy, potentially infinite lists.
 *
 * data Stream[A] = Empty | A #:: Stream[A]
 *
 * As seen in the functions on lists example, we can easily define
 * our own recursive functions on lists (or streams). In particular,
 * we can write functions to perform operations that involve some or
 * all items in a list.
 *
 * For example, we can try to compute the list of primes using the
 * Sieve of Eratosthenes. Lets start with some explorations toward this end.
 * The predefined Stream.filter function comes in handy. Let's start by
 * successively filtering the known primes in ascending order:
 */

val no2 = (2 to 20) filter { _ % 2 != 0 }
val no3 = no2 filter { _ % 3 != 0 }
val no5 = no3 filter { _ % no3.head != 0 }

/*
 * Note how an iteration pattern emerges: in each step, we eliminate
 * multiples of the first element of the current sequence.
 * The successive first elements themselves are the primes. So:
 */

def withoutMultiplesOf(n: Int): Stream[Int] => Stream[Int] = _ filter { _ % n != 0 }

def primesRec: Stream[Int] => Stream[Int] = {
  case Stream.Empty => Stream.Empty
  case x #:: xs => x #:: (primesRec(withoutMultiplesOf(x)(xs)))
}

/*
 * Correctness proof: structural induction on the stream.
 *
 * Let's also set up some helpers for testing.
 */

val to20 = (2 to 20).toStream
val primesTo20 = Stream(2, 3, 5, 7, 11, 13, 17, 19)
def test(subject: Stream[Int] => Stream[Int]) = assert { subject(to20) == primesTo20 }

test { primesRec }

/*
 * A good question is whether we can express the sieve from above without
 * explicit recursion. Here is a first attempt:
 */

def primesFold(xs: Stream[Int]) =
  xs.foldRight(Stream.empty[Int])((y, ys) => y #:: withoutMultiplesOf(y)(ys))

test { primesFold }

/*
 * So this works, but unlike the original ad-hoc recursive version above,
 * it requires one step for each original list item instead of one step
 * for each resulting prime! That is not so good.
 *
 * (With fold(Left), you would have a harder time building the result
 * list.)
 *
 * It turns out that fold is not powerful enough to express the required
 * pattern of recursion. Fold is a factory function for catamorphisms,
 * http://en.wikipedia.org/wiki/Catamorphism
 * which break down the data structure step-by-step to produce a result.
 * (I hope we have some classics minors who can help with the Greek here.
 * The last time I studied Greek myself was at this public school founded
 * in 1604 by Jesuits: http://www.suso.schulen.konstanz.de )
 *
 * We will instead define an anamorphism,
 * [http://en.wikipedia.org/wiki/Anamorphism]
 * which builds up a data structure from a seed and a function applied
 * successively. The factory function for anamorphisms is unfoldRight:
 */

type Generator[Seed, Result] = Seed => Option[(Result, Seed)]

def unfoldRight[Seed, Result](s: Seed)(g: Generator[Seed, Result]): Stream[Result] = g(s) match {
  case None => Stream.empty
  case Some((curr, next)) => curr #:: unfoldRight(next)(g)
}

/*
 * Here, b is the seed and g is the step-by-step generator, which uses
 * the predefined Option type to produce a pair consisting of the next
 * item and the next seed. While fold requires you to process each item
 * in the underlying list, unfold recurses over the result of applying
 * function g to the seed, b1. This gives you a lot more power, as we will
 * see below.
 *
 * (Btw, Option is a much cleaner alternative to null references in
 * languages like Scala that have both.)
 *
 * As another challenge, try defining as anamorphisms: a simple range
 * generator, list identity, and map. (The latter two can easily be defined
 * as catamorphisms, too.)
 *
 * And finally:
 */

def primesUnfold(xs: Stream[Int]) = unfoldRight(xs)({
  case Stream.Empty => None
  case x #:: xs => Some((x, withoutMultiplesOf(x)(xs)))
})

test { primesUnfold }

/*
 * Yay! The beauty is that you get to filter the candidate list before
 * each new iteration, so this behaves exactly like the original ad-hoc
 * recursive version.
 *
 * Some final thoughts
 *
 * - Why not simply use ad-hoc recursion? Because that makes it
 *   considerably harder to reason about your code. For general
 *   operations such as map, fold, unfold, etc., you can prove general
 *   laws, which you can then apply to special cases. Such laws help
 *   you with correctness proofs, compiler optimizations, etc.
 *   For ad-hoc recursion, you need to come up with your own, specific
 *   proof.
 *
 * - Why bother with this formal stuff at all? Because it provides a
 *   solid foundation for the higher-level structures we build on top,
 *   and because it trains our minds to think in a certain way and develop
 *   an intuition for flaws in those structures. In other words, it gives
 *   you an edge over those who have not studied this material. Furthermore,
 *   by exploiting common abstractions, the amount of separate code you
 *   have to write goes down; this is a significant software engineering
 *   benefit.
 *
 * - Is this approach limited to lists? Not at all. As we will see in
 *   another collection of examples, this approach can be used on any
 *   algebraic data type. Conceptually, for each variant of the type,
 *   the fold function needs a corresponding argument; if there are
 *   more than two variants, the fold arguments are usually combined into
 *   a single structure corresponding to an algebra.
 *   http://en.wikipedia.org/wiki/Catamorphism#Example
 *   (Look here for more theoretical background:
 *   http://en.wikipedia.org/wiki/Initial_algebra#Use_in_Computer_Science )
 */

println("■")