import kotlin.reflect.KProperty

class MaLazyString(val f: () -> String) {
    var s: String? = null

    operator fun getValue(d: MonDelegateur, k: KProperty<*>): String {
        if (s == null) {
            s = f() // calculé une seule fois
            println("Le délégateur $d m'a demandé la propriété $k pour la première fois")
            println("Tel un intrus, je vois ses données: base = ${d.base} et nombre=${d.nombre}")
            println("Un délégué d'interface ne ferait jamais ça!")
        } else {
            println("Le délégateur $d m'a demandé la propriété $k de nouveau")
        }
        return s as String
    }
}

class MonDelegateur(val base: String, val nombre: Int) {
    override fun toString() = "MonDelegateur($base, $nombre)"
    val chaine by MaLazyString { base.repeat(nombre) }
}

val test = MonDelegateur("bla", 5)
repeat(3) { println(test.chaine) }
