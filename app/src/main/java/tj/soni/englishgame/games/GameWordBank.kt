package tj.soni.englishgame.games

import tj.soni.englishgame.data.Word
import tj.soni.englishgame.data.WordCatalog

object GameWordBank {

    fun getAllWords(): List<Word> {
        return WordCatalog.allWords.shuffled()
    }

    fun getRandomWords(count: Int): List<Word> {
        return getAllWords().take(count.coerceAtMost(WordCatalog.allWords.size))
    }

    fun getRandomWord(): Word {
        return WordCatalog.allWords.random()
    }
}
