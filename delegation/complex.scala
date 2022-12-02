import scala.math._
import scala.language.implicitConversions

trait Complex {
  val re: Double
  val im: Double
  val mod: Double
  val arg: Double
  def pow(n: Int): Complex
}

case class ComplexRad(val re: Double, val im: Double) extends Complex {
  override def toString() = "%f + %fi".format(re, im)
  val mod = sqrt(re * re + im *im)
  val arg =
    if (im == 0.0 && re >= 0) 0.0
    else if (im == 0.0) Pi
    else if (re >= 0) atan(re / im)
    else if (im > 0) atan(re / im) + Pi
    else atan(re / im) - Pi
  def pow(n: Int): ComplexRad = {
    val m = scala.math.pow(mod, n)
    val a = arg * n
    return new ComplexRad(m * cos(a), m * sin(a))
  }
}

case class ComplexDeg(re: Double, im: Double) {
  override def toString() = "%f + %fi".format(re, im)
  private val z = new ComplexRad(re, im)
  val arg = z.arg * 180 / Pi
}
object ComplexDeg {
  implicit def DelegateToComplexRad(c: ComplexDeg) = c.z
}

object Tests extends App {
  val phrase = "Le nombre z=%s a comme argument %f"
  val z = new ComplexRad(1, 1)
  println(phrase.format(z, z.arg))
  List(3, 5, 7).foreach { n =>
    val zp = z.pow(n)
    println(phrase.format(zp, zp.arg))
  }
  val z2 = new ComplexDeg(1, 1)
  println(phrase.format(z2, z2.arg))
  List(3, 5, 7).foreach { n =>
    val zpr = z.pow(n)
    val zpd = new ComplexDeg(zpr.re, zpr.im)
    println(phrase.format(zpd, zpd.arg))
  }
}
