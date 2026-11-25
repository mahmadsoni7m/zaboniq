package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Sports {
    val category = Category(
        id = "sports",
        title = "Варзиш ва ҳаракат",
        englishTitle = "Sports & Actions",
        emoji = "⚽",
        description = "Варзиш ва амалҳои ҳаррӯза",
        words = listOf(
            Word("sport_1", "Football", "Футбол", "Футбол", "I play football.", "Ман футбол бозӣ мекунам."),
            Word("sport_2", "Basketball", "Баскетбол", "Баскетбол", "He plays basketball.", "Ӯ баскетбол бозӣ мекунад."),
            Word("sport_3", "Swimming", "Шиноварӣ", "Свиминг", "Swimming is fun.", "Шиноварӣ шавқовар аст."),
            Word("sport_4", "Running", "Давидан", "Ранинг", "I like running.", "Ман давиданро дӯст медорам."),
            Word("sport_5", "Walking", "Роҳгардӣ", "Уокинг", "We are walking.", "Мо роҳ мегардем."),
            Word("sport_6", "Jump", "Ҷаҳидан", "Ҷамп", "Jump high.", "Баланд ҷаҳ."),
            Word("sport_7", "Play", "Бозӣ кардан", "Плей", "Children play outside.", "Кӯдакон дар берун бозӣ мекунанд."),
            Word("sport_8", "Read", "Хондан", "Рид", "I read every day.", "Ман ҳар рӯз мехонам."),
            Word("sport_9", "Write", "Навиштан", "Райт", "Write your name.", "Номатро навис."),
            Word("sport_10", "Dance", "Рақс кардан", "Дэнс", "They dance together.", "Онҳо якҷоя рақс мекунанд.")
        )
    )
}
