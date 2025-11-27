
package com.example.a7labkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // --- Елементи для Завдання 1 ---
    private lateinit var listView: ListView
    private lateinit var tvSelection: TextView

    // --- Елементи для Завдання 2 ---
    private lateinit var etXStart: EditText
    private lateinit var etXEnd: EditText
    private lateinit var etStep: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnExit: Button
    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація Завдання 1
        setupTask1()

        // Ініціалізація Завдання 2
        setupTask2()
    }

    // ===================================================================
    // ЛОГІКА ДЛЯ ЗАВДАННЯ 1
    // ===================================================================
    private fun setupTask1() {
        listView = findViewById(R.id.listView)
        tvSelection = findViewById(R.id.tvSelection)

        // 1. Створюємо дані (5 назв)
        val items = arrayOf(
            "Елемент 1: Java",
            "Елемент 2: Kotlin",
            "Елемент 3: Swift",
            "Елемент 4: Python",
            "Елемент 5: C#"
        )

        // 2. Створюємо ArrayAdapter
        // android.R.layout.simple_list_item_1 - це стандартний макет TextView,
        // вбудований в Android, який адаптер буде використовувати для кожного елемента.
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        // 3. Призначаємо адаптер для ListView
        listView.adapter = adapter

        // 4. Встановлюємо слухач кліків
        listView.setOnItemClickListener { parent, view, position, id ->
            // 'position' - це індекс натиснутого елемента
            val selectedItem = items[position] // або parent.getItemAtPosition(position) as String

            // Виведення у TextView
            tvSelection.text = getString(R.string.selected_item_prefix) + " " + selectedItem

            // Виведення у Toast
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()
        }
    }

    // ===================================================================
    // ЛОГІКА ДЛЯ ЗАВДАННЯ 2
    // ===================================================================
    private fun setupTask2() {
        etXStart = findViewById(R.id.etXStart)
        etXEnd = findViewById(R.id.etXEnd)
        etStep = findViewById(R.id.etStep)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnExit = findViewById(R.id.btnExit)
        gridView = findViewById(R.id.gridView)

        // Слухач для кнопки "Обчислити"
        btnCalculate.setOnClickListener {
            calculateAndDisplayTable()
        }

        // Слухач для кнопки "Вихід"
        btnExit.setOnClickListener {
            finish() // Закриває поточну активність (і додаток, якщо вона єдина)
        }
    }

    private fun calculateAndDisplayTable() {
        // 1. Отримуємо та валідуємо вхідні дані
        val xStartStr = etXStart.text.toString()
        val xEndStr = etXEnd.text.toString()
        val stepStr = etStep.text.toString()

        if (xStartStr.isEmpty() || xEndStr.isEmpty() || stepStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show()
            return
        }

        val xStart = xStartStr.toDoubleOrNull()
        val xEnd = xEndStr.toDoubleOrNull()
        val step = stepStr.toDoubleOrNull()

        if (xStart == null || xEnd == null || step == null || step <= 0) {
            Toast.makeText(this, "Некоректні числа або крок <= 0", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Табулюємо функцію (Y = X^2)
        val resultsList = mutableListOf<String>()
        var currentX = xStart

        // Додаємо заголовки
        resultsList.add("X")
        resultsList.add("Y = X²")

        while (currentX <= xEnd) {
            val currentY = currentX * currentX

            // Додаємо відформатовані значення X та Y у список
            // Використовуємо Locale.US, щоб десятковим роздільником була крапка
            resultsList.add(String.format(Locale.US, "%.2f", currentX))
            resultsList.add(String.format(Locale.US, "%.2f", currentY))

            currentX += step
        }

        // 3. Створюємо адаптер для GridView
        // Використовуємо наш кастомний макет grid_item.xml
        val gridAdapter = ArrayAdapter(this, R.layout.grid_item, resultsList)

        // 4. Призначаємо адаптер для GridView
        gridView.adapter = gridAdapter
    }
}
