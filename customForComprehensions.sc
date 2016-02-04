/**
 * A simple case class that happens to implement
 * map (for Functor) and flatMap (for Monad).
 */
case class Foo[A](v: A) {
  def map[B](f: A => B) = Foo(f(v))
  def flatMap[B](f: A => Foo[B]) = f(v)
}

// Scala uses reflection to support for-comprehensions automatically
// when these two methods are present.
val r = for {
  v <- Foo(3)     // translates to flatMap
  w <- Foo(v + 5) // last binding translates to map
} yield w
assert { r.v == 8 }

// Result of transformation of syntactic sugar to flatMap and map invocations.
val s = Foo(3).flatMap(v =>
  Foo(v + 5).map(w =>
    w)
)
assert { s.v == 8 }

println("■")