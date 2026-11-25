package tj.soni.englishgame.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/* ---------- Modifiers ---------- */

fun Modifier.bounceClick(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) 0.94f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "press"
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = source,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}

fun Modifier.staggerIn(index: Int, key: Any = Unit): Modifier = composed {
    val progress = remember(key) { Animatable(if (index < 10) 0f else 1f) }
    LaunchedEffect(key) {
        if (index < 10) {
            delay(index * 55L)
            progress.animateTo(1f, tween(480, easing = FastOutSlowInEasing))
        }
    }
    this.graphicsLayer {
        alpha = progress.value
        translationY = (1f - progress.value) * 70f
        val s = 0.94f + 0.06f * progress.value
        scaleX = s
        scaleY = s
    }
}

fun Modifier.shake(trigger: Int): Modifier = composed {
    val offset = remember { Animatable(0f) }
    LaunchedEffect(trigger) {
        if (trigger > 0) {
            for (step in listOf(-16f, 14f, -10f, 8f, -4f, 0f)) {
                offset.animateTo(step, tween(45))
            }
        }
    }
    this.graphicsLayer { translationX = offset.value }
}

fun Modifier.floating(amplitude: Float = 8f, durationMs: Int = 2200): Modifier = composed {
    val t = rememberInfiniteTransition(label = "float")
    val y by t.animateFloat(
        initialValue = -amplitude,
        targetValue = amplitude,
        animationSpec = infiniteRepeatable(
            tween(durationMs, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "floatY"
    )
    this.graphicsLayer { translationY = y }
}

/* ---------- Background ---------- */

@Composable
fun AnimatedBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val transition = rememberInfiniteTransition(label = "bg")
    val t by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(16000, easing = LinearEasing), RepeatMode.Reverse),
        label = "bgT"
    )
    Box(modifier.fillMaxSize().background(Z.Bg)) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            fun blob(color: Color, cx: Float, cy: Float, r: Float) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(color.copy(alpha = 0.32f), Color.Transparent),
                        center = Offset(cx, cy),
                        radius = r
                    ),
                    radius = r,
                    center = Offset(cx, cy)
                )
            }
            blob(Z.Violet, w * (0.15f + 0.55f * t), h * (0.12f + 0.10f * t), w * 0.85f)
            blob(Z.Mint, w * (0.95f - 0.5f * t), h * (0.55f + 0.15f * t), w * 0.7f)
            blob(Z.Pink, w * (0.2f + 0.3f * t), h * (0.95f - 0.12f * t), w * 0.75f)
        }
        content()
    }
}

/* ---------- Cards & buttons ---------- */

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    corner: Dp = 24.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(corner)
    val clickMod = if (onClick != null) Modifier.bounceClick(onClick = onClick) else Modifier
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(clickMod)
            .clip(shape)
            .background(Z.Card.copy(alpha = 0.55f))
            .background(
                Brush.linearGradient(listOf(tint.copy(alpha = 0.16f), tint.copy(alpha = 0.04f)))
            )
            .border(1.dp, tint.copy(alpha = 0.20f), shape)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = content
    )
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Z.Purple, Z.Violet),
    enabled: Boolean = true,
    height: Dp = 54.dp
) {
    val alpha by animateFloatAsState(if (enabled) 1f else 0.4f, tween(250), label = "btnAlpha")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .graphicsLayer { this.alpha = alpha }
            .bounceClick(enabled = enabled, onClick = onClick)
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.horizontalGradient(colors)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun SoftButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val alpha by animateFloatAsState(if (enabled) 1f else 0.4f, tween(250), label = "softAlpha")
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier
            .height(54.dp)
            .graphicsLayer { this.alpha = alpha }
            .bounceClick(enabled = enabled, onClick = onClick)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.07f))
            .border(1.dp, Color.White.copy(alpha = 0.18f), shape),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Z.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
    }
}

@Composable
fun BackPill(onClick: () -> Unit) {
    Box(
        Modifier
            .size(42.dp)
            .bounceClick(onClick = onClick)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.09f))
            .border(1.dp, Color.White.copy(alpha = 0.18f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = Z.White, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun XpChip(xp: Int) {
    val shown by animateIntAsState(xp, tween(700), label = "xpChip")
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(Z.Yellow.copy(alpha = 0.15f))
            .border(1.dp, Z.Yellow.copy(alpha = 0.4f), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Icon(Icons.Rounded.Star, contentDescription = null, tint = Z.Yellow, modifier = Modifier.size(16.dp))
            Text("$shown", color = Z.Yellow, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun GameTopBar(title: String, onBack: () -> Unit, xp: Int) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackPill(onBack)
        Text(title, color = Z.White, fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.sp)
        XpChip(xp)
    }
}

/* ---------- Progress ---------- */

@Composable
fun AnimatedBar(
    progress: Float,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Z.Purple, Z.Mint),
    height: Dp = 10.dp
) {
    val p by animateFloatAsState(
        progress.coerceIn(0f, 1f),
        tween(800, easing = FastOutSlowInEasing),
        label = "bar"
    )
    val shape = RoundedCornerShape(50)
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.09f))
    ) {
        Box(
            Modifier
                .fillMaxWidth(p)
                .height(height)
                .clip(shape)
                .background(Brush.horizontalGradient(colors))
        )
    }
}

