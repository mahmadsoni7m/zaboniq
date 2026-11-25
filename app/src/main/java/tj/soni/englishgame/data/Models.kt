package tj.soni.englishgame.data

data class Word(
    val id: String,
    val english: String,
    val tajik: String,
    val pronunciation: String,
    val sentenceEn: String,
    val sentenceTg: String
)

data class Category(
    val id: String,
    val title: String,
    val englishTitle: String,
    val emoji: String,
    val description: String,
    val words: List<Word>
)
