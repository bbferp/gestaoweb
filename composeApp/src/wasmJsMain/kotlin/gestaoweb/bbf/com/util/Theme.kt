package gestaoweb.bbf.com.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush


object Theme {

    val transparentColor = Color(0x00000000)
    val  darkBlueColorTransparent = Color(0x340a1f44)
    val  darkBlueColor = Color(0xbb0a1f44)
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF597395),
            Color(0xFF005F73),
            Color(0xFF0A1F44)
        )
    )

    private val LightColorPalette = lightColors(
        primary = Color.Black,

    )

    @Composable
    fun MyAppTheme(content: @Composable () -> Unit) {
        MaterialTheme(
            colors = LightColorPalette,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradientBackground)
                ) {
                    content()
                }
            }
        )
    }
}
