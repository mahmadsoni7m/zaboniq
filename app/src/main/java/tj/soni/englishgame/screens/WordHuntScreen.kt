package tj.soni.englishgame.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tj.soni.englishgame.data.Word
import tj.soni.englishgame.games.GameWordBank
import tj.soni.englishgame.ui.AnswerSlots
import tj.soni.englishgame.ui.Confetti
import tj.soni.englishgame.ui.GameTopBar
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.GradientButton
import tj.soni.englishgame.ui.LetterTile
import tj.soni.englishgame.ui.ProgressRing
import tj.soni.englishgame.ui.SoftButton
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.staggerIn

private fun buildHuntWords(): List<Word> =
    GameWordBank.getAllWords()
        .filter { it.english.length in 3..10 && it.english.all(Char::isLetter) }
        .distinctBy { it.id }
        .shuffled()

@Composable
fun WordHuntScreen(
    onBack: () -> Unit,
    onXp: (Int) -> Unit
) {
    var words by remember { mutableStateOf(buildHuntWords()) }
    var index by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(60) }
    var score by remember { mutableIntStateOf(0) }
    var solvedCount by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf(listOf<Int>()) }
    var solved by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }
    var wrongShake by remember { mutableIntStateOf(0) }
    var confetti by remember { mutableIntStateOf(0) }
    var wrongFlash by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current
    val word = words.getOrNull(index)
    val letters = remember(index, words) {
        word?.english?.uppercase()?.toList()?.shuffled() ?: emptyList()
    }
    val answer = selected.mapNotNull { letters.getOrNull(it) }.joinToString("")

    LaunchedEffect(finished) {
        while (!finished && seconds > 0) {
            delay(1000)
            seconds--
        }
        if (seconds == 0) finished = true
    }

    LaunchedEffect(word) {
        if (word == null) finished = true
    }

    LaunchedEffect(finished) {
        if (finished && score > 0) confetti++
    }

    LaunchedEffect(wrongFlash) {
        if (wrongFlash) {
            delay(650)
            selected = emptyList()
            wrongFlash = false
        }
    }

    val pulseT = rememberInfiniteTransition(label = "pulse")
    val pulse by pulseT.animateFloat(
        initialValue = 1f,
        targetValue = 1.14f,
        animationSpec = infiniteRepeatable(tween(450, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulseScale"
    )
    val urgent = seconds <= 10 && !finished

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GameTopBar("КАЛИМАШИКОР", onBack, score)

            if (finished || word == null) {
                Spacer(Modifier.padding(top = 8.dp))
                Text(
                    "Бозӣ анҷом ёфт!",
                    color = Z.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.staggerIn(0)
                )
                GlassCard(modifier = Modifier.staggerIn(1), tint = Z.Orange, corner = 28.dp) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        ProgressRing(
                            progress = (solvedCount / 10f).coerceAtMost(1f),
                            size = 110.dp,
                            strokeWidth = 11.dp,
                            colors = listOf(Z.Yellow, Z.Orange)
                        ) {
                            Text("$solvedCount", color = Z.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Холи ту: $score XP", color = Z.Mint, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("Калимаҳои ёфтшуда: $solvedCount", color = Z.Muted, fontSize = 14.sp)
                        }
                    }
                }
                GradientButton(
                    "Боз аз нав",
                    onClick = {
                        words = buildHuntWords()
                        index = 0
                        seconds = 60
                        score = 0
                        solvedCount = 0
                        selected = emptyList()
                        solved = false
                        wrongFlash = false
                        finished = false
                    },
                    colors = listOf(Z.Orange, Color(0xFFE0701A))
                )
                SoftButton("Ба бозиҳо баргаштан", onBack, Modifier.fillMaxWidth())
            } else {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("$score XP", color = Z.Mint, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Ёфташуда: $solvedCount", color = Z.Muted, fontSize = 13.sp)
                    }
                    ProgressRing(
                        progress = seconds / 60f,
                        size = 78.dp,
                        strokeWidth = 8.dp,
                        colors = if (urgent) listOf(Z.Red, Z.Orange) else listOf(Z.Purple, Z.Mint),
                        modifier = Modifier.graphicsLayer {
                            val s = if (urgent) pulse else 1f
                            scaleX = s
                            scaleY = s
                        }
                    ) {
                        Text(
                            "$seconds",
                            color = if (urgent) Z.Red else Z.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                androidx.compose.runtime.key(index) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        GlassCard(modifier = Modifier.staggerIn(0), tint = Z.Orange, corner = 26.dp) {
                            Text("Калимаи тоҷикӣ:", color = Z.Muted, fontSize = 13.sp)
                            Text(word.tajik, color = Z.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                            Text("Тарҷумаи англисиро ҷамъ кун!", color = Z.Muted, fontSize = 13.sp)
                        }

                        Box(Modifier.staggerIn(1)) {
                            AnswerSlots(
                                answer = answer,
                                length = word.english.length,
                                status = if (solved) true else if (wrongFlash) false else null,
                                shakeTrigger = wrongShake
                            )
                        }

                        Column(
                            Modifier.staggerIn(2),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val colors = listOf(Z.Orange, Z.Purple, Z.Blue, Z.Pink)
                            letters.withIndex().toList().chunked(4).forEach { row ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    row.forEach { entry ->
                                        LetterTile(
                                            letter = entry.value,
                                            used = entry.index in selected,
                                            enabled = !solved && !wrongFlash,
                                            color = colors[entry.index % colors.size],
                                            onClick = {
                                                if (!solved && !wrongFlash && entry.index !in selected) {
                                                    selected = selected + entry.index
                                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                                }
                                            },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = solved,
                    enter = scaleIn(tween(300)) + fadeIn(tween(300)),
                    exit = fadeOut(tween(100))
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Офарин! +10 XP", color = Z.Mint, fontSize = 22.sp, fontWeight = FontWeight.Black)
                        GradientButton(
                            "Калимаи нав →",
                            onClick = {
                                index++
                                selected = emptyList()
                                solved = false
                            },
                            colors = listOf(Z.Mint, Color(0xFF1FA67A))
                        )
                    }
                }

                if (!solved) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SoftButton(
                            text = "Пок кардан",
                            onClick = { selected = emptyList() },
                            enabled = selected.isNotEmpty() && !wrongFlash,
                            modifier = Modifier.weight(1f)
                        )
                        GradientButton(
                            text = "Санҷидан",
                            onClick = {
                                if (answer.equals(word.english, ignoreCase = true)) {
                                    solved = true
                                    score += 10
                                    solvedCount++
                                    onXp(10)
                                    confetti++
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                } else {
                                    wrongShake++
                                    wrongFlash = true
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                }
                            },
                            enabled = selected.isNotEmpty() && !wrongFlash,
                            modifier = Modifier.weight(1f),
                            height = 54.dp
                        )
                    }
                }
            }
        }

        Confetti(trigger = confetti)
    }
}
