/**
 * data Nat = Zero | Succ Nat
 */
sealed trait Nat
case class Zero() extends Nat
case class Succ(n: Nat) extends Nat

val zero: Nat = Zero()
val one: Nat = Succ(zero)
val two: Nat = (Succ andThen Succ)(Zero())
val two2: Nat = (Succ compose Succ)(Zero())
val three: Nat = Succ(Succ(Succ(Zero())))

def succ = Succ

assert { succ(zero) == one }

def toInt: Nat => Int = {
  case Zero() => 0
  case Succ(n) => 1 + toInt(n)
}

assert { toInt(zero) == 0 }
assert { toInt(three) == 3 }

def plus(m: Nat)(n: Nat): Nat = m match {
  case Zero() => n
  case Succ(m1) => ???
}

assert { toInt(plus(two)(three)) == 5 }

// TODO times
// TODO fromInt
