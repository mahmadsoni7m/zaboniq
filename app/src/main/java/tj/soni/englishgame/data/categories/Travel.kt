package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Travel {
    val category = Category(
        id = "travel",
        title = "Сафар ва кишварҳо",
        englishTitle = "Travel & Countries",
        emoji = "🌍",
        description = "Сафар, кишварҳо ва забонҳо",
        words = listOf(
            Word("travel_1", "Country", "Кишвар", "Кантри", "Tajikistan is my country.", "Тоҷикистон кишвари ман аст."),
            Word("travel_2", "City", "Шаҳр", "Сити", "Dushanbe is a city.", "Душанбе шаҳр аст."),
            Word("travel_3", "Village", "Деҳа", "Вилиҷ", "My village is beautiful.", "Деҳаи ман зебо аст."),
            Word("travel_4", "Passport", "Шиноснома", "Паспорт", "I have a passport.", "Ман шиноснома дорам."),
            Word("travel_5", "Ticket", "Чипта", "Тикит", "Buy a ticket.", "Чипта харид кун."),
            Word("travel_6", "Journey", "Сафар", "Ҷёрни", "Have a nice journey.", "Сафари хуб дошта бош."),
            Word("travel_7", "Map", "Харита", "Мэп", "Look at the map.", "Ба харита нигоҳ кун."),
            Word("travel_8", "Hotel", "Меҳмонхона", "Ҳоутел", "The hotel is clean.", "Меҳмонхона тоза аст."),
            Word("travel_9", "Language", "Забон", "Лэнгвиҷ", "English is a language.", "Забони англисӣ як забон аст."),
            Word("travel_10", "Airport", "Фурудгоҳ", "Эйрпорт", "We are at the airport.", "Мо дар фурудгоҳ ҳастем.")
        )
    )
}
