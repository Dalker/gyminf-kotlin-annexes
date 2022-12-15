import java.util.Calendar
import java.util.Locale

val LOCALE = Locale("fr", "CH")  // ne peut pas être const car pas "primitif"
const val PHRASE = "Dans précisément %d ans nous serons un %s."
const val DELTA_ANNEES = 10

/** style de code impératif */
fun jourFutur1(deltaAnnees: Int = 0): String? {
    val calendrier = Calendar.getInstance()
    calendrier.set(Calendar.YEAR, calendrier.get(Calendar.YEAR) + deltaAnnees)
    return calendrier.getDisplayName(Calendar.DAY_OF_WEEK,
                                     Calendar.LONG_FORMAT, LOCALE)
}

/** style de code en "piplelining" de foncitons avec argument explicite */
fun jourFutur2(deltaAnnees: Int = 0) = Calendar.getInstance().also {
        it.set(Calendar.YEAR, it.get(Calendar.YEAR) + deltaAnnees)
    }. let {
        it.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
    }

/** style de code en "piplelining" de foncitons avec argument implicite */
fun jourFutur3(deltaAnnees: Int = 0) = Calendar.getInstance().apply {
        set(Calendar.YEAR, get(Calendar.YEAR) + deltaAnnees)
    }. run {
        getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, LOCALE)
    }

/** tests */
fun main() {
    listOf(::jourFutur1, ::jourFutur2, ::jourFutur3).forEach {
        println(PHRASE.format(DELTA_ANNEES, it(DELTA_ANNEES)))
    }
}
