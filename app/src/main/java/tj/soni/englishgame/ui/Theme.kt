package tj.soni.englishgame.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object Z {
    val Bg = Color(0xFF0C0B16)
    val Panel = Color(0xFF19172A)
    val Card = Color(0xFF211E38)
    val Purple = Color(0xFF9B72FF)
    val Violet = Color(0xFF6C4BFF)
    val Mint = Color(0xFF54E3B3)
    val Pink = Color(0xFFFF6FB1)
    val Yellow = Color(0xFFFFD52A)
    val Red = Color(0xFFFF4D6D)
    val Blue = Color(0xFF4D8DFF)
    val Orange = Color(0xFFFF9F43)
    val White = Color(0xFFF8F6FF)
    val Muted = Color(0xFFAAA5BE)

    val categoryGradients = listOf(
        listOf(Color(0xFF9B72FF), Color(0xFF6C4BFF)),
        listOf(Color(0xFF54E3B3), Color(0xFF1FA67A)),
        listOf(Color(0xFFFF6FB1), Color(0xFFD33F86)),
        listOf(Color(0xFF4D8DFF), Color(0xFF2A5BD7)),
        listOf(Color(0xFFFF9F43), Color(0xFFE0701A))
    )
}

@Composable
fun ZaboniqTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Z.Purple,
            secondary = Z.Mint,
            background = Z.Bg,
            surface = Z.Panel,
            onSurface = Z.White,
            onBackground = Z.White
        ),
        content = content
    )
}
