/**
 * A simple case class that happens to implement
 * map (for Functor) and flatMap (for Monad).
 */
case class Foo[A](v: A) {
  def map[B](f: A => B) = Foo(f(v))
  def flatMap[B](f: A => Foo[B]) = f(v)
}

/*
 * Scala uses reflection to support for-comprehensions automatically
 * when these two methods are present.
 */
for {
  v <- Foo(3)     // translates to flatMap
  if v % 2 != 0
  w <- Foo(v + 5) // last binding translates to map
} yield w

/*
 * Result of transformation of syntactic sugar to flatMap and map invocations.
 */
Foo(3).flatMap(v =>
  Foo(v + 5).map(w =>
    w)
)