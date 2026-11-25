package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Nature {
    val category = Category(
        id = "nature",
        title = "Табиат ва обу ҳаво",
        englishTitle = "Nature & Weather",
        emoji = "🌦️",
        description = "Табиат, фаслҳо ва ҳаво",
        words = listOf(
            Word("nature_1", "Sun", "Офтоб", "Сан", "The sun is bright.", "Офтоб равшан аст."),
            Word("nature_2", "Moon", "Моҳ", "Мун", "The moon is beautiful.", "Моҳ зебо аст."),
            Word("nature_3", "Star", "Ситора", "Стар", "I see a star.", "Ман ситораро мебинам."),
            Word("nature_4", "Sky", "Осмон", "Скай", "The sky is blue.", "Осмон кабуд аст."),
            Word("nature_5", "Cloud", "Абр", "Клауд", "A cloud is white.", "Абр сафед аст."),
            Word("nature_6", "Rain", "Борон", "Рейн", "It is raining.", "Борон меборад."),
            Word("nature_7", "Snow", "Барф", "Сноу", "Snow is white.", "Барф сафед аст."),
            Word("nature_8", "Wind", "Шамол", "Уинд", "The wind is cold.", "Шамол хунук аст."),
            Word("nature_9", "Tree", "Дарахт", "Три", "The tree is tall.", "Дарахт баланд аст."),
            Word("nature_10", "Flower", "Гул", "Флауэр", "This flower is beautiful.", "Ин гул зебо аст.")
        )
    )
}
