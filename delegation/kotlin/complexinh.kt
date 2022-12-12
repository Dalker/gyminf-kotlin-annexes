package complex.inheritance
import kotlin.math.*

open class ComplexInhRad(val re: Double, val im: Double) {
    override fun toString() = "$re + ${im}i"
    val mod: Double
        get() = sqrt(re*re + im*im)
    open val arg: Double
        get() = when {
            im == 0.0 && re >= 0 -> 0.0
            im == 0.0 -> PI
            re >= 0 -> atan(re / im)
            im > 0 -> atan(re / im) + PI
            else -> atan(re / im) - PI
        }
    fun pow(n: Int): ComplexInhRad {
        val m = mod.pow(n)
        val a = arg * n
        return ComplexInhRad(m * cos(a), m * sin(a))
    }
}

class ComplexInhDeg(re: Double, im: Double): ComplexInhRad(re, im) {
    override val arg: Double
        get() = super.arg * 180 / PI
}
