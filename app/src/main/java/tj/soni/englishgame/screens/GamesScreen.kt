package tj.soni.englishgame.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.GradientButton
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.floating
import tj.soni.englishgame.ui.staggerIn

@Composable
fun GamesScreen(onSelect: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(Modifier.staggerIn(0), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("МАРКАЗИ БОЗИҲО", color = Z.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
            Text("Бозии дӯстдоштаатро интихоб кун!", color = Z.Muted, fontSize = 14.sp)
        }

        GameCard(
            index = 1,
            icon = Icons.Rounded.Search,
            title = "Калимаёбака",
            description = "Тарҷумаи дурусти калимаро ёб!",
            tint = Z.Blue,
            colors = listOf(Z.Blue, Color(0xFF2A5BD7)),
            onPlay = { onSelect(1) }
        )
        GameCard(
            index = 2,
            icon = Icons.Rounded.Extension,
            title = "Ҳарфҳоро ҷамъ кун",
            description = "Аз ҳарфҳои пароканда калима соз!",
            tint = Z.Purple,
            colors = listOf(Z.Purple, Z.Violet),
            onPlay = { onSelect(2) }
        )
        GameCard(
            index = 3,
            icon = Icons.Rounded.Bolt,
            title = "Калимашикор",
            description = "Дар 60 сония то ҳадди имкон зиёд калима ҷамъ кун!",
            tint = Z.Orange,
            colors = listOf(Z.Orange, Color(0xFFE0701A)),
            onPlay = { onSelect(3) }
        )
    }
}

@Composable
private fun GameCard(
    index: Int,
    icon: ImageVector,
    title: String,
    description: String,
    tint: Color,
    colors: List<Color>,
    onPlay: () -> Unit
) {
    GlassCard(
        modifier = Modifier.staggerIn(index),
        tint = tint,
        corner = 28.dp,
        onClick = onPlay
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                Modifier.size(64.dp).floating(6f, 1800 + index * 250).clip(CircleShape).background(Brush.linearGradient(colors)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(34.dp))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, color = Z.White, fontSize = 21.sp, fontWeight = FontWeight.Black)
                Text(description, color = Z.Muted, fontSize = 13.sp)
            }
        }
        GradientButton("▶ Бозиро оғоз кун", onPlay, colors = colors)
    }
}
