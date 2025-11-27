import kotlin.math.*

// Базовий клас "Фігура"
open class Figure {
    open fun area(): Double {
        throw NotImplementedError("Метод area() не реалізований у базовому класі")
    }

    open fun perimeter(): Double {
        throw NotImplementedError("Метод perimeter() не реалізований у базовому класі")
    }

    open fun printInfo() {
        println("Фігура: площа = ${area()}, периметр = ${perimeter()}")
    }
}
