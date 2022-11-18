import java.util.Optional;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

class OptionalTests {
    static void getReply(MockRequest request) {
        Optional<String> name = request.getParameter("name");

        /** on peut enchaîner les méthodes comme dans Scala */
        var upper = name.map(String::strip)
            .filter(x -> x.length() != 0)
            .map(String::toUpperCase);

        System.out.println(upper.orElse("No name value"));
    }

    static void getValue(int key) {
        var abc = new HashMap<Integer, String>();
        abc.put(1, "A");
        var bMaybe = Optional.ofNullable(abc.get(key));
        if (bMaybe.isPresent()) {
            System.out.println(String.format("Found %s", bMaybe.get()));
        } else {
            System.out.println("Not found");
        }
    }

    public static void main(String[] args) {
        List<Optional<String>> options = new LinkedList<Optional<String>>() {{
            add(Optional.of("minuscule"));
            add(Optional.of("   indent"));
            add(Optional.of(""));
            add(Optional.empty());
        }};
        for(Optional<String> opt : options) {
            getReply(new MockRequest(opt));
        }
        getValue(1);
        getValue(2);
    }
}