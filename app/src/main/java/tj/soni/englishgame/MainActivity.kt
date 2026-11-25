package tj.soni.englishgame

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import android.app.Activity
import android.content.Context
import android.graphics.Color as AndroidColor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tj.soni.englishgame.data.Category
import tj.soni.englishgame.screens.BuildWordScreen
import tj.soni.englishgame.screens.FindWordScreen
import tj.soni.englishgame.screens.GamesScreen
import tj.soni.englishgame.screens.HomeScreen
import tj.soni.englishgame.screens.LessonsScreen
import tj.soni.englishgame.screens.RatingScreen
import tj.soni.englishgame.screens.WordHuntScreen
import tj.soni.englishgame.screens.WordsScreen
import tj.soni.englishgame.ui.AnimatedBackground
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.ZaboniqTheme
import tj.soni.englishgame.ui.bounceClick

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )
        setContent { GameApp() }
    }
}

private val navIcons = listOf(Icons.Rounded.Home, Icons.Rounded.MenuBook, Icons.Rounded.SportsEsports, Icons.Rounded.EmojiEvents)
private val navNames = listOf("Асосӣ", "Дарсҳо", "Бозиҳо", "Рейтинг")

@Composable
fun GameApp() {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
    }

    var xp by remember { mutableIntStateOf(prefs.getInt("xp", 0)) }
    var tab by rememberSaveable { mutableStateOf(0) }
    var selectedGame by rememberSaveable { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var lastBackPress by remember { mutableLongStateOf(0L) }

    val addXp: (Int) -> Unit = { earned ->
        xp += earned
        prefs.edit().putInt("xp", xp).apply()
    }

    BackHandler {
        when {
            selectedGame != 0 -> selectedGame = 0
            selectedCategory != null -> selectedCategory = null
            tab != 0 -> tab = 0
            else -> {
                val now = System.currentTimeMillis()
                if (now - lastBackPress < 2000L) {
                    (context as? Activity)?.finish()
                } else {
                    lastBackPress = now
                    Toast.makeText(
                        context,
                        "Барои баромадан бори дигар Назад-ро пахш кун",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    ZaboniqTheme {
        AnimatedBackground {
            Column(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    AnimatedContent(
                        targetState = tab,
                        transitionSpec = {
                            val dir = if (targetState > initialState) 1 else -1
                            (slideInHorizontally(tween(380)) { (it / 4) * dir } + fadeIn(tween(380))) togetherWith
                                (slideOutHorizontally(tween(260)) { -(it / 4) * dir } + fadeOut(tween(200)))
                        },
                        label = "tabs"
                    ) { currentTab ->
                        when (currentTab) {
                            0 -> HomeScreen(
                                xp = xp,
                                onStartLearning = { tab = 1 },
                                onOpenGames = { tab = 2 },
                                onOpenCategory = { category ->
                                    selectedCategory = category
                                    tab = 1
                                }
                            )

                            1 -> AnimatedContent(
                                targetState = selectedCategory,
                                transitionSpec = {
                                    val forward = targetState != null
                                    val enter = slideInHorizontally(tween(350)) { if (forward) it / 3 else -it / 3 } + fadeIn(tween(350))
                                    val exit = slideOutHorizontally(tween(250)) { if (forward) -it / 3 else it / 3 } + fadeOut(tween(200))
                                    enter togetherWith exit
                                },
                                label = "lessons"
                            ) { category ->
                                if (category == null) {
                                    LessonsScreen(onCategoryClick = { selectedCategory = it })
                                } else {
                                    WordsScreen(category = category, onBack = { selectedCategory = null })
                                }
                            }

                            2 -> AnimatedContent(
                                targetState = selectedGame,
                                transitionSpec = {
                                    val forward = targetState != 0
                                    val enter = scaleIn(tween(350), initialScale = if (forward) 0.92f else 1.06f) + fadeIn(tween(350))
                                    val exit = scaleOut(tween(250), targetScale = if (forward) 1.06f else 0.92f) + fadeOut(tween(200))
                                    enter togetherWith exit
                                },
                                label = "games"
                            ) { game ->
                                when (game) {
                                    1 -> FindWordScreen(onBack = { selectedGame = 0 }, onXp = addXp)
                                    2 -> BuildWordScreen(onBack = { selectedGame = 0 }, onXp = addXp)
                                    3 -> WordHuntScreen(onBack = { selectedGame = 0 }, onXp = addXp)
                                    else -> GamesScreen(onSelect = { selectedGame = it })
                                }
                            }

                            else -> RatingScreen(xp = xp)
                        }
                    }
                }

                BottomBar(
                    selected = tab,
                    onSelect = {
                        if (it != tab) {
                            tab = it
                        } else if (it == 1) {
                            selectedCategory = null
                        } else if (it == 2) {
                            selectedGame = 0
                        }
                    }
                )
            }

        }
    }
}

@Composable
private fun BottomBar(selected: Int, onSelect: (Int) -> Unit) {
    val shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Z.Panel.copy(alpha = 0.92f))
            .border(1.dp, Color.White.copy(alpha = 0.10f), shape)
            .navigationBarsPadding()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        navIcons.forEachIndexed { i, icon ->
            NavItem(
                icon = icon,
                label = navNames[i],
                selected = selected == i,
                onClick = { onSelect(i) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pillWidth by animateDpAsState(
        if (selected) 66.dp else 38.dp,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 400f),
        label = "pillW"
    )
    val pillColor by animateColorAsState(
        if (selected) Z.Purple.copy(alpha = 0.30f) else Color.Transparent,
        tween(300),
        label = "pillC"
    )
    val emojiScale by animateFloatAsState(
        if (selected) 1.2f else 0.95f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 400f),
        label = "emojiS"
    )
    val labelColor by animateColorAsState(
        if (selected) Z.White else Z.Muted,
        tween(300),
        label = "labelC"
    )

    Column(
        modifier.bounceClick(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            Modifier
                .width(pillWidth)
                .height(34.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(pillColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = labelColor,
                modifier = Modifier.size(24.dp).graphicsLayer {
                    scaleX = emojiScale
                    scaleY = emojiScale
                }
            )
        }
        Text(
            label,
            color = labelColor,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
