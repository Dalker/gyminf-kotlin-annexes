class MockRequest(val mockValue: String?) {
    init { println("mock object created with mock value $mockValue") }
    fun getParameter(mockKey: String) = mockValue
}

class Exemple(param: String?) {
    private val request = MockRequest(param)

    fun optSyntax1() {
        val name = request.getParameter("name")
        val upper = name
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.uppercase()
        println(upper ?: "No name value (syntax 1)")
    }

    fun optSyntax2() {
        request.getParameter("name").let {
            when {
                it is String && it.trim().isNotEmpty() -> it.trim().uppercase()
                else -> "No name value (syntax 2)"
            }.also(::println)
        }
    }

    companion object {
        private val abc = mapOf(1 to "A")

        fun getValue(key: Int) {
            val bMaybe = abc[key]
            when (bMaybe) {
                is String -> "Found $bMaybe"
                else -> "Not found"
            }.also(::println)
        }

        fun getValue2(key: Int) {
            println(abc[key] ?.let { "Found %s".format(it) } ?: "Not found")
        }
    }
}

fun main() {
    listOf("minuscule", "   indent", "", null).forEach {
        val exemple = Exemple(it)
        exemple.optSyntax1()
        exemple.optSyntax2()
    }
    listOf(1, 2).forEach {
        Exemple.getValue(it)
        Exemple.getValue2(it)
    }
}
