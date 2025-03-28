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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


object Theme {

    val transparentColor = Color(0x00000000)
    val backgroundCard = Color(0xffe0e0e0)
    val  darkBlueColorTransparent = Color(0x340a1f44)
    val  colorIconClient = Color(0xfffc7900)
    val  darkBlueColor = Color(0xbb0a1f44)
    val  borderColor = Color(0x995d6987)
    val heightField = 55.dp
    val fontDefault = 12.sp
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
