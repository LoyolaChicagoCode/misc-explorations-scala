def loeb[A](fs: Seq[Seq[A] => A]): Seq[A] = {
  def xs: Seq[A] = fs.map(_(xs))
  xs
}

// def loeb[A, F[_]: Functor](fs: F[F[A] => A]): F[A] = {
//   def xs: F[A] = fs.map(_(xs))
//   xs
// }

assert { loeb(Seq[Seq[Int] => Int](_.length, _(0), xs => xs(0) + xs(1)).view) == Seq(3, 3, 6) }

assert { loeb(Seq.empty[Seq[Int] => Int]).isEmpty }

println("■")
