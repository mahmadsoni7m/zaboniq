package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Time {
    val category = Category(
        id = "time",
        title = "Вақт ва рӯзҳо",
        englishTitle = "Time & Days",
        emoji = "⏰",
        description = "Рӯзҳои ҳафта ва вақт",
        words = listOf(
            Word("time_1", "Monday", "Душанбе", "Мандей", "Today is Monday.", "Имрӯз Душанбе аст."),
            Word("time_2", "Tuesday", "Сешанбе", "Тюздей", "I work on Tuesday.", "Ман рӯзи сешанбе кор мекунам."),
            Word("time_3", "Wednesday", "Чоршанбе", "Уэнздей", "Wednesday is a busy day.", "Чоршанбе рӯзи серкор аст."),
            Word("time_4", "Thursday", "Панҷшанбе", "Тёрздей", "See you on Thursday.", "Рӯзи панҷшанбе мебинем."),
            Word("time_5", "Friday", "Ҷумъа", "Фрайдей", "Friday is my favorite day.", "Ҷумъа рӯзи дӯстдоштаи ман аст."),
            Word("time_6", "Saturday", "Шанбе", "Сатэрдей", "We rest on Saturday.", "Мо рӯзи шанбе истироҳат мекунем."),
            Word("time_7", "Sunday", "Якшанбе", "Сандей", "Sunday is a holiday.", "Якшанбе рӯзи истироҳат аст."),
            Word("time_8", "Morning", "Субҳ", "Морнинг", "Good morning!", "Субҳ ба хайр!"),
            Word("time_9", "Evening", "Бегоҳ", "Ивнинг", "Good evening!", "Шом ба хайр!"),
            Word("time_10", "Night", "Шаб", "Найт", "Good night!", "Шаб ба хайр!")
        )
    )
}
