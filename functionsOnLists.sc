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

def withoutMultiplesOf(n: Int): TraversableOnce[Int] => TraversableOnce[Int] = _ filter { _ % n != 0 }

//primesR []     = []
//primesR (x:xs) = x : (primesR (withoutMultiplesOf x xs))
//
//primesFold :: [Int] -> [Int]
//primesFold = foldr (\y ys -> y : withoutMultiplesOf y ys) []
//
//primesUnfold :: [Int] -> [Int]
//primesUnfold = unfoldr g where
//g []     = Nothing
//g (x:xs) = Just (x, withoutMultiplesOf x xs)


println("yahoo")