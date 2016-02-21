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

// all possible combinations of v and w values

val r = for {
  v <- 1 to 10
  w <- 1 to 10
} yield (v, w, v * w)

assert { r.length == 100 }
assert { r(4) == (1, 5, 5) }

// pattern matching

(4, "asdf", List(1, 2)) match {
  case (x, y, z) => x + y.length + z.sum
}

// filters and range of w values depending on v

for {
  v <- 1 to 10
  if v % 2 != 0
  w <- 1 to v
} {
  print(v * w)
  print(" ")
  if (v == w) println()
}

// equivalent using operations on collections
// note the nested map corresponding to the iteration over w

(1 to 10) filter { v => v % 2 != 0 } flatMap { v => (1 to v) map { w => (v, w) } } map { case (v, w) => 
  print(v * w)
  print(" ") 
  if (v == w) println()
}

println("■")
