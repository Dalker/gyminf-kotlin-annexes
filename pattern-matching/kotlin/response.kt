/* Hiérarchie de classes pour illustrer le pattern matching structurel */

sealed class Response<out A: Any>
object Pending: Response<Nothing>()
data class Failure(val message: String): Response<Nothing>()
data class Success<A: Any>(val result: A, val comment: String? = null): Response<A>()

fun<A: Any> processResponse(reponse: Response<A>) = when(reponse) {
    is Pending -> "rien reçu"
    is Failure ->
        if (reponse.message == "tout va bien") "on a reçu un paradoxe!"
        else "on a reçu une erreur: " + reponse.message
    is Success<A> -> reponse.comment?.let {
        "ça a fonctionné et on nous a signalé: $it"
    } ?: "ça a fonctionné!"
}

fun main() {
    for (test in listOf(
        Pending,
        Success<Int>(42),
        Success<Int>(42, "quelle est la question?"),
        Failure("catastrophe"),
        Failure("tout va bien"),
    )) processResponse(test).also(::println)
}
