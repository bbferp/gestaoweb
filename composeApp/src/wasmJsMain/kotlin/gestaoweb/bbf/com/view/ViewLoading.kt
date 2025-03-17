package gestaoweb.bbf.com.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gestaoweb.bbf.com.util.Theme.darkBlueColor
import kotlinx.coroutines.flow.MutableStateFlow

var abrirViewLoading = MutableStateFlow(false)

@Composable
fun showViewLoading() {
    AnimatedVisibility (
        visible = abrirViewLoading.collectAsState().value,
        enter =   fadeIn(animationSpec = tween(durationMillis = 500)),
        exit =   fadeOut(animationSpec = tween(durationMillis = 500))
    ) {

        val strokeWidth = 8f
        val animatables = remember { List(3) { Animatable(0f) } }

        LaunchedEffect(true) {
            while (true) {
                for (i in animatables.indices) {
                    animatables[i].animateTo(1f, tween(300))
                    animatables[i].animateTo(0f, tween(300))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .background(darkBlueColor)
                    .size(100.dp)
            ) {
                val radius = size.minDimension / 2
                val spacing = 20f

                animatables.forEachIndexed { index, animatable ->
                    val xOffset = (index - 1) * spacing
                    drawCircle(
                        color = darkBlueColor,
                        radius = radius / 10,
                        center = Offset(radius + xOffset, radius)
                    )
                    drawCircle(
                        color = Color.Black,
                        radius = radius / 10,
                        center = Offset(radius + xOffset, radius),
                        alpha = animatable.value
                    )
                }
            }
        }
    }
}
