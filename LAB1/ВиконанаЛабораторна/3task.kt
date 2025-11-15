fun main() {
    print("Введіть перше число: ")
    val a = readLine()!!.toInt()
    print("Введіть друге число: ")
    val b = readLine()!!.toInt()

    var result = 0
    for (i in 1..b) {
        result += a
    }

    println("Результат множення: $result")
}
