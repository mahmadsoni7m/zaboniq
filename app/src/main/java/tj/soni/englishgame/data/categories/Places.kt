package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Places {
    val category = Category(
        id = "places",
        title = "Ҷойҳо ва биноҳо",
        englishTitle = "Places & Buildings",
        emoji = "🏙️",
        description = "Ҷойҳои муҳим ва биноҳо",
        words = listOf(
            Word("place_1", "School", "Мактаб", "Скул", "I go to school.", "Ман ба мактаб меравам."),
            Word("place_2", "Hospital", "Беморхона", "Ҳоспитэл", "The hospital is near.", "Беморхона наздик аст."),
            Word("place_3", "Market", "Бозор", "Маркет", "We go to the market.", "Мо ба бозор меравем."),
            Word("place_4", "Shop", "Мағоза", "Шоп", "The shop is open.", "Мағоза кушода аст."),
            Word("place_5", "Park", "Боғ", "Парк", "Children play in the park.", "Кӯдакон дар боғ бозӣ мекунанд."),
            Word("place_6", "Library", "Китобхона", "Лайбрэри", "I read in the library.", "Ман дар китобхона мехонам."),
            Word("place_7", "Bank", "Бонк", "Бэнк", "The bank is closed.", "Бонк баста аст."),
            Word("place_8", "Restaurant", "Тарабхона", "Рестронт", "We eat at a restaurant.", "Мо дар тарабхона хӯрок мехӯрем."),
            Word("place_9", "Airport", "Фурудгоҳ", "Эйрпорт", "The airport is busy.", "Фурудгоҳ серодам аст."),
            Word("place_10", "Hotel", "Меҳмонхона", "Ҳоутел", "We stay at a hotel.", "Мо дар меҳмонхона меистем.")
        )
    )
}
