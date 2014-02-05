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
    (ys, x) => x :: ys
  }

assert { reverseAsFold(Nil) == Nil }
assert { reverseAsFold((1 to 5).toList) == (5 to 1 by -1).toList }

// TODO map
// TODO mapAsFold

// TODO foldLeft

println("yahoo")