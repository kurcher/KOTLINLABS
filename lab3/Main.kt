fun main() {
    try {
        val triangle = Triangle(0.0, 0.0, 3.0, 0.0, 0.0, 4.0)
        triangle.printInfo()

        val circle = Circle(5.0)
        circle.printInfo()
    } catch (e: Exception) {
        println("Виникла помилка: ${e.message}")
    }
}
