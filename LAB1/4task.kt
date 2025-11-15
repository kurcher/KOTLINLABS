fun main() {
    print("Введіть N: ")
    val N = readLine()!!.toInt()

    var sum = 0.0

    for (n in 1..N) {
        var pow3n = 1.0
        var fact = 1.0
        var powNn = 1.0

        // Обчислюємо 3^n
        for (i in 1..n) pow3n *= 3

        // Обчислюємо n!
        for (i in 1..n) fact *= i

        // Обчислюємо n^n
        for (i in 1..n) powNn *= n

        sum += (pow3n * fact) / powNn
    }

    println("Сума ряду = $sum")
}
