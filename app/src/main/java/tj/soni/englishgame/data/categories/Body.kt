package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Body {
    val category = Category(
        id = "body",
        title = "Бадан ва саломатӣ",
        englishTitle = "Body & Health",
        emoji = "💪",
        description = "Қисмҳои бадан ва саломатӣ",
        words = listOf(
            Word("body_1", "Head", "Сар", "Ҳед", "My head hurts.", "Сари ман дард мекунад."),
            Word("body_2", "Eye", "Чашм", "Ай", "I have two eyes.", "Ман ду чашм дорам."),
            Word("body_3", "Ear", "Гӯш", "Ир", "My ear is small.", "Гӯши ман хурд аст."),
            Word("body_4", "Nose", "Бинӣ", "Ноуз", "Touch your nose.", "Ба биниат даст расон."),
            Word("body_5", "Mouth", "Даҳон", "Маус", "Open your mouth.", "Даҳонатро кушо."),
            Word("body_6", "Hand", "Даст", "Ҳэнд", "Wash your hands.", "Дастҳоятро бишӯй."),
            Word("body_7", "Foot", "Пой", "Фут", "My foot is cold.", "Пои ман хунук аст."),
            Word("body_8", "Heart", "Дил", "Ҳарт", "The heart pumps blood.", "Дил хунро мегардонад."),
            Word("body_9", "Doctor", "Духтур", "Доктэр", "The doctor helps me.", "Духтур ба ман ёрӣ медиҳад."),
            Word("body_10", "Medicine", "Дору", "Медисин", "Take your medicine.", "Доруятро истеъмол кун.")
        )
    )
}
