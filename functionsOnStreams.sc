/*
* data Stream[A] = Empty | A #:: Stream[A]
*/

val to20 = (2 to 20).toStream
val primesTo20 = Stream(2, 3, 5, 7, 11, 13, 17, 19)
def test(subject: Stream[Int] => Stream[Int]) = assert { subject(to20) == primesTo20 }

def withoutMultiplesOf(n: Int): Stream[Int] => Stream[Int] = _ filter { _ % n != 0 }

def primesRec: Stream[Int] => Stream[Int] = {
  case Stream.Empty => Stream.Empty
  case x #:: xs => x #:: (primesRec(withoutMultiplesOf(x)(xs)))
}

test { primesRec }

def primesFold(xs: Stream[Int]) =
  xs.foldRight(Stream.empty[Int])((y, ys) => y #:: withoutMultiplesOf(y)(ys))

test { primesFold }

type Generator[Seed, Result] = Seed => Option[(Result, Seed)]

def unfoldRight[Seed, Result](s: Seed)(g: Generator[Seed, Result]): Stream[Result] = g(s) match {
  case None => Stream.empty
  case Some((curr, next)) => curr #:: unfoldRight(next)(g)
}

def primesUnfold(xs: Stream[Int]) = unfoldRight(xs)({
  case Stream.Empty => None
  case x #:: xs => Some((x, withoutMultiplesOf(x)(xs)))
})

test { primesUnfold }

println("■")