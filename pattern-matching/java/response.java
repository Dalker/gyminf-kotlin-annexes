import java.util.List;

final class Nothing {};

sealed abstract class Response<A>
    permits Pending, Failure, Success{};

final class Pending extends Response<Nothing> {};

final class Failure extends Response<Nothing> {
    String message;
    Failure(String msg) { message = msg; }
};

final class Success<A> extends Response<A> {
    A result;
    String comment;

    Success(A res, String comment) {
        result = res;
        this.comment = comment;
    }

    Success(A res) {
        result = res;
        comment = null;
    }
}

class Main {

    static String processResult(Response r) {
        return switch (r) {
            case Pending i -> "rien reçu";
            case Failure f && f.message == "tout va bien"
                -> "on a reçu un paradoxe!";
            case Failure f -> "on a reçu une erreur: " + f.message;
            case Success s && s.comment == null -> "ça a fonctionné!";
            case Success s
                -> "ça a fonctionné et on nous rapporte: " + s.comment;
        };
    }

    public static void main(String[] args) {
      List.of(new Pending(),
              new Success<Integer>(42),
              new Success<Integer>(42, "quelle est la question?"),
              new Failure("catastrophe"),
              new Failure("tout va bien")).forEach((r)
                      -> System.out.println(processResult(r)));
    }
}
