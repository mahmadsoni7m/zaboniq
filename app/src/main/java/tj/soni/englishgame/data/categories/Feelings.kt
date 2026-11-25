package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Feelings {
    val category = Category(
        id = "feelings",
        title = "Эҳсосот ва сифатҳо",
        englishTitle = "Feelings & Adjectives",
        emoji = "😊",
        description = "Эҳсосот ва хусусиятҳои одамон",
        words = listOf(
            Word("feel_1", "Happy", "Хушҳол", "Ҳэппи", "I am happy today.", "Ман имрӯз хушҳолам."),
            Word("feel_2", "Sad", "Ғамгин", "Сэд", "He is sad.", "Ӯ ғамгин аст."),
            Word("feel_3", "Angry", "Қаҳрнок", "Энгри", "She is angry.", "Ӯ қаҳрнок аст."),
            Word("feel_4", "Tired", "Хаста", "Тайэрд", "I am tired.", "Ман хастаам."),
            Word("feel_5", "Hungry", "Гурусна", "Ҳангри", "The boy is hungry.", "Писар гурусна аст."),
            Word("feel_6", "Thirsty", "Ташна", "Сёрсти", "I am thirsty.", "Ман ташнаам."),
            Word("feel_7", "Beautiful", "Зебо", "Бютэфул", "The flower is beautiful.", "Гул зебо аст."),
            Word("feel_8", "Strong", "Қавӣ", "Стронг", "My brother is strong.", "Бародари ман қавӣ аст."),
            Word("feel_9", "Clever", "Зирак", "Клэвэр", "The student is clever.", "Хонанда зирак аст."),
            Word("feel_10", "Kind", "Меҳрубон", "Кайнд", "My mother is kind.", "Модари ман меҳрубон аст.")
        )
    )
}
