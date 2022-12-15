import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

val LOCALE = Locale("fr", "CH")  // ne peut pas être const car pas "primitif"
const val PHRASE = "Dans précisément %d ans nous serons un %s."
const val DELTA_ANNEES = 10

/* Style de code avec variables locales pour stocker valeurs intermédiaires */
fun jourFutur1(deltaAnnees: Int = 0): String? {
    val date = LocalDate.now()
    val newDate = date.plusYears(deltaAnnees.toLong())
    val dayOfWeek = newDate.dayOfWeek
    return dayOfWeek.getDisplayName(TextStyle.FULL, LOCALE)
}

/* Style de code avec enchaînement de fonctions */
fun jourFutur2(deltaAnnees: Int = 0) =
    LocalDate.now().plusYears(
        deltaAnnees.toLong()
    ).dayOfWeek.getDisplayName(TextStyle.FULL, LOCALE)

/* Style de code en "pipelining" avec argument explicite */
fun jourFutur3(deltaAnnees: Int = 0) =
    LocalDate.now().let {
        it.plusYears(deltaAnnees.toLong())
    }.let {
        it.dayOfWeek
    }.let {
        it.getDisplayName(TextStyle.FULL, LOCALE)
    }

/* Style de code en "pipelining" avec argument implicite */
fun jourFutur4(deltaAnnees: Int = 0) =
    LocalDate.now().run {
        plusYears(deltaAnnees.toLong())
    }.run {
        dayOfWeek
    }.run {
        getDisplayName(TextStyle.FULL, LOCALE)
    }


fun main() {
    listOf(::jourFutur1, ::jourFutur2, ::jourFutur3, ::jourFutur4).forEach {
        println(PHRASE.format(DELTA_ANNEES, it(DELTA_ANNEES)))
    }
}
