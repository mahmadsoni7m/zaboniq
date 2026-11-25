package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object DailyActions {
    val category = Category(
        id = "daily_actions",
        title = "Амалҳои ҳаррӯза",
        englishTitle = "Daily Actions",
        emoji = "🏃",
        description = "Феълҳои муҳим барои гуфтугӯ",
        words = listOf(
            Word("action_1", "Wake up", "Аз хоб хестан", "Уэйк ап", "I wake up early.", "Ман барвақт аз хоб мехезам."),
            Word("action_2", "Eat", "Хӯрдан", "Ит", "I eat breakfast.", "Ман наҳорӣ мехӯрам."),
            Word("action_3", "Drink", "Нӯшидан", "Дринк", "Drink some water.", "Каме об бинӯш."),
            Word("action_4", "Sleep", "Хобидан", "Слип", "I sleep at night.", "Ман шабона мехобам."),
            Word("action_5", "Go", "Рафтан", "Гоу", "I go to school.", "Ман ба мактаб меравам."),
            Word("action_6", "Come", "Омадан", "Кам", "Come here, please.", "Лутфан, ба ин ҷо биё."),
            Word("action_7", "Speak", "Гап задан", "Спик", "I speak English.", "Ман бо забони англисӣ гап мезанам."),
            Word("action_8", "Listen", "Гӯш кардан", "Лисэн", "Listen to me.", "Ба ман гӯш кун."),
            Word("action_9", "Learn", "Омӯхтан", "Лёрн", "I learn English.", "Ман забони англисиро меомӯзам."),
            Word("action_10", "Work", "Кор кардан", "Уорк", "I work every day.", "Ман ҳар рӯз кор мекунам.")
        )
    )
}
