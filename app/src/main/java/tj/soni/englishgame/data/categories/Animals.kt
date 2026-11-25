package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Animals {
    val category = Category(
        id = "animals",
        title = "Ҳайвонот",
        englishTitle = "Animals",
        emoji = "🐾",
        description = "Номи ҳайвонотро омӯз",
        words = listOf(
            Word("animal_1", "Cat", "Гурба", "Кэт", "I have a cat.", "Ман гурба дорам."),
            Word("animal_2", "Dog", "Саг", "Дог", "The dog is big.", "Саг калон аст."),
            Word("animal_3", "Lion", "Шер", "Лайэн", "The lion is strong.", "Шер пурқувват аст."),
            Word("animal_4", "Tiger", "Паланг", "Тайгэр", "The tiger can run.", "Паланг давида метавонад."),
            Word("animal_5", "Elephant", "Фил", "Элефэнт", "The elephant is big.", "Фил калон аст."),
            Word("animal_6", "Horse", "Асп", "Ҳорс", "I can ride a horse.", "Ман аспсаворӣ карда метавонам."),
            Word("animal_7", "Cow", "Гов", "Кау", "The cow gives milk.", "Гов шир медиҳад."),
            Word("animal_8", "Bird", "Парранда", "Бёрд", "The bird can fly.", "Парранда парвоз карда метавонад."),
            Word("animal_9", "Fish", "Моҳӣ", "Фиш", "Fish live in water.", "Моҳиҳо дар об зиндагӣ мекунанд."),
            Word("animal_10", "Rabbit", "Харгӯш", "Рабит", "The rabbit is fast.", "Харгӯш тез аст.")
        )
    )
}
