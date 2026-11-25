package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Colors {
    val category = Category(
        id = "colors",
        title = "Рангҳо ва шаклҳо",
        englishTitle = "Colors & Shapes",
        emoji = "🎨",
        description = "Рангҳо ва шаклҳои гуногун",
        words = listOf(
            Word("color_1", "Red", "Сурх", "Ред", "The apple is red.", "Себ сурх аст."),
            Word("color_2", "Blue", "Кабуд", "Блу", "The sky is blue.", "Осмон кабуд аст."),
            Word("color_3", "Green", "Сабз", "Грин", "The grass is green.", "Алаф сабз аст."),
            Word("color_4", "Yellow", "Зард", "Йелоу", "The sun is yellow.", "Офтоб зард аст."),
            Word("color_5", "Black", "Сиёҳ", "Блэк", "My bag is black.", "Халтаи ман сиёҳ аст."),
            Word("color_6", "White", "Сафед", "Уайт", "Snow is white.", "Барф сафед аст."),
            Word("color_7", "Circle", "Доира", "Сёркл", "Draw a circle.", "Доира каш."),
            Word("color_8", "Square", "Мураббаъ", "Сквеэр", "This is a square.", "Ин мураббаъ аст."),
            Word("color_9", "Triangle", "Секунҷа", "Трайэнгл", "A triangle has three sides.", "Секунҷа се тараф дорад."),
            Word("color_10", "Rectangle", "Росткунҷа", "Ректэнгл", "Draw a rectangle.", "Росткунҷа каш.")
        )
    )
}
