package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object BasicWords {
    val category = Category(
        id = "basic_words",
        title = "Калимаҳои муҳим",
        englishTitle = "Essential Words",
        emoji = "📚",
        description = "Калимаҳои зарурӣ барои гуфтугӯ",
        words = listOf(
            Word("basic_1", "Hello", "Салом", "Ҳэлоу", "Hello, my friend!", "Салом, дӯсти ман!"),
            Word("basic_2", "Goodbye", "Хайр", "Гудбай", "Goodbye, see you soon.", "Хайр, ба зудӣ мебинем."),
            Word("basic_3", "Please", "Лутфан", "Плиз", "Please help me.", "Лутфан, ба ман ёрӣ деҳ."),
            Word("basic_4", "Thank you", "Ташаккур", "Сэнк ю", "Thank you very much.", "Ташаккури зиёд."),
            Word("basic_5", "Sorry", "Бубахшед", "Сори", "I am sorry.", "Маро бубахшед."),
            Word("basic_6", "Yes", "Ҳа", "Йес", "Yes, I understand.", "Ҳа, ман мефаҳмам."),
            Word("basic_7", "No", "Не", "Ноу", "No, thank you.", "Не, ташаккур."),
            Word("basic_8", "Help", "Ёрӣ", "Ҳелп", "Can you help me?", "Метавонӣ ба ман ёрӣ диҳӣ?"),
            Word("basic_9", "Beautiful", "Зебо", "Бютэфул", "This place is beautiful.", "Ин ҷой зебо аст."),
            Word("basic_10", "Important", "Муҳим", "Импортэнт", "English is important.", "Забони англисӣ муҳим аст.")
        )
    )
}
