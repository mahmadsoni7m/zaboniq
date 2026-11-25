package tj.soni.englishgame.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.soni.englishgame.data.Category
import tj.soni.englishgame.data.LearnedWordsStore
import tj.soni.englishgame.data.Word
import tj.soni.englishgame.data.WordCatalog
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.GradientButton
import tj.soni.englishgame.ui.ProgressRing
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.staggerIn

@Composable
fun HomeScreen(
    xp: Int,
    onStartLearning: () -> Unit,
    onOpenGames: () -> Unit,
    onOpenCategory: (Category) -> Unit
) {
    val context = LocalContext.current
    val learned = remember { LearnedWordsStore.getLearnedIds(context).size }
    val total = WordCatalog.allWords.size
    val percent = if (total == 0) 0 else learned * 100 / total
    val shownPercent by animateIntAsState(percent, tween(1100), label = "percent")

    var query by rememberSaveable { mutableStateOf("") }
    val results = remember(query) {
        val q = query.trim()
        if (q.isEmpty()) {
            emptyList<Pair<Category, Word>>()
        } else {
            WordCatalog.categories.flatMap { category ->
                category.words.filter { word ->
                    listOf(
                        word.english,
                        word.tajik,
                        word.pronunciation,
                        word.sentenceEn,
                        word.sentenceTg
                    ).any { it.contains(q, ignoreCase = true) }
                }.map { word -> category to word }
            }
        }
    }

    val level = xp / 100 + 1

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth().staggerIn(0),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "ZABONIQ",
                        color = Z.Purple,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 3.sp,
                        fontSize = 13.sp
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Салом!", color = Z.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                    }
                    Text("Ҳар рӯз каме англисӣ омӯз!", color = Z.Muted, fontSize = 14.sp)
                }
                ProgressRing(
                    progress = (xp % 100) / 100f,
                    size = 66.dp,
                    strokeWidth = 6.dp,
                    colors = listOf(Z.Yellow, Z.Orange)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("LV", color = Z.Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text("$level", color = Z.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().staggerIn(1),
                label = { Text("Ҷустуҷӯи калима ё ҷумла...") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Z.Purple,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.16f),
                    focusedTextColor = Z.White,
                    unfocusedTextColor = Z.White,
                    cursorColor = Z.Mint,
                    focusedLabelColor = Z.Purple,
                    unfocusedLabelColor = Z.Muted,
                    focusedContainerColor = Color.White.copy(alpha = 0.06f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.04f)
                )
            )
        }

        if (query.isNotBlank()) {
            item {
                Text("Натиҷаҳо: ${results.size}", color = Z.Mint, fontWeight = FontWeight.Bold)
            }
            itemsIndexed(results.take(40)) { index, (category, word) ->
                GlassCard(
                    modifier = Modifier.staggerIn(index),
                    tint = Z.Purple,
                    onClick = { onOpenCategory(category) }
                ) {
                    Text(
                        "${word.english} — ${word.tajik}",
                        color = Z.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Талаффуз: ${word.pronunciation}", color = Z.Muted, fontSize = 13.sp)
                    Text(word.sentenceEn, color = Z.White, fontSize = 14.sp)
                    Text(word.sentenceTg, color = Z.Mint, fontSize = 14.sp)
                    Text("${category.title}  →", color = Z.Purple, fontSize = 13.sp)
                }
            }
        } else {
            item {
                GlassCard(modifier = Modifier.staggerIn(2), tint = Z.Purple, corner = 28.dp) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        ProgressRing(
                            progress = if (total == 0) 0f else learned.toFloat() / total,
                            size = 108.dp,
                            strokeWidth = 11.dp
                        ) {
                            Text(
                                "$shownPercent%",
                                color = Z.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Column(
                            Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("Ҳадафи имрӯз", color = Z.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                            Text("$learned / $total калима азёд шудааст", color = Z.Muted, fontSize = 13.sp)
                            Text("$xp XP", color = Z.Mint, fontWeight = FontWeight.Bold)
                        }
                    }
                    GradientButton("Оғози омӯзиш →", onStartLearning)
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickTile(
                        modifier = Modifier.weight(1f).staggerIn(3),
                        icon = Icons.Rounded.MenuBook,
                        title = "Калимаҳо",
                        subtitle = "${WordCatalog.categories.size} категория",
                        tint = Z.Blue,
                        onClick = onStartLearning
                    )
                    QuickTile(
                        modifier = Modifier.weight(1f).staggerIn(4),
                        icon = Icons.Rounded.SportsEsports,
                        title = "Бозиҳо",
                        subtitle = "3 бозии нав",
                        tint = Z.Pink,
                        onClick = onOpenGames
                    )
                }
            }

            item {
                Text(
                    "Бахшҳои барнома",
                    color = Z.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.staggerIn(5)
                )
            }
            item {
                InfoRow(Icons.Rounded.MenuBook, "Калимаҳои нав", "$total калима бо мисол ва талаффуз", Z.Purple, Modifier.staggerIn(6))
            }
            item {
                InfoRow(Icons.Rounded.SportsEsports, "Бозиҳои омӯзишӣ", "Тарҷума, ҳарфҳо ва калимашикор", Z.Mint, Modifier.staggerIn(7))
            }
            item {
                InfoRow(Icons.Rounded.EmojiEvents, "Дастовардҳо", "XP, сатҳҳо ва медалҳо", Z.Yellow, Modifier.staggerIn(8))
            }
        }
    }
}


@Composable
private fun QuickTile(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    tint: Color,
    onClick: () -> Unit
) {
    GlassCard(modifier = modifier, tint = tint, onClick = onClick) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(34.dp))
        Text(title, color = Z.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Text(subtitle, color = Z.Muted, fontSize = 12.sp)
    }
}

@Composable
private fun InfoRow(icon: ImageVector, title: String, description: String, tint: Color, modifier: Modifier) {
    GlassCard(modifier = modifier, tint = tint, corner = 20.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(46.dp).clip(CircleShape).background(tint.copy(alpha = 0.22f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
            }
            Column(Modifier.padding(start = 14.dp)) {
                Text(title, color = Z.White, fontWeight = FontWeight.Bold)
                Text(description, color = Z.Muted, fontSize = 12.sp)
            }
        }
    }
}
