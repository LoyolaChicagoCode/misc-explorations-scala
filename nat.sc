import scalaz.syntax.equal._ // for assert_===
import scalaz.std.anyVal._   // for assert_=== to work on basic values

sealed trait Nat
case class Zero() extends Nat
case class Succ(n: Nat) extends Nat

val zero: Nat = Zero()
val one: Nat = Succ(zero)
val two: Nat = (Succ andThen Succ)(Zero())
val two2: Nat = (Succ compose Succ)(Zero())
val three: Nat = Succ(Succ(Succ(Zero())))

def succ = Succ

succ(zero) assert_=== one

def toInt: Nat => Int = {
  case Zero() => 0
  case Succ(n) => 1 + toInt(n)
}

toInt(zero) assert_=== 0
toInt(three) assert_=== 3

def plus(m: Nat)(n: Nat): Nat = m match {
  case Zero() => n
  case Succ(m1) => ???
}

toInt(plus(two)(three)) assert_=== 5