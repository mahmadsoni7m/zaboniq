package tj.soni.englishgame.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import tj.soni.englishgame.ui.AnimatedBar
import tj.soni.englishgame.ui.BackPill
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.bounceClick
import tj.soni.englishgame.ui.staggerIn

@Composable
fun WordsScreen(category: Category, onBack: () -> Unit) {
    val context = LocalContext.current

    var learnedIds by remember(category.id) {
        mutableStateOf(LearnedWordsStore.getLearnedIds(context))
    }
    val orderedWords = remember(category, learnedIds) {
        category.words.sortedBy { it.id in learnedIds }
    }
    val listState = rememberLazyListState()
    var savedScroll by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    LaunchedEffect(learnedIds) {
        savedScroll?.let { (index, offset) ->
            listState.scrollToItem(index, offset)
            savedScroll = null
        }
    }
    val learnedCount = category.words.count { it.id in learnedIds }
    val total = category.words.size

    Column(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(start = 18.dp, end = 18.dp, top = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BackPill(onBack)
                Column(Modifier.padding(start = 14.dp)) {
                    Text(
                        category.title,
                        color = Z.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(category.englishTitle, color = Z.Muted, fontSize = 13.sp)
                }
            }
            AnimatedBar(
                progress = if (total == 0) 0f else learnedCount.toFloat() / total,
                colors = listOf(Z.Mint, Z.Purple)
            )
            Text(
                "Азёдшуда: $learnedCount • Боқимонда: ${total - learnedCount}",
                color = Z.Muted,
                fontSize = 13.sp
            )
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 6.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orderedWords, key = { it.id }) { word ->
                val learned = word.id in learnedIds
                WordCard(
                    word = word,
                    learned = learned,
                    onToggle = {
                        savedScroll = listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
                        learnedIds = LearnedWordsStore.toggle(context, word.id)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Composable
private fun WordCard(
    word: Word,
    learned: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember(word.id) { mutableStateOf(false) }

    GlassCard(
        modifier = modifier.animateContentSize(tween(300)),
        tint = if (learned) Z.Mint else Color.White,
        corner = 22.dp,
        onClick = { expanded = !expanded }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    word.english,
                    color = if (learned) Z.Mint else Z.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
                Text("🇹🇯 ${word.tajik}", color = Z.White, fontSize = 16.sp)
                Text("Талаффуз: ${word.pronunciation}", color = Z.Muted, fontSize = 12.sp)
            }
            CheckButton(learned = learned, onClick = onToggle)
        }

        AnimatedVisibility(visible = expanded) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                HorizontalDivider(color = Color.White.copy(alpha = 0.12f))
                Text("🇬🇧 ${word.sentenceEn}", color = Z.White, fontSize = 15.sp)
                Text("🇹🇯 ${word.sentenceTg}", color = Z.Mint, fontSize = 15.sp)
            }
        }

        Text(
            when {
                learned -> "Азёд шудааст"
                expanded -> "Барои азёд кардан тугмаи тикро пахш кун"
                else -> "Барои мисол пахш кун ↓"
            },
            color = if (learned) Z.Mint else Z.Muted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun CheckButton(learned: Boolean, onClick: () -> Unit) {
    val fill by animateColorAsState(
        if (learned) Z.Mint else Color.White.copy(alpha = 0.07f),
        tween(300),
        label = "checkFill"
    )
    val scale by animateFloatAsState(
        if (learned) 1.12f else 1f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 450f),
        label = "checkScale"
    )
    Box(
        Modifier
            .size(46.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .bounceClick(onClick = onClick)
            .clip(CircleShape)
            .background(fill)
            .border(1.5.dp, if (learned) Z.Mint else Color.White.copy(alpha = 0.25f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Rounded.Check,
            contentDescription = null,
            tint = if (learned) Z.Bg else Z.Muted,
            modifier = Modifier.size(26.dp)
        )
    }
}
