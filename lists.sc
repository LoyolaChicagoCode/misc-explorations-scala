// creation of immutable lists and
// related kinds of objects

List(1, 2, 3)
List.apply(1, 2, 3)

val xs = Seq(1, 2, 3)
assert { xs.length == 3 }

1 to 10
1 until 11

val m = Map("hello" -> List(1, 2, 3), "world" -> List(4, 5))
assert { m.get("hello") == Some(List(1, 2, 3)) }
assert { m.get("hullo") == None }

val r = for {
  v <- 1 to 10
  w <- 1 to 10
} yield (v, w, v * w)

r(4)

// pattern matching

(4, "asdf", List(1, 2)) match {
  case (x, y, z) => x + y.length + z.sum
}

println("■")
