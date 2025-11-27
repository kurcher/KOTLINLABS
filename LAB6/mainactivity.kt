
package com.example.a6labkotlin



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // *** Встановіть макет з Завдання 2 ***
        setContentView(R.layout.activity_task2)

        // Отримуємо посилання на кореневий RelativeLayout та кнопку
        val rootLayout = findViewById<RelativeLayout>(R.id.root_relative_layout)
        val buttonToMove = findViewById<Button>(R.id.btn_to_move)

        // Додаємо слухач кліку на кнопку, щоб виконати переміщення
        buttonToMove.setOnClickListener {
            // 1. Видаляємо компонент з макету
            rootLayout.removeView(buttonToMove)

            // 2. Створюємо нові параметри розмітки для RelativeLayout
            val newParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // ширина
                RelativeLayout.LayoutParams.WRAP_CONTENT  // висота
            )

            // 3. Встановлюємо нові правила прив'язки
            newParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            newParams.addRule(RelativeLayout.ALIGN_PARENT_START)

            // 4. Застосовуємо нові параметри до кнопки
            buttonToMove.layoutParams = newParams

            // 5. Вставляємо компонент назад у макет
            rootLayout.addView(buttonToMove)

            // Оновлюємо текст, щоб показати, що дія відбулась
            buttonToMove.text = "Я Переміщений!"
            buttonToMove.backgroundTintList = getColorStateList(android.R.color.holo_green_dark)
        }
    }
}
