fun main() {
    print("Введіть перше число: ")
    val a = readLine()!!.toInt()
    print("Введіть друге число: ")
    val b = readLine()!!.toInt()

    if (a > b)
        println("$a більше за $b")
    else if (a < b)
        println("$a менше за $b")
    else
        println("Числа рівні")
}
