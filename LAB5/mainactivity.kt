package com.example.a5labkotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Знаходимо всі наші View-елементи за їхніми ID з XML
        val etNum1 = findViewById<EditText>(R.id.etNum1)
        val etNum2 = findViewById<EditText>(R.id.etNum2)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSub = findViewById<Button>(R.id.btnSub)
        val btnMul = findViewById<Button>(R.id.btnMul)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // 2. Встановлюємо "слухачів" (event listeners) для кожної кнопки

        // Додавання
        btnAdd.setOnClickListener {
            performOperation(etNum1, etNum2, tvResult, "add")
        }

        // Віднімання
        btnSub.setOnClickListener {
            performOperation(etNum1, etNum2, tvResult, "sub")
        }

        // Множення
        btnMul.setOnClickListener {
            performOperation(etNum1, etNum2, tvResult, "mul")
        }

        // Ділення
        btnDiv.setOnClickListener {
            performOperation(etNum1, etNum2, tvResult, "div")
        }
    }

    /**
     * Загальна функція для виконання арифметичних операцій.
     * Вона бере текстові поля, зчитує їх, і виконує дію.
     */
    private fun performOperation(
        etNum1: EditText,
        etNum2: EditText,
        tvResult: TextView,
        operation: String
    ) {
        // Отримуємо текст з полів введення
        val sNum1 = etNum1.text.toString()
        val sNum2 = etNum2.text.toString()

        // Перевірка, чи поля не порожні
        if (sNum1.isEmpty() || sNum2.isEmpty()) {
            tvResult.text = "Помилка: Введіть обидва числа"
            return
        }

        try {
            // Конвертуємо текст у числа (Double, щоб підтримувати дробові)
            val num1 = sNum1.toDouble()
            val num2 = sNum2.toDouble()
            var result = 0.0

            // Виконуємо операцію
            when (operation) {
                "add" -> result = num1 + num2
                "sub" -> result = num1 - num2
                "mul" -> result = num1 * num2
                "div" -> {
                    // Окрема перевірка для ділення на нуль
                    if (num2 == 0.0) {
                        tvResult.text = "Помилка: Ділення на нуль!"
                        return
                    }
                    result = num1 / num2
                }
            }

            // Виводимо результат у текстове поле
            tvResult.text = "Результат: $result"

        } catch (e: NumberFormatException) {
            // Цей блок виконається, якщо користувач ввів
            // щось, що не є числом (наприклад, "abc" або "1.2.3")
            tvResult.text = "Помилка: Невірний формат числа"
        }
    }
}
