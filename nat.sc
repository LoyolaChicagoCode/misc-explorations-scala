/**
 * In this example, we represent natural numbers as lists without item values:
 *
 * 0 = zero
 * 3 = succ(succ(succ(zero)))
 *
 * We can then define operations such as addition on these.
 *
 * data Nat = Zero | Succ Nat
 */
sealed trait Nat
case object Zero extends Nat
case class Succ(n: Nat) extends Nat

val one: Nat = Succ(Zero)
val two: Nat = (Succ andThen Succ)(Zero)
val two2: Nat = (Succ compose Succ)(Zero)
val three: Nat = Succ(Succ(Succ(Zero)))

assert { Succ(Zero) == one }

def toInt: Nat => Int = {
  case Zero    => 0
  case Succ(n) => 1 + toInt(n)
}

assert { toInt(Zero) == 0 }
assert { toInt(three) == 3 }

def plus(m: Nat)(n: Nat): Nat = m match {
  case Zero     => n
  case Succ(m1) => ???
}

assert { toInt(plus(two)(three)) == 5 }

// TODO times
// TODO fromInt

println("■")