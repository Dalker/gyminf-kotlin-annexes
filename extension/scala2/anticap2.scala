/** Imitation d'une extension function avec Scala  (variante avec implicit class)*/

object Extensions {
  implicit class ExtendedString(s: String) {
    def anticapitalize = s.take(1).toLowerCase() + s.drop(1).toUpperCase()
  }
}

object Main extends App {
  import Extensions.ExtendedString  // nécessaire même si dans même fichier... :-(
  for (test <- List("blabla", "BLABLA", "b", "")) {
    print("original: " + test)
    print(", capitalized: " + test.capitalize)
    println(", anticapitalized: " + test.anticapitalize)
  }
}
