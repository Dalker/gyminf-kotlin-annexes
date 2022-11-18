package complex.inheritance
import kotlin.math.*

open class ComplexInh(val a: Double, val b: Double) {
    override fun toString() = "$a + ${b}i"
    val mod: Double
        get() = sqrt(a*a + b*b)
    open val arg: Double
        get() = when {
            a > 0 -> atan(a / b)
            b > 0 -> atan(a / b) + PI
            else -> atan(a / b) - PI
        }
    fun pow(n: Int): ComplexInh {
        val m = mod.pow(n)
        val a = arg * n
        return ComplexInh(m * cos(a), m * sin(a))
    }
}

class ComplexInhDeg(a: Double, b: Double): ComplexInh(a, b) {
    override val arg: Double
        get() = super.arg * 180 / PI
}
