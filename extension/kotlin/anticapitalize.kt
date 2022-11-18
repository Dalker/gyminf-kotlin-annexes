/** Exemple d'extension function dans Kotlin */

fun String.anticapitalize() = take(1).lowercase() + drop(1).uppercase()
fun String.capitalize() = take(1).uppercase() + drop(1).lowercase()
// capitalize() existe dans le type String mais est "deprecated" (vu qu'elle ne
// faisait pas ce qu'on attend), donc on peut l'écraser, pas de souci -- est-ce
// parce qu'elle est "deprecated" que notre définition l'emporte?
fun String.lowercase() = uppercase() // on essaye de changer une fonction d'instance
// non deprecated et on y arrive! parce qu'en fait String.lowercase() est *déjà*
// une extension function à la base (dans Kotlin standard)

fun main() {
    listOf("blabla", "BLABLA", "a", "").forEach {
        print("original: " + it)
        print(", lowercase: " + it.lowercase())
        print(", capitalized: " + it.capitalize())
        println(", anticapitalized: " + it.anticapitalize())
    }
}
