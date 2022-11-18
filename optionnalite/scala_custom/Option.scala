/** Reconstitution partielle de Option, basée sur certains des
 * exercices du livre "Functional Programming in Scala", Paul Chiusano
 * & Rúnar Bjarnason, @Manning 2015
 */
case class Some[+A](v: A) extends Option[A]
case object None extends Option[Nothing]

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(v) => Some(f(v))
  }

  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(v) => f(v)
  }

  /** B >: A indique que B doit être un "surtype" de A (pas possible
    * dans Kotlin!) */
  def orElse[B >: A](optb: => Option[B]): Option[B] = this match {
    case None => optb
    case _ => this
  }

  /** => B indique que l'argument "lambda" ne sera évalué que si
   * nécessaire ("lazy evaluation") (possible dans Kotlin mais
   * hors-sujet pour le chapitre "nullabilité et optionnalité")
   */
  def getOrElse[B >: A](defaut: => B): B = this match {
    case Some(v) => v
    case None => defaut
  }

  def filter(p: A => Boolean): Option[A] = this match {
    case Some(v) if p(v) => this
    case _ => None
  }

  /** semble nécessaire pour la "for-comprehension" avec filtre */
  def withFilter(p: A => Boolean): Option[A] = this filter p
}

object Option {
  def apply[A](a: A): Option[A] =
    if (a == null) None
    else Some(a)
}
