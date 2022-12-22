import scala.math._

trait Complex {
  def re: Double
  def im: Double
  def mod: Double
  def arg: Double
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

case class ComplexDeg(re: Double, im: Double) extends Complex {
  override def toString() = "%f + %fi".format(re, im)
  private val z = new ComplexRad(re, im)
  val arg = z.arg * 180 / Pi
  export z.{mod, pow}
}

object Tests extends App {
  val phrase = "Le nombre z=%s a comme argument %f et module %f"
  val z1 = new ComplexRad(1, 1)
  val z2 = new ComplexDeg(1, 1)
  List(z1, z2).foreach { z =>
    println(phrase.format(z, z.arg, z.mod))
    List(3, 5, 7).foreach { n =>
      val zp = z.pow(n)
      println(phrase.format(zp, zp.arg, zp.mod))
    }
  }
}
