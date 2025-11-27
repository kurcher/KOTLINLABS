import java.io.File

fun main() {
    println("Оберіть задачу (1 або 2):")
    when (readLine()!!.toInt()) {
        1 -> task1()
        2 -> task2()
        else -> println("Невірний вибір")
    }
}


fun task1() {
    val input = File("input.txt").readText().trim()

    // Якщо текст містить пробіли — розбиваємо по них
    val words = input.split(" ", "\n", "\t").filter { it.isNotEmpty() }

    // Якщо текст справді без пробілів — це одне слово
    if (words.isEmpty()) {
        File("output.txt").writeText("Вхідний текст порожній")
        return
    }

    val minLength = words.minOf { it.length }
    val minWords = words.filter { it.length == minLength }

    val result = buildString {
        appendLine("Слова мінімальної довжини ($minLength):")
        appendLine(minWords.joinToString(", "))
        appendLine("Кількість: ${minWords.size}")
    }

    File("output.txt").writeText(result)
}
fun task2() {
    val lines = File("input1.txt").readLines().filter { it.isNotBlank() }

    val matrix = lines.map { line ->
        line.trim().split(" ").map { it.toDouble() }
    }

    val newMatrix = matrix.map { row ->
        row.map { value ->
            val intPart = value.toInt()
            if (intPart % 2 == 0) value - intPart else value
        }
    }

    val result = newMatrix.joinToString("\n") { row ->
        row.joinToString(" ") { "%.3f".format(it) }
    }

    File("output1.txt").writeText(result)
}
