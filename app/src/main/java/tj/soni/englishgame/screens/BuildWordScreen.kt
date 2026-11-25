package tj.soni.englishgame.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tj.soni.englishgame.data.Word
import tj.soni.englishgame.games.BuildWordGame
import tj.soni.englishgame.games.GameWordBank
import tj.soni.englishgame.ui.AnimatedBar
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

@Composable
fun BuildWordScreen(
    onBack: () -> Unit,
    onXp: (Int) -> Unit = {}
) {
    val questions = remember {
        BuildWordGame.createQuestions(GameWordBank.getAllWords(), 10)
    }

    var index by remember { mutableIntStateOf(0) }
    var xp by remember { mutableIntStateOf(0) }
    var status by remember { mutableStateOf<Boolean?>(null) }
    var wrongShake by remember { mutableIntStateOf(0) }
    var confetti by remember { mutableIntStateOf(0) }
    var lastAnswer by remember { mutableStateOf("") }

    val selectedIndices = remember { mutableStateListOf<Int>() }
    val correctWords = remember { mutableStateListOf<Word>() }
    val wrongWords = remember { mutableStateListOf<Word>() }

    val finished = index >= questions.size
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(status) {
        if (status != null) {
            delay(1200)
            index++
            selectedIndices.clear()
            status = null
        }
    }

    LaunchedEffect(finished) {
        if (finished && questions.isNotEmpty() && correctWords.size * 2 >= questions.size) confetti++
    }

    val letterColors = listOf(Z.Blue, Z.Purple, Color(0xFFB33BFF), Color(0xFF087F9C))

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GameTopBar("ҲАРФҲОРО ҶАМЪ КУН", onBack, xp)

            if (finished) {
                val percent = if (questions.isEmpty()) 0 else correctWords.size * 100 / questions.size
                val shownPercent by animateIntAsState(percent, tween(1200), label = "buildPercent")

                Text(
                    "НАТИҶАИ БОЗӢ",
                    color = Z.Yellow,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.staggerIn(0)
                )
                GlassCard(modifier = Modifier.staggerIn(1), tint = Z.Purple, corner = 28.dp) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        ProgressRing(progress = percent / 100f, size = 110.dp, strokeWidth = 11.dp) {
                            Text("$shownPercent%", color = Z.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("$xp XP", color = Z.Yellow, fontSize = 26.sp, fontWeight = FontWeight.Black)
                            Text("Дуруст: ${correctWords.size}", color = Z.Mint, fontSize = 16.sp)
                            Text("Нодуруст: ${wrongWords.size}", color = Z.Red, fontSize = 16.sp)
                        }
                    }
                }

                if (correctWords.isNotEmpty()) {
                    Text("Калимаҳои дуруст", color = Z.Mint, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    correctWords.forEachIndexed { i, w ->
                        Text("${w.english} — ${w.tajik}", color = Color.White, modifier = Modifier.staggerIn(i + 2))
                    }
                }
                if (wrongWords.isNotEmpty()) {
                    Text("Калимаҳои нодуруст", color = Z.Red, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    wrongWords.forEachIndexed { i, w ->
                        Text("${w.english} — ${w.tajik}", color = Color.White, modifier = Modifier.staggerIn(i + 2))
                    }
                }

                GradientButton("Ба бозиҳо баргаштан", onBack)
            } else {
                val question = questions[index]
                val answer = question.word.english.uppercase()
                val selectedAnswer = selectedIndices.joinToString("") { question.letters[it].toString() }

                fun chooseLetter(letterIndex: Int) {
                    if (status != null || letterIndex in selectedIndices) return
                    selectedIndices.add(letterIndex)
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)

                    if (selectedIndices.size == answer.length) {
                        val built = selectedIndices.joinToString("") { question.letters[it].toString() }
                        val isCorrect = built == answer
                        lastAnswer = answer
                        status = isCorrect
                        if (isCorrect) {
                            xp += 10
                            onXp(10)
                            correctWords.add(question.word)
                        } else {
                            wrongShake++
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            wrongWords.add(question.word)
                        }
                    }
                }

                Text(
                    "САВОЛИ ${index + 1} / ${questions.size}",
                    color = Z.White,
                    fontWeight = FontWeight.Bold
                )
                AnimatedBar(
                    progress = (index + (if (status != null) 1 else 0)).toFloat() / questions.size.coerceAtLeast(1),
                    colors = listOf(Z.Yellow, Z.Orange)
                )

                key(index) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        GlassCard(modifier = Modifier.staggerIn(0), tint = Z.Purple, corner = 26.dp) {
                            Text("КАЛИМАИ АНГЛИСИРО СОЗ", color = Z.Yellow, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(question.word.tajik, color = Z.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                            Text("Тарҷумаи англисиро аз ҳарфҳо соз", color = Z.Muted, fontSize = 13.sp)
                        }

                        Box(Modifier.staggerIn(1)) {
                            AnswerSlots(
                                answer = selectedAnswer,
                                length = answer.length,
                                status = status,
                                shakeTrigger = wrongShake
                            )
                        }

                        Column(
                            Modifier.staggerIn(2),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            question.letters.withIndex().toList().chunked(4).forEach { row ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    row.forEach { entry ->
                                        LetterTile(
                                            letter = entry.value,
                                            used = entry.index in selectedIndices,
                                            enabled = status == null,
                                            color = letterColors[entry.index % letterColors.size],
                                            onClick = { chooseLetter(entry.index) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    repeat(4 - row.size) {
                                        Spacer(Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SoftButton(
                        text = "Ҳарфи охирин",
                        onClick = {
                            if (selectedIndices.isNotEmpty()) {
                                selectedIndices.removeAt(selectedIndices.lastIndex)
                            }
                        },
                        enabled = status == null && selectedIndices.isNotEmpty(),
                        modifier = Modifier.weight(1f)
                    )
                    SoftButton(
                        text = "Тоза кардан",
                        onClick = { selectedIndices.clear() },
                        enabled = status == null && selectedIndices.isNotEmpty(),
                        modifier = Modifier.weight(1f)
                    )
                }

                AnimatedVisibility(
                    visible = status != null,
                    enter = slideInVertically(tween(300)) { it / 2 } + fadeIn(tween(300)),
                    exit = ExitTransition.None
                ) {
                    val ok = status != false
                    GlassCard(tint = if (ok) Z.Mint else Z.Red, corner = 20.dp) {
                        Text(
                            if (ok) "ОФАРИН! +10 XP" else "ХАТО! Ҷавоби дуруст: $lastAnswer",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }
                }

                Text(
                    "$xp XP     ${correctWords.size}     ${wrongWords.size}",
                    color = Z.Yellow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Confetti(trigger = confetti)
    }
}
