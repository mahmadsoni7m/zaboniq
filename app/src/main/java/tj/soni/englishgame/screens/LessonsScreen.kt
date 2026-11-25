package tj.soni.englishgame.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.LearnedWordsStore
import tj.soni.englishgame.data.WordCatalog
import tj.soni.englishgame.ui.AnimatedBar
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.floating
import tj.soni.englishgame.ui.staggerIn

@Composable
fun LessonsScreen(onCategoryClick: (Category) -> Unit) {
    val context = LocalContext.current
    val learnedIds = remember { LearnedWordsStore.getLearnedIds(context) }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(Modifier.staggerIn(0), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Дарсҳои англисӣ", color = Z.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                Text(
                    "${WordCatalog.categories.size} категория • ${WordCatalog.allWords.size} калима",
                    color = Z.Muted,
                    fontSize = 14.sp
                )
            }
        }

        itemsIndexed(WordCatalog.categories, key = { _, c -> c.id }) { index, category ->
            val colors = Z.categoryGradients[index % Z.categoryGradients.size]
            val learned = category.words.count { it.id in learnedIds }
            val total = category.words.size

            GlassCard(
                modifier = Modifier.staggerIn(index + 1),
                tint = colors[0],
                corner = 22.dp,
                onClick = { onCategoryClick(category) }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(colors)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(category.englishTitle.take(1).uppercase(), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                    }
                    Column(Modifier.weight(1f).padding(start = 14.dp)) {
                        Text(category.title, color = Z.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text(category.englishTitle, color = Z.Muted, fontSize = 13.sp)
                    }
                    Text("›", color = Z.Muted, fontSize = 26.sp)
                }
                AnimatedBar(
                    progress = if (total == 0) 0f else learned.toFloat() / total,
                    colors = colors,
                    height = 7.dp
                )
                Text("$learned / $total калима", color = Z.Muted, fontSize = 12.sp)
            }
        }
    }
}
