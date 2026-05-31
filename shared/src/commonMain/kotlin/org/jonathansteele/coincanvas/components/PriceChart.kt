package org.jonathansteele.coincanvas.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Suppress("ComposeUnrememberedObjectInspection")
@Composable
fun PriceChart(history: List<Double>) {
    if (history.isEmpty()) return

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary

    val max = history.max()
    val min = history.min()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val stepX = size.width / (history.size - 1)
        val scaleY = size.height / (max - min)

        // Convert points to canvas coordinates
        val points = history.mapIndexed { i, value ->
            Offset(
                x = stepX * i,
                y = size.height - ((value - min) * scaleY).toFloat()
            )
        }

        // Build a smooth cubic Bézier path
        val path = Path().apply {
            moveTo(points.first().x, points.first().y)

            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val curr = points[i]

                val control1 = Offset((prev.x + curr.x) / 2, prev.y)
                val control2 = Offset((prev.x + curr.x) / 2, curr.y)

                cubicTo(
                    control1.x, control1.y,
                    control2.x, control2.y,
                    curr.x, curr.y
                )
            }
        }

        // Gradient stroke
        val gradient = Brush.verticalGradient(
            colors = listOf(primary, secondary),
            startY = 0f,
            endY = size.height
        )

        drawPath(
            path = path,
            brush = gradient,
            style = Stroke(width = 4f)
        )
    }
}
