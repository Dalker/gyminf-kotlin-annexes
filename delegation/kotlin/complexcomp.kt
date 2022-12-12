package complex.composition
import kotlin.math.*

data class ComplexCompRad(val re: Double, val im: Double) {
    override fun toString() = "$re + ${im}i"
    val mod: Double
        get() = sqrt(re * re + im *im)
    val arg: Double // le donner entre -pi et pi
        get() = when {
            im == 0.0 && re >= 0 -> 0.0
            im == 0.0 -> PI
            re >= 0 -> atan(re / im)
            im > 0 -> atan(re / im) + PI
            else -> atan(re / im) - PI
        }
    fun pow(n: Int): ComplexCompRad {
        val mod = mod.pow(n)
        val arg = arg * n
        return ComplexCompRad(mod * cos(arg), mod * sin(arg))
    }
}

class ComplexCompDeg(a: Double, b: Double) {
    private val z = ComplexCompRad(a, b)
    override fun toString() = z.toString()
    val re = z.re
    val im = z.im
    val mod = z.mod
    val arg: Double
        get() = z.arg * 180 / PI
    fun pow(n: Int) = z.pow(n)
}
