object None: Option<Nothing>()
data class Some<A: Any>(val value: A): Option<A>()

sealed class Option<out A: Any> {
    companion object {
        fun<A> fromNullable(a: A?) = if (a == null) None else Some(a)
    }

    fun<B: Any> map(f: (A) -> B ): Option<B> =
        when (this) {
            is None -> None
            is Some -> Some<B>(f(this.value))
        }

    fun<B: Any> flatMap(f: (A) -> Option<B>): Option<B> =
        when (this) {
            is None -> None
            is Some -> f(this.value)
        }

    fun filter(p: (A) -> Boolean): Option<A> =
        when {
            this is Some && p(this.value) -> this
            else -> None
        }

    fun isEmpty(): Boolean =
        when (this) {
            is None -> true
            is Some -> false
        }
}

/** getOrElse() doit être définie comme "extension function" pour pouvoir
 * déclarer proprement la relation entre les types génériques A et B avec
 * "where". En effet, Kotlin ne permet pas de déclarer une relation de
 * supertype (comme Scala A :> B) et "where" ne s'applique qu'aux
 * génériques de la fonction, pas ceux de la classe contenant la
 * fonction...
 */

fun<A, B> Option<A>.getOrElse(default: B): B
        where A: B, B: Any =
    when (this) {
        is None -> default
        is Some -> this.value
    }
