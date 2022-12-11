val list = mutableListOf(1,3,5)

fun main() {
    list.add(7)
    list.forEvery { it ->
        if (it == 3) {
            return
        }
        println("$it")
    }

    println("Done!")
}


inline fun <T> List<T>.forEvery(itemAction : (T) -> Unit) {
    list.reversed().forEach { itemAction(it) }
}