import java.util.Optional;

/** "Mock class" pour simuler une requête */
class MockRequest {
    Optional<String> mockValue;
    MockRequest(Optional<String> mv) {
        mockValue = mv;
    }

    public Optional<String> getParameter(String mockKey) {
        return mockValue;
    }
}
