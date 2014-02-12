/*
 * data List[A] = Nil | A :: List[A]
 */

assert { 1 :: 2 :: 3 :: Nil == List(1, 2, 3) }

def reverse[A]: List[A] => List[A] = {
  case Nil => Nil
  case (h :: t) => reverse(t) :+ h
}
assert { reverse(Nil) == Nil }
assert { reverse((1 to 5).toList) == (5 to 1 by -1).toList }

def reverseAsFold[A](xs: List[A]) =
  xs.foldLeft {
    Nil: List[A]
  } {
    (ys, x) => x :: ys // same as +:
  }

assert { reverseAsFold(Nil) == Nil }
assert { reverseAsFold((1 to 5).toList) == (5 to 1 by -1).toList }

// TODO map
// TODO mapAsFold

// TODO foldLeft

println("■")