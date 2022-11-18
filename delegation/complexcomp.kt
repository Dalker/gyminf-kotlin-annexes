package complex.composition
import kotlin.math.*

data class ComplexComp(val re: Double, val im: Double) {
    override fun toString() = "$re + ${im}i"
    val mod: Double
        get() = sqrt(re * re + im *im)
    val arg: Double // le donner entre -pi et pi
        get() = when {
            im == 0.0 -> 0.0 // arbitraire, pour éviter exception
            re >= 0 -> atan(re / im)
            im > 0 -> atan(re / im) + PI
            else -> atan(re / im) - PI
        }
    fun pow(n: Int): ComplexComp {
        val mod = mod.pow(n)
        val arg = arg * n
        return ComplexComp(mod * cos(arg), mod * sin(arg))
    }
}

class ComplexCompDeg(val z: ComplexComp) {
    constructor(re: Double, im: Double): this(ComplexComp(re, im))
    override fun toString() = z.toString()
    val re = z.re
    val im = z.im
    val mod = z.mod
    val arg: Double
        get() = z.arg * 180 / PI
    fun pow(n: Int) = z.pow(n)
}
