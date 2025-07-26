package de.syntax_institut.androidabschlussprojekt.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import kotlinx.coroutines.delay
import kotlin.math.sin
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import de.syntax_institut.androidabschlussprojekt.ui.theme.VintageWoodBrown

@Composable
fun BookSplashScreen(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val starCount = 5
    val maxOffset = 150f

    val scale = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition()

    LaunchedEffect(Unit) {
        scale.animateTo(1f, tween(durationMillis = 800, easing = FastOutSlowInEasing))
        delay(2200)
        onTimeout()
    }


    val animationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VintageWoodBrown),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.applogopng),
            contentDescription = "Logo",
            modifier = Modifier
                .size(220.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        )

        for (i in 0 until starCount) {
            val phaseX = i * 72f
            val phaseY = i * 144f

            val xOffset = maxOffset * sin(Math.toRadians((animationValue + phaseX).toDouble()))
            val yOffset = maxOffset * sin(Math.toRadians((animationValue + phaseY).toDouble()))

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Sparkle",
                tint = Color.Yellow,
                modifier = Modifier
                    .size(30.dp)
                    .offset(xOffset.dp, yOffset.dp)
            )
        }
    }
}