import complex.composition.*
import complex.delegation.*
import complex.inheritance.*

val phrase = "Le nombre z=%s a comme argument %f"

// Un test à polymorphisme paramétrique permet de s'assurer de
// faire les choses proprement
fun<C> test(z: C, arg: (C) -> Double, pow: (C, Int) -> C) {
    println(phrase.format(z, arg(z)))
    listOf(3, 5, 7).forEach { n ->
        val zp = pow(z, n)
        println(phrase.format(zp, arg(zp)))
    }
}

println("Tests avec héritage")
test(ComplexInh(1.0, 1.0), { z -> z.arg }, { z, n -> z.pow(n) })
// Le test générique n'est pas accepté parce que z.pow(n) est de type différent de z
// test(ComplexInhDeg(1.0, 1.0), { z -> z.arg }, { z, n -> z.pow(n) })
// En effet c'est bien une source possible d'erreur: si on fait "à la main",
// on retrouve la classe de base, mais la rotation est *fausse*
val zh = ComplexInhDeg(1.0, 1.0)
println(phrase.format(zh, zh.arg))
listOf(3, 5, 7).forEach { n ->
    val zhp = zh.pow(n)
    println(phrase.format(zhp, zhp.arg))
}

println("Tests avec composition")
test(ComplexComp(1.0, 1.0), { z -> z.arg }, { z, n -> z.pow(n) })
// Maintenant on peut "caster" pour passer les tests correctement
test(ComplexCompDeg(1.0, 1.0), { z -> z.arg }, { z, n -> ComplexCompDeg(z.pow(n)) })
// On peut aussi faire le test "à la main" et retomber sur la classe de base,
// mais la rotation est quand même juste! L'argument utilisé est bien celui en radian
val zc = ComplexCompDeg(1.0, 1.0)
println(phrase.format(zc, zc.arg))
listOf(3, 5, 7).forEach { n ->
    val zcp = zc.pow(n)
    println(phrase.format(zcp, zcp.arg))
}

println("Tests avec délégation")
test(ComplexDel(1.0, 1.0), { z -> z.arg }, { z, n -> z.pow(n) })
test(ComplexDelDeg(1.0, 1.0), { z -> z.arg }, { z, n -> ComplexDelDeg(z.pow(n)) })
// Comme pour la composition, on peut "caster" pour passer les tests correctement,
// ou faire "à la main" et retrouver la classe de base, mais avec une rotation correcte!
val zd = ComplexDelDeg(1.0, 1.0)
println(phrase.format(zd, zd.arg))
listOf(3, 5, 7).forEach { n ->
    val zdp = zd.pow(n)
    println(phrase.format(zdp, zdp.arg))
}
