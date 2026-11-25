package tj.soni.englishgame.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tj.soni.englishgame.data.Word
import tj.soni.englishgame.games.FindWordGame
import tj.soni.englishgame.games.GameWordBank
import tj.soni.englishgame.ui.AnimatedBar
import tj.soni.englishgame.ui.Confetti
import tj.soni.englishgame.ui.GameTopBar
import tj.soni.englishgame.ui.GlassCard
import tj.soni.englishgame.ui.GradientButton
import tj.soni.englishgame.ui.ProgressRing
import tj.soni.englishgame.ui.Z
import tj.soni.englishgame.ui.bounceClick
import tj.soni.englishgame.ui.shake
import tj.soni.englishgame.ui.staggerIn

private enum class OptionState { Idle, Correct, Wrong, Dim }

@Composable
fun FindWordScreen(
    onBack: () -> Unit,
    onXp: (Int) -> Unit = {},
    onWrongAnswer: (Word) -> Unit = {}
) {
    val questions = remember {
        FindWordGame.createQuestions(GameWordBank.getAllWords(), 10)
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var xp by remember { mutableIntStateOf(0) }
    var streak by remember { mutableIntStateOf(0) }
    var wrongShake by remember { mutableIntStateOf(0) }
    var confetti by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var lastCorrect by remember { mutableStateOf(true) }
    var lastCorrectText by remember { mutableStateOf("") }

    val correctWords = remember { mutableStateListOf<Word>() }
    val wrongWords = remember { mutableStateListOf<Word>() }

    val finished = currentIndex >= questions.size
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(selectedAnswer) {
        if (selectedAnswer != null) {
            delay(1100)
            currentIndex++
            selectedAnswer = null
        }
    }

    LaunchedEffect(finished) {
        if (finished && questions.isNotEmpty() && score * 2 >= questions.size) confetti++
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GameTopBar("КАЛИМАЁБАКА", onBack, xp)

            if (finished) {
                val percent = if (questions.isEmpty()) 0 else score * 100 / questions.size
                val shownPercent by animateIntAsState(percent, tween(1200), label = "findPercent")

                Text(
                    "НАТИҶАИ БОЗӢ!",
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
                        ProgressRing(
                            progress = percent / 100f,
                            size = 110.dp,
                            strokeWidth = 11.dp
                        ) {
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
                    Text("Ҷавобҳои дуруст", color = Z.Mint, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    correctWords.forEachIndexed { i, word ->
                        ResultWordCard(word, true, Modifier.staggerIn(i + 2))
                    }
                }
                if (wrongWords.isNotEmpty()) {
                    Text("Ҷавобҳои нодуруст", color = Z.Red, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    wrongWords.forEachIndexed { i, word ->
                        ResultWordCard(word, false, Modifier.staggerIn(i + 2))
                    }
                }
                GradientButton("Ба меню баргаштан", onBack)
            } else {
                val question = questions[currentIndex]
                val answered = if (selectedAnswer != null) 1 else 0

                Text(
                    "САВОЛИ ${currentIndex + 1} / ${questions.size}",
                    color = Z.White,
                    fontWeight = FontWeight.Bold
                )
                AnimatedBar(
                    progress = (currentIndex + answered).toFloat() / questions.size,
                    colors = listOf(Z.Yellow, Z.Orange)
                )

                key(currentIndex) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassCard(
                            modifier = Modifier.staggerIn(0),
                            tint = Z.Purple,
                            corner = 26.dp
                        ) {
                            Text(
                                if (question.englishToTajik) "ТАРҶУМАИ КАЛИМАРО ЁБ" else "КАЛИМАИ АНГЛИСИРО ЁБ",
                                color = Z.Yellow,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                question.question,
                                color = Z.White,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        question.options.forEachIndexed { i, option ->
                            val isCorrect = option == question.correctAnswer
                            val isSelected = option == selectedAnswer
                            val state = when {
                                selectedAnswer == null -> OptionState.Idle
                                isCorrect -> OptionState.Correct
                                isSelected -> OptionState.Wrong
                                else -> OptionState.Dim
                            }
                            OptionButton(
                                text = option,
                                state = state,
                                shakeTrigger = if (isSelected && !isCorrect) wrongShake else 0,
                                enabled = selectedAnswer == null,
                                modifier = Modifier.staggerIn(i + 1),
                                onClick = {
                                    if (selectedAnswer == null) {
                                        selectedAnswer = option
                                        lastCorrect = isCorrect
                                        lastCorrectText = question.correctAnswer
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        if (isCorrect) {
                                            score++
                                            xp += 10
                                            streak++
                                            onXp(10)
                                            correctWords.add(question.word)
                                        } else {
                                            streak = 0
                                            wrongShake++
                                            wrongWords.add(question.word)
                                            onWrongAnswer(question.word)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = selectedAnswer != null,
                    enter = slideInVertically(tween(300)) { it / 2 } + fadeIn(tween(300)),
                    exit = ExitTransition.None
                ) {
                    GlassCard(tint = if (lastCorrect) Z.Mint else Z.Red, corner = 20.dp) {
                        Text(
                            if (lastCorrect) "ОФАРИН! ДУРУСТ!" else "ХАТО! Ҷавоби дуруст: $lastCorrectText",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                        if (lastCorrect && streak >= 2) {
                            Text("Серия: $streak дуруст пай дар пай!", color = Z.Yellow, fontSize = 14.sp)
                        }
                    }
                }

                Text(
                    "$xp XP     $score дуруст     ${wrongWords.size} хато",
                    color = Z.Yellow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Confetti(trigger = confetti)
    }
}

@Composable
private fun OptionButton(
    text: String,
    state: OptionState,
    shakeTrigger: Int,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg by animateColorAsState(
        when (state) {
            OptionState.Idle -> Color.White.copy(alpha = 0.08f)
            OptionState.Correct -> Z.Mint.copy(alpha = 0.35f)
            OptionState.Wrong -> Z.Red.copy(alpha = 0.38f)
            OptionState.Dim -> Color.White.copy(alpha = 0.03f)
        },
        tween(250),
        label = "optBg"
    )
    val border by animateColorAsState(
        when (state) {
            OptionState.Idle -> Color.White.copy(alpha = 0.20f)
            OptionState.Correct -> Z.Mint
            OptionState.Wrong -> Z.Red
            OptionState.Dim -> Color.White.copy(alpha = 0.06f)
        },
        tween(250),
        label = "optBorder"
    )
    val scale by animateFloatAsState(
        if (state == OptionState.Correct) 1.04f else 1f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 400f),
        label = "optScale"
    )
    val shape = RoundedCornerShape(18.dp)
    val mark = when (state) {
        OptionState.Correct -> "  "
        OptionState.Wrong -> "  "
        else -> ""
    }

    Box(
        modifier
            .fillMaxWidth()
            .height(60.dp)
            .shake(shakeTrigger)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .bounceClick(enabled = enabled, onClick = onClick)
            .clip(shape)
            .background(bg)
            .border(1.5.dp, border, shape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text + mark,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ResultWordCard(word: Word, correct: Boolean, modifier: Modifier = Modifier) {
    GlassCard(
        modifier = modifier,
        tint = if (correct) Z.Mint else Z.Red,
        corner = 18.dp
    ) {
        Text(
            "${if (correct) "" else ""} ${word.english} — ${word.tajik}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        Text(word.sentenceEn, color = Color.White.copy(alpha = 0.85f), fontSize = 14.sp)
        Text(word.sentenceTg, color = Color.White.copy(alpha = 0.85f), fontSize = 14.sp)
    }
}
