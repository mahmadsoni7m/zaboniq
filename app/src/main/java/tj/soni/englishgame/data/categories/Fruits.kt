package tj.soni.englishgame.data.categories

import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.Word

object Fruits {
    val category = Category(
        id = "fruits",
        title = "Меваҳо",
        englishTitle = "Fruits",
        emoji = "🍎",
        description = "Номи меваҳоро омӯз",
        words = listOf(
            Word("fruit_1", "Apple", "Себ", "Эпл", "I eat an apple.", "Ман себ мехӯрам."),
            Word("fruit_2", "Banana", "Банан", "Банана", "The banana is yellow.", "Банан зард аст."),
            Word("fruit_3", "Orange", "Афлесун", "Оринҷ", "I like orange juice.", "Ман шарбати афлесунро дӯст медорам."),
            Word("fruit_4", "Grape", "Ангур", "Грейп", "Grapes are sweet.", "Ангур ширин аст."),
            Word("fruit_5", "Watermelon", "Тарбуз", "Вотэрмелон", "The watermelon is big.", "Тарбуз калон аст."),
            Word("fruit_6", "Melon", "Харбуза", "Мелон", "This melon is sweet.", "Ин харбуза ширин аст."),
            Word("fruit_7", "Peach", "Шафтолу", "Пич", "The peach is soft.", "Шафтолу мулоим аст."),
            Word("fruit_8", "Pear", "Нок", "Пеэр", "I have a pear.", "Ман як нок дорам."),
            Word("fruit_9", "Cherry", "Гелос", "Черӣ", "The cherry is red.", "Гелос сурх аст."),
            Word("fruit_10", "Strawberry", "Тути заминӣ", "Строберӣ", "I like strawberries.", "Ман тути заминиро дӯст медорам.")
        )
    )
}
