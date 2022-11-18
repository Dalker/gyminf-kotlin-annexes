package complex.delegation
import kotlin.math.*

interface ComplexDelInt {
    val re: Double
    val im: Double
    val mod: Double
    val arg: Double
    fun pow(n: Int): ComplexDelInt
}

data class ComplexDel(override val re: Double,
                   override val im: Double): ComplexDelInt {
    override fun toString() = "$re + ${im}i"
    override val mod: Double
        get() = sqrt(re * re + im *im)
    override val arg: Double // le donner entre -pi et pi
        get() = when {
            im == 0.0 -> 0.0 // arbitraire, pour éviter exception
            re >= 0 -> atan(re / im)
            im > 0 -> atan(re / im) + PI
            else -> atan(re / im) - PI
        }
    override fun pow(n: Int): ComplexDel {
        val mod = mod.pow(n)
        val arg = arg * n
        return ComplexDel(mod * cos(arg), mod * sin(arg))
    }
}

class ComplexDelDeg(val z: ComplexDelInt): ComplexDelInt by z {
    constructor(re: Double, im: Double): this(ComplexDel(re, im))
    override fun toString() = z.toString()
    override val arg: Double
        get() = z.arg * 180 / PI
}
