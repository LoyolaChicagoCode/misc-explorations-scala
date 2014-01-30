// creation of immutable lists and
// related kinds of objects
List(1, 2, 3)
List.apply(1, 2, 3)
Seq(1, 2, 3)
1 to 10
1 until 10

val m = Map("hello" -> List(1, 2, 3), "world" -> List(4, 5))

val r = for {
  v <- 1 to 10
  w <- 1 to 10
} yield (v, w, v * w)

r(4)

(4, "asdf", List(1, 2)) match {
  case (x, y, z) => x + y.length + z.sum
}

println("done")