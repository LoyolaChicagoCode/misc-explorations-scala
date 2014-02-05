// rename upon import
import collection.mutable.{Map => MMap}
val m2 = MMap("asdf" -> 3)

// continue with immutable maps (default)
val m = Map("hello" -> 7, "world" -> 9)
m("hello")

try {
  m("hello")
} catch {
  case _: Throwable => 8
}
m.get("hello")
m.get("world")
Some(7)
None

Some(7): Option[Int]
None: Option[Int]

// pattern matching
m.get("hello") match {
  case None => println("nope")
  case Some(v) => println("found value " + v)
}
m.get("world") match {
  case None => println("nope")
  case Some(v) => println("found value " + v)
}

// print sum if both lookups succeed
// gets unwieldy with more than two steps!
m.get("hello") match {
  case None => println("nope")
  case Some(v) =>
    m.get("world") match {
      case None => println("nope")
      case Some(w) => println("the sum is " + (v + w))
    }
}

// for comprehension
val z = for {
  v1 <- m.get("hello")
  v2 <- m.get("hello")
  v3 <- m.get("hello1")
  v4 <- m.get("hello")
  v5 <- m.get("hello")
  w <- m.get("world")
} yield (v1 + v2 + v3 + v4 + v5 + w)

println(z)