@Composable
fun ProgressRing(
    progress: Float,
    size: Dp,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    colors: List<Color> = listOf(Z.Purple, Z.Mint),
    content: @Composable () -> Unit = {}
) {
    val p by animateFloatAsState(
        progress.coerceIn(0f, 1f),
        tween(1100, easing = FastOutSlowInEasing),
        label = "ring"
    )
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(size)) {
            val sw = strokeWidth.toPx()
            val inset = sw / 2f
            val arcSize = Size(this.size.width - sw, this.size.height - sw)
            val stroke = Stroke(width = sw, cap = StrokeCap.Round)
            drawArc(
                color = Color.White.copy(alpha = 0.09f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = stroke
            )
            if (p > 0f) {
                drawArc(
                    brush = Brush.linearGradient(
                        colors,
                        start = Offset(0f, 0f),
                        end = Offset(this.size.width, this.size.height)
                    ),
                    startAngle = -90f,
                    sweepAngle = 360f * p,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = arcSize,
                    style = stroke
                )
            }
        }
        content()
    }
}

/* ---------- Confetti ---------- */

private class Particle(
    val x: Float,
    val fall: Float,
    val sway: Float,
    val size: Float,
    val spin: Float,
    val color: Color
)

@Composable
fun Confetti(trigger: Int, modifier: Modifier = Modifier, count: Int = 70) {
    if (trigger <= 0) return
    val palette = listOf(Z.Purple, Z.Mint, Z.Pink, Z.Yellow, Z.Blue, Z.Orange)
    val particles = remember(trigger) {
        List(count) {
            Particle(
                x = Random.nextFloat(),
                fall = 0.7f + Random.nextFloat() * 0.9f,
                sway = 2f + Random.nextFloat() * 6f,
                size = 9f + Random.nextFloat() * 10f,
                spin = 180f + Random.nextFloat() * 540f,
                color = palette[Random.nextInt(palette.size)]
            )
        }
    }
    val progress = remember(trigger) { Animatable(0f) }
    LaunchedEffect(trigger) {
        progress.animateTo(1f, tween(2400, easing = LinearEasing))
    }
    Canvas(modifier.fillMaxSize()) {
        val p = progress.value
        if (p >= 1f) return@Canvas
        val w = size.width
        val h = size.height
        particles.forEach { pt ->
            val x = pt.x * w + sin(p * pt.sway * 3f) * 40f
            val y = -40f + p * (h + 80f) * pt.fall
            val alpha = if (p > 0.75f) (1f - p) / 0.25f else 1f
            rotate(degrees = p * pt.spin, pivot = Offset(x, y)) {
                drawRect(
                    color = pt.color.copy(alpha = alpha.coerceIn(0f, 1f)),
                    topLeft = Offset(x, y),
                    size = Size(pt.size, pt.size * 0.55f)
                )
            }
        }
    }
}

/* ---------- Letter game widgets ---------- */

@Composable
fun AnswerSlots(
    answer: String,
    length: Int,
    status: Boolean?,
    shakeTrigger: Int,
    modifier: Modifier = Modifier
) {
    val accent = when (status) {
        true -> Z.Mint
        false -> Z.Red
        null -> Z.Purple
    }
    Column(
        modifier.fillMaxWidth().shake(shakeTrigger),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0 until length).toList().chunked(6).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                row.forEach { i ->
                    val ch = answer.getOrNull(i)
                    val fill by animateColorAsState(
                        if (ch != null) accent.copy(alpha = 0.28f) else Color.White.copy(alpha = 0.06f),
                        tween(250),
                        label = "slotFill"
                    )
                    val border by animateColorAsState(
                        if (ch != null) accent else Color.White.copy(alpha = 0.18f),
                        tween(250),
                        label = "slotBorder"
                    )
                    val pop by animateFloatAsState(
                        if (ch != null) 1f else 0f,
                        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 500f),
                        label = "slotPop"
                    )
                    Box(
                        Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .background(fill)
                            .border(1.5.dp, border, RoundedCornerShape(13.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            ch?.toString() ?: " ",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.graphicsLayer {
                                scaleX = pop
                                scaleY = pop
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LetterTile(
    letter: Char,
    used: Boolean,
    enabled: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        if (used) 0.7f else 1f,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 450f),
        label = "tileScale"
    )
    val alpha by animateFloatAsState(if (used) 0.18f else 1f, tween(200), label = "tileAlpha")
    Box(
        modifier
            .height(58.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .bounceClick(enabled = enabled && !used, onClick = onClick)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.verticalGradient(listOf(color, color.copy(alpha = 0.65f)))),
        contentAlignment = Alignment.Center
    ) {
        Text(letter.toString(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
    }
}
