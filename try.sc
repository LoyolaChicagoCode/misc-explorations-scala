import scala.util.Try

for {
  x <- Try(5)
  y <- Try(6)
  z <- Try(y + 2)
} println(x + y + z)

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

r.isFailure