/** "Mock class" pour pouvoir exécuter les exemples de la doc officielle */
class MockRequest(mockValue: Option[String]) {
  print("mock object created with mock value: ")
  println(mockValue)
  def getParameter(mockKey: String): Option[String] = mockValue
}

/** Le contenu des fonctions qui suivent est adapté directement de
 *  https://www.scala-lang.org/api/current/scala/Option.html
 *
 *  Les ajustements suivants ont cependant été faits:
 *  - ajout d'un "mock object" permettant de tester les différents case
 *  - séparation en fonctions afin de rendre le code plus facile à tester
 *  - ajout de messages explicites pour tous les "or else"
 *  - ajustement de la "syntaxe 3" pour obtenir le même résultat que pour
 *    les syntaxes 1 et 2.
 *
 *  Les commentaires en anglais sont copiés directement de
 *  https://www.scala-lang.org/api/current/scala/Option.html
 */
class Exemple(val request: MockRequest) {
  // The most idiomatic way to use an scala.Option instance is to treat it as
  // a collection or monad and use map,flatMap, filter, or foreach:

  /** syntaxe 1: map + filter */
  def getReplySyntax1(): Unit = {
    val name: Option[String] = request getParameter "name"
    val upper = name map { _.trim } filter { _.length != 0 } map { _.toUpperCase }
    println(upper getOrElse "No name value (syntax 1)")
  }

  /** syntaxe 2: for */
  def getReplySyntax2(): Unit = {
    val upper = for {
        name <- request getParameter "name"
        trimmed <- Some(name.trim)
        upper <- Some(trimmed.toUpperCase) if trimmed.length != 0
    } yield upper
    println(upper getOrElse "No name value (syntax 2)")
  }

  // Because of how for comprehension works, if None is returned from
  // request.getParameter, the entire expression results in None
  //
  // This allows for sophisticated chaining of scala.Option values without
  // having to check for the existence of a value.
  //
  // A less-idiomatic way to use scala.Option values is via pattern
  // matching: syntax 3: pattern matching

  /** syntaxe 3: pattern matching
   * NB: les 'case' ont été changés de l'exemple officiel afin d'obtenir le même
   *     résultat que pour les autres syntaxes
   **/
  def getReplySyntax3(): Unit = {
    val nameMaybe = request getParameter "name"
    nameMaybe match {
    case Some(name) if name.trim.length != 0 =>
        println(name.trim.toUpperCase)
    case _ =>
        println("No name value (syntax 3)")
    }
  }
}

// Interacting with code that can occasionally return null can be safely
// wrapped in scala.Option to become None and scala.Some otherwise.
object Exemple {
  /** Exemple d'utilisation d'Option pour gérer la nullabilité
   *
   * Un paramètre a été ajouté pour pouvoir tester les deux cas.
   *
   * NB: Le "faux constructeur" de Option -- en fait une méthode apply() --
   * permet de transformer un nullable en une option (None si null,
   * Some() sinon)
   * */
  def getValue(key: Int): Unit = {
    val abc = new java.util.HashMap[Int, String]
    abc.put(1, "A")
    val bMaybe = Option(abc.get(key))
    bMaybe match {
    case Some(b) =>
        println(s"Found $b")
    case None =>
        println("Not found")
    }
  }
}

/** Test de tous les cas avec toutes les syntaxes. */
object main {
  def main(args: Array[String]): Unit =  {
    List(
      Some("minuscule"),
      Some("   indent"),
      Some(""),
      None: Option[String]
    ) foreach { opt =>
        val exemple = new Exemple(new MockRequest(opt))
        exemple.getReplySyntax1()
        exemple.getReplySyntax2()
        exemple.getReplySyntax3()
    }
    List(1, 2) foreach { key => Exemple.getValue(key) }
}}
