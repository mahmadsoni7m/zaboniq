package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Food {
    val category = Category(
        id = "food",
        title = "Хӯрок ва нӯшокиҳо",
        englishTitle = "Food & Drinks",
        emoji = "🍽️",
        description = "Номи хӯрок ва нӯшокиҳо",
        words = listOf(
            Word("food_1", "Bread", "Нон", "Бред", "I eat bread.", "Ман нон мехӯрам."),
            Word("food_2", "Water", "Об", "Вотэр", "I drink water.", "Ман об менӯшам."),
            Word("food_3", "Milk", "Шир", "Милк", "Milk is white.", "Шир сафед аст."),
            Word("food_4", "Rice", "Биринҷ", "Райс", "We cook rice.", "Мо биринҷ мепазем."),
            Word("food_5", "Meat", "Гӯшт", "Мит", "I like meat.", "Ман гӯштро дӯст медорам."),
            Word("food_6", "Egg", "Тухм", "Эг", "I eat an egg.", "Ман тухм мехӯрам."),
            Word("food_7", "Cheese", "Панир", "Чиз", "The cheese is good.", "Панир хуб аст."),
            Word("food_8", "Tea", "Чой", "Ти", "I drink hot tea.", "Ман чойи гарм менӯшам."),
            Word("food_9", "Coffee", "Қаҳва", "Кофи", "He drinks coffee.", "Ӯ қаҳва менӯшад."),
            Word("food_10", "Juice", "Шарбат", "Ҷус", "The juice is sweet.", "Шарбат ширин аст.")
        )
    )
}
