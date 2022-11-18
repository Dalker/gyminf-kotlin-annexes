class MockRequest(val mockValue: Option<String>) {
    init { println("mock object created with mock value $mockValue") }
    fun getParameter(mockKey: String): Option<String> = mockValue
}

class Exemple(val request: MockRequest) {

    fun getReplySyntax1() {
        val name = request.getParameter("name")
        val upper = name
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { it.uppercase() }
        println(upper.getOrElse("No name value (syntax 1)"))
    }

    fun getReplySyntax2() {
        val name = request.getParameter("name")
        println(
            when {
                name is Some && name.value.trim().isNotEmpty() ->
                    name.value.trim().uppercase()
                else -> "No name value (syntax 2)"
            }
        )
    }

    fun getReplySyntax3() {
        request.getParameter("name").let {
            when {
                it is Some && it.value.trim().isNotEmpty() ->
                    it.value.trim().uppercase()
                else -> "No name value (syntax 3)"
            }.apply(::println)
        }
    }

    companion object {
        fun getValue(key: Int) {
            val abc = mapOf(1 to "A")
            val bMaybe = Option.fromNullable(abc[key])
            when (bMaybe) {
                is Some -> "Found ${bMaybe.value}"
                is None -> "Not found"
            }.apply(::println)
        }
    }
}

fun main() {
    listOf(
        Some("minuscule"),
        Some("   indent"),
        Some(""),
        None
    ).forEach {
        val exemple = Exemple(MockRequest(it))
        exemple.getReplySyntax1()
        exemple.getReplySyntax2()
        exemple.getReplySyntax3()
    }
    listOf(1, 2).forEach { Exemple.getValue(it) }
}