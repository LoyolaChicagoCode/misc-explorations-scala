val r1 = for {
  x <- Right({ println("hello"); 3 }).right
  y <- Right({ println("world"); 5.5 }).right
} yield (x, y)
assert { r1 == Right((3, 5.5)) }

val r2 = for {
  x <- Left({ println("hello"); "bzzt" }).right
  y <- Right({ println("world"); 5.5 }).right
} yield (x, y)
assert { r2 == Left("bzzt") }

for {
  z <- Right(7).right
  x <- Right(5).right
  y <- Right(6).right
} println(x + y)
