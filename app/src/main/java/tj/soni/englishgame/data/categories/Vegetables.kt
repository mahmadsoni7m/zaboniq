package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Vegetables {
    val category = Category(
        id = "vegetables",
        title = "Сабзавот",
        englishTitle = "Vegetables",
        emoji = "🥕",
        description = "Номи сабзавотҳоро омӯз",
        words = listOf(
            Word("veg_1", "Carrot", "Сабзӣ", "Кэрэт", "A carrot is orange.", "Сабзӣ норанҷӣ аст."),
            Word("veg_2", "Potato", "Картошка", "Потейто", "I like potatoes.", "Ман картошкаро дӯст медорам."),
            Word("veg_3", "Tomato", "Помидор", "Томейто", "The tomato is red.", "Помидор сурх аст."),
            Word("veg_4", "Cucumber", "Бодиринг", "Кюкамбэр", "The cucumber is green.", "Бодиринг сабз аст."),
            Word("veg_5", "Onion", "Пиёз", "Анйэн", "I cut an onion.", "Ман пиёзро реза мекунам."),
            Word("veg_6", "Garlic", "Сир", "Гарлик", "Garlic has a strong smell.", "Сир бӯи тез дорад."),
            Word("veg_7", "Cabbage", "Карам", "Кэбиҷ", "Cabbage is healthy.", "Карам фоиданок аст."),
            Word("veg_8", "Pepper", "Қаламфур", "Пепэр", "This pepper is hot.", "Ин қаламфур тез аст."),
            Word("veg_9", "Eggplant", "Бодинҷон", "Эгплэнт", "The eggplant is purple.", "Бодинҷон бунафш аст."),
            Word("veg_10", "Pumpkin", "Каду", "Пампкин", "The pumpkin is big.", "Каду калон аст.")
        )
    )
}
