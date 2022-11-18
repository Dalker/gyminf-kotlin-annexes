// imiter les scope functions dans scala

// but: objet.let(lambda) -> lambda(objet), permettant l'enchaînement

import scala.language.implicitConversions
import scala.util.chaining._  // -> pipe et tap

import java.util.Calendar
import java.util.Locale

object ScopeFunctions {
  implicit class ExtendedAny[S](obj: S) {
    def let[T](func: S => T) = func(obj)
    def also[T](func: S => T) = { func(obj); obj }
  }
}

object JourFutur {
  import ScopeFunctions._

  // val LOCALE = Locale("fr", "CH") // ne fonctionne pas dans Scala (!?)
  val LOCALE = Locale.FRENCH

  // version impérative pour comparaison
  def jourFutur0(deltaAnnees: Int = 0) = {
    val calendrier = Calendar.getInstance()
    calendrier.set(Calendar.YEAR, calendrier.get(Calendar.YEAR) + deltaAnnees)
    calendrier.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
  }

  // première version de let/also
  def jourFutur1(deltaAnnees: Int = 0) = Calendar.getInstance().also { it =>
    // nommage inévitable si argument utilisé plus d'une fois
    it.set(Calendar.YEAR, it.get(Calendar.YEAR) + deltaAnnees)
  }.let {
    // argument anonyme _ possible s'il apparaît à un seul endroit
    _.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
  }

  // deuxième version plus proche des run/apply de kotlin
  //
  // comme import est local au /scope/, on ne peut pas s'en passer dans
  // l'expression fonctionnelle elle-même - pas terrible quand celle-ci se
  // limite à une seule ligne de code, comme ici, vu que cela double sa
  // taille au lieu de la diminuer...
  def jourFutur2(deltaAnnees: Int = 0) = Calendar.getInstance().also { it =>
    import it._
    set(Calendar.YEAR, get(Calendar.YEAR) + deltaAnnees)
  }.let { it =>
    import it._
    getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
  }

  // troisième version: on utilise simplement les fonctions pipe et tap de Scala
  def jourFutur3(deltaAnnees: Int = 0) = Calendar.getInstance().tap { it =>
    it.set(Calendar.YEAR, it.get(Calendar.YEAR) + deltaAnnees)
  }.pipe {
    _.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
  }
}

object main extends App {
  import ScopeFunctions._
  import JourFutur._

  val PHRASE = "Dans précisément %d ans nous serons un %s."
  val DELTA_ANNEES = 10

  println("bLA".let { x => x.toUpperCase + x.toLowerCase })

  for (fun <- List[Int => String](jourFutur0, jourFutur1, jourFutur2, jourFutur3)) {
    println(PHRASE.format(DELTA_ANNEES, fun(DELTA_ANNEES)))
  }

}
