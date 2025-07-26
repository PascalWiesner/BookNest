package de.syntax_institut.androidabschlussprojekt.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VintageWoodBrown,
    secondary = WarmSand,
    tertiary = CreamHighlight,
    background = DarkBrownText,
    surface = VintageWoodBrown,
    onPrimary = White,
    onSecondary = DarkBrownText,
    onTertiary = DarkBrownText,
    onBackground = CreamTextOnPrimary,
    onSurface = CreamTextOnPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = VintageWoodBrown,
    secondary = WarmSand,
    tertiary = CreamHighlight,
    background = PaperWhite,
    surface = CreamHighlight,
    onPrimary = Black,
    onSecondary = DarkBrownText,
    onTertiary = DarkBrownText,
    onBackground = DarkBrownText,
    onSurface = DarkBrownText,
)

@Composable
fun AndroidAbschlussprojektTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}