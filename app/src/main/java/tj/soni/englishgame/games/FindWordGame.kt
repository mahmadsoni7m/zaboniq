package tj.soni.englishgame.games

import tj.soni.englishgame.data.Word

data class GameQuestion(
    val word: Word,
    val question: String,
    val correctAnswer: String,
    val options: List<String>,
    val englishToTajik: Boolean
)

object FindWordGame {

    fun createQuestions(
        words: List<Word>,
        count: Int = 10
    ): List<GameQuestion> {
        val pool = words.shuffled()

        return pool.take(count.coerceAtMost(pool.size)).map { word ->
            val direction = listOf(true, false).random()

            val correct = if (direction) word.tajik else word.english

            val wrongAnswers = pool
                .filter { it.id != word.id }
                .map { if (direction) it.tajik else it.english }
                .distinct()
                .filter { it != correct }
                .shuffled()
                .take(3)

            val options = (wrongAnswers + correct).shuffled()

            GameQuestion(
                word = word,
                question = if (direction) word.english else word.tajik,
                correctAnswer = correct,
                options = options,
                englishToTajik = direction
            )
        }
    }
}
