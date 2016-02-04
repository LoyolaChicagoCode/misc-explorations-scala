import scala.util.Try

// we can iterate directly over successful Try values
for {
  x <- Try(5)
  y <- Try(6)
  z <- Try(y + 2)
} println(x + y + z)

// or keep them for further computations
val q = for {
  x <- Try(5)
  y <- Try(6)
  z <- Try(y + 2)
} yield x + y + z

assert { q.isSuccess }
// such as converting them to a (nonempty) option
assert { q.toOption.map(_ + 1) == Some(20) }

// a failure in one or more of the Try values leads to an empty iteration
for {
  x <- Try(5)
  y <- Try {
    6 / 0
  }
  z <- Try(y + 2)
} println(x + y + z)

val r = for {
  x <- Try(5)
  y <- Try {
    6 / 0
  }
  z <- Try(y + 2)
} yield(x + y + z)

assert { r.isFailure }
// and an empty option
assert { r.toOption.map(_ + 1) == None }

println("■")
