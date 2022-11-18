/* Hiérarchie de classes pour illustrer le pattern matching structurel */

sealed trait Response[+A]

object Pending extends Response[Nothing]
case class Failure(message: String) extends Response[Nothing]
case class Success[+A](result: A, comment: Option[String] = None) extends Response[A]

object Main extends App {

  def processResult[A](response: Response[A]): String =
    response match {
      case Pending => "rien reçu"
      case Failure(message) if message == "tout va bien" => "on a reçu un paradoxe!"
      case Failure(message) => "on a reçu une erreur: " + message
      case Success(result, None) => "ça a fonctionné!"
      case Success(result, Some(comment))
          => "ça a fonctionné et on nous rapporte: " + comment
    }

  for (test <- List(
    Pending,
    Success[Int](42),
    Success[Int](42, Some("quelle est la question?")),
    Failure("catastrophe"),
    Failure("tout va bien"),
  )) {
    println(processResult(test))
  }
}
