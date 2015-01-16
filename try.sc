import scala.util.Try

for {
  x <- Try(5)
  y <- Try(6)
} println(x + y)

for {
  x <- Try(5)
  y <- Try(6 / 0)
} println(x + y)