/*
 * data List[A] = Nil | A :: List[A]
 *
 * In the context of the org chart example, we already saw uses of the
 * Seq.map and Seq.fold/sum functions.
 *
 * - map applies a function to each item in a list
 *
 * - foldLeft successively applies a function to the partial result
 *   accumulated so far and the next list item
 *
 * - There is also foldRight, which does the same thing but starting
 *   from the back of the list. This makes a difference unless the function
 *   is commutative (which also implies that both of its arguments are of
 *   the same type).
 *   When used with lists, foldRight usually does things in the right order
 *   but is not tail-recursive, so its recursive call stack might grow very
 *   large.
 *
 *  - filter keeps only those items that satisfy the given predicate
 *    (boolean function).
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

/*
 * Up for a challenge? Try implementing these functions yourself and test
 * if they behave like the predefined ones!
 */

// TODO map
// TODO mapAsFold
// TODO foldLeft

println("■")