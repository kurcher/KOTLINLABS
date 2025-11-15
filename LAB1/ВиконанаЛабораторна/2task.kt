fun main() {
    print("Введіть ділене: ")
    var dividend = readLine()!!.toInt()
    print("Введіть дільник: ")
    val divisor = readLine()!!.toInt()

    var quotient = 0
    while (dividend >= divisor) {
        dividend -= divisor
        quotient++
    }

    println("Результат цілочисельного ділення: $quotient")
    println("Залишок: $dividend")
}
