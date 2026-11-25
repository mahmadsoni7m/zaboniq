package tj.soni.englishgame.games

import tj.soni.englishgame.data.Word

data class BuildWordQuestion(
    val word: Word,
    val letters: List<Char>
)

object BuildWordGame {

    private val extraLetters = ('A'..'Z').toList()

    fun createQuestions(
        words: List<Word>,
        count: Int = 10
    ): List<BuildWordQuestion> {
        return words
            .filter {
                it.english.length in 2..12 &&
                it.english.all { char -> char.isLetter() }
            }
            .distinctBy { it.id }
            .shuffled()
            .take(count)
            .map { word ->
                val answerLetters = word.english.uppercase().toList()
                val availableExtras = extraLetters
                    .filter { it !in answerLetters }
                    .shuffled()
                    .take(3)

                BuildWordQuestion(
                    word = word,
                    letters = (answerLetters + availableExtras).shuffled()
                )
            }
    }
}
