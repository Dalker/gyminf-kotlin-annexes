/** Imitation d'une extension function avec Scala */
import scala.language.implicitConversions  // permet les implicit def

class ExtendedString(s: String) {
  // capitalize existe déjà dans Scala, donc pas besoin (et pas possible)
  // de la redéfinir, sans aboutir à un message d'erreur (ambiguité dans l'appel)
  // def capitalize = s.take(1).toUpperCase() + s.drop(1).toLowerCase()
  //
  // la méthode capitalize existante est appellée sans parenthèses, donc nous
  // faisons pareil pour anticapitalize (selon le principe énoncé dans
  // https://stackoverflow.com/questions/6939908/scala-methods-with-no-arguments
  // qui indique que c'est la convention pour des méthodes sans argument n'ayant
  // pas d'effet de bord, que l'on veut délibérément ne pas distinguer d'une
  // propriété)
  def anticapitalize = s.take(1).toLowerCase() + s.drop(1).toUpperCase()
}

object Main extends App {
  // l'implicit def doit être défini dans le bon "scope"
  // si on le définit en-dehors de main on aboutit à une erreur
  implicit def stringToExtendedString(s: String) = new ExtendedString(s)

  for (test <- List("blabla", "BLABLA", "b", "")) {
    print("original: " + test)
    print(", capitalized: " + test.capitalize)
    println(", anticapitalized: " + test.anticapitalize)
  }
}
