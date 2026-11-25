package tj.soni.englishgame.data

import android.content.Context

object LearnedWordsStore {

    private const val PREFS = "learned_words"

    fun getLearnedIds(context: Context): Set<String> {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet("ids", emptySet())
            ?.toSet() ?: emptySet()
    }

    fun toggle(context: Context, wordId: String): Set<String> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val updated = getLearnedIds(context).toMutableSet()

        if (wordId in updated) {
            updated.remove(wordId)
        } else {
            updated.add(wordId)
        }

        prefs.edit().putStringSet("ids", updated).apply()
        return updated
    }
}
