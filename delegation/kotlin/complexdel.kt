package complex.delegation
import kotlin.math.*

interface Complex {
    val re: Double
    val im: Double
    val mod: Double
    val arg: Double
    fun pow(n: Int): Complex
}

data class ComplexDelRad(override val re: Double,
                         override val im: Double): Complex {
    override fun toString() = "$re + ${im}i"
    override val mod: Double
        get() = sqrt(re * re + im *im)
    override val arg: Double // le donner entre -pi et pi
        get() = when {
            im == 0.0 && re >= 0 -> 0.0
            im == 0.0 -> PI
            re >= 0 -> atan(re / im)
            im > 0 -> atan(re / im) + PI
            else -> atan(re / im) - PI
        }
    override fun pow(n: Int): ComplexDelRad {
        val mod = mod.pow(n)
        val arg = arg * n
        return ComplexDelRad(mod * cos(arg), mod * sin(arg))
    }
}

data class ComplexDelDeg private constructor(private val z: Complex): Complex by z {
    constructor(a: Double, b: Double): this(ComplexDelRad(a, b))
    override fun toString() = z.toString()
    override val arg: Double
        get() = z.arg * 180 / PI
}
