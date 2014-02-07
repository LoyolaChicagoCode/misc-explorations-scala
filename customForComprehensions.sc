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
  v <- Foo(3) // translated to flatMap
  w <- Foo(v + 5) // translated to Map
} yield w
