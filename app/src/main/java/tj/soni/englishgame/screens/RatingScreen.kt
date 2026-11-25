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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.soni.englishgame.data.LearnedWordsStore
import tj.soni.englishgame.data.WordCatalog
import tj.soni.englishgame.ui.AnimatedBar
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.ProgressRing
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.staggerIn

private data class Badge(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val unlocked: Boolean
)

@Composable
fun RatingScreen(xp: Int) {
    val context = LocalContext.current
    val learned = remember { LearnedWordsStore.getLearnedIds(context).size }
    val total = WordCatalog.allWords.size
    val remaining = (total - learned).coerceAtLeast(0)
    val level = xp / 100 + 1
    val shownXp by animateIntAsState(xp, tween(1000), label = "ratingXp")

    val levelName = when {
        level <= 2 -> "Навомӯз"
        level <= 4 -> "Омӯзанда"
        level <= 7 -> "Моҳир"
        else -> "Устод"
    }

    val badges = listOf(
        Badge(Icons.Rounded.PlayArrow, "Оғоз", "1 калима азёд кун", learned >= 1),
        Badge(Icons.Rounded.MenuBook, "10 калима", "10 калима азёд кун", learned >= 10),
        Badge(Icons.Rounded.Whatshot, "50 калима", "50 калима азёд кун", learned >= 50),
        Badge(Icons.Rounded.WorkspacePremium, "100 калима", "100 калима азёд кун", learned >= 100),
        Badge(Icons.Rounded.Star, "500 XP", "500 XP ҷамъ кун", xp >= 500),
        Badge(Icons.Rounded.EmojiEvents, "Сатҳи 5", "Ба сатҳи 5 бирас", level >= 5)
    )

    val glow = rememberInfiniteTransition(label = "glow")
    val glowScale by glow.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(tween(1600, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glowScale"
    )

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Рейтинги ман",
            color = Z.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.staggerIn(0)
        )

        GlassCard(modifier = Modifier.staggerIn(1), tint = Z.Purple, corner = 28.dp) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProgressRing(
                    progress = (xp % 100) / 100f,
                    size = 150.dp,
                    strokeWidth = 12.dp,
                    colors = listOf(Z.Yellow, Z.Orange),
                    modifier = Modifier.graphicsLayer {
                        scaleX = glowScale
                        scaleY = glowScale
                    }
                ) {
                    Icon(Icons.Rounded.School, contentDescription = null, tint = Z.Yellow, modifier = Modifier.size(64.dp))
                }
                Text("Сатҳи $level • $levelName", color = Z.Purple, fontSize = 22.sp, fontWeight = FontWeight.Black)
                Text("$shownXp XP", color = Z.Mint, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                AnimatedBar(
                    progress = (xp % 100) / 100f,
                    colors = listOf(Z.Yellow, Z.Orange)
                )
                Text("${xp % 100} / 100 XP то сатҳи нав", color = Z.Muted, fontSize = 13.sp)
            }
        }

        GlassCard(modifier = Modifier.staggerIn(2), tint = Z.Mint) {
            Text("Пешрафти калимаҳо", color = Z.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            AnimatedBar(
                progress = if (total == 0) 0f else learned.toFloat() / total,
                colors = listOf(Z.Mint, Z.Purple)
            )
            Row(Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Text("Азёдшуда: $learned", color = Z.Mint, fontWeight = FontWeight.Bold)
                Text("Боқимонда: $remaining", color = Z.Muted)
            }
            Text("Ҳамагӣ: $total калима • ${WordCatalog.categories.size} категория", color = Z.White, fontSize = 13.sp)
        }

        Text(
            "Дастовардҳо",
            color = Z.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.staggerIn(3)
        )

        badges.chunked(2).forEachIndexed { rowIndex, pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                pair.forEachIndexed { colIndex, badge ->
                    BadgeCard(
                        badge = badge,
                        modifier = Modifier.weight(1f).staggerIn(4 + rowIndex * 2 + colIndex)
                    )
                }
            }
        }

        Text(
            "ZABONIQ • VERSION 2.1",
            color = Z.Muted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )
    }
}

@Composable
private fun BadgeCard(badge: Badge, modifier: Modifier) {
    GlassCard(
        modifier = modifier.graphicsLayer { alpha = if (badge.unlocked) 1f else 0.42f },
        tint = if (badge.unlocked) Z.Yellow else Color.White,
        corner = 20.dp
    ) {
        Icon(if (badge.unlocked) badge.icon else Icons.Rounded.Lock, contentDescription = null, tint = if (badge.unlocked) Z.Yellow else Z.Muted, modifier = Modifier.size(32.dp))
        Text(badge.title, color = Z.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Text(badge.description, color = Z.Muted, fontSize = 11.sp)
    }
}
