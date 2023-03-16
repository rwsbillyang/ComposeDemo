package com.github.rwsbillyang.composedemo.demo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme


//https://juejin.cn/post/6943118679949180958
@Composable
fun SmileFace(
    modifier: Modifier = Modifier.size(300.dp)
) {
    Canvas(
        modifier = modifier,
        onDraw = {
            // Head
            drawCircle(
                Brush.linearGradient(
                    colors = listOf(Color.Green, Color.Cyan)
                ),
                radius = size.width / 2 - 5,
                center = center,
                style = Stroke(width = size.width * 0.075f)
            )

            // Smile
            val smilePadding = size.width * 0.15f
            drawArc(
                color = Color.Red,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(smilePadding, smilePadding),
                size = Size(size.width - (smilePadding * 2f), size.height - (smilePadding * 2f))
            )

            // Left eye
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(size.width * 0.25f, size.height / 4),
                size = Size(smilePadding, smilePadding)
            )

            // Right eye
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset((size.width * 0.75f) - smilePadding, size.height / 4),
                size = Size(smilePadding, smilePadding)
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSmileFace() {
    ComposeDemoTheme {
        SmileFace()
    }
}