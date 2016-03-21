val db = Map("hallo" -> 2, "hello" -> 3, "hullo" -> 4, "world" -> 5, "hola" -> 7)

def pm(word: String): Unit = for {
  n <- db.get(word)
  i <- 1 to n
  w <- word.substring(0, i)
} {
  println(w)
}

def pm2(word: String): Unit = 
  db.get(word) map { n => (1 to n) map { _ => println(word) } }
