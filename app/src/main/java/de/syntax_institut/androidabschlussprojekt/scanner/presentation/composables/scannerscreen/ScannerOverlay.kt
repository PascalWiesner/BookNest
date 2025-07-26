package de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScannerOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val overlayColor = Color.Black.copy(alpha = 0.7f)

        val scanRectWidth = 300.dp.toPx()
        val scanRectHeight = 100.dp.toPx()

        val centerX = size.width / 2
        val centerY = size.height / 2

        val left = centerX - scanRectWidth / 2
        val top = centerY - scanRectHeight / 2

        drawRect(color = overlayColor)

        drawRect(
            color = Color.Transparent,
            topLeft = Offset(left, top),
            size = Size(scanRectWidth, scanRectHeight),
            blendMode = BlendMode.Clear
        )
    }
}