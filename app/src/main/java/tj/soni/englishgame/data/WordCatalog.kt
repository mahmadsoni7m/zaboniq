package tj.soni.englishgame.data

import tj.soni.englishgame.data.categories.*

object WordCatalog {

    val categories: List<Category> = listOf(
        Animals.category,
        Fruits.category,
        Vegetables.category,
        People.category,
        Food.category,
        Transport.category,
        School.category,
        Nature.category,
        Colors.category,
        Sports.category,
        Jobs.category,
        Time.category,
        Feelings.category,
        Places.category,
        Technology.category,
        Travel.category,
        Body.category,
        DailyActions.category,
        BasicWords.category
    )

    val allWords: List<Word>
        get() = categories.flatMap { it.words }

    fun findWord(id: String): Word? {
        return allWords.find { it.id == id }
    }

    fun findCategory(id: String): Category? {
        return categories.find { it.id == id }
    }
}
