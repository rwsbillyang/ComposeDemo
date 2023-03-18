package com.github.rwsbillyang.composedemo.demo



import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.launch

import kotlin.math.abs

@Preview(showBackground = true)
@Composable
fun PreviewDemo() {
    ComposeDemoTheme {
        demo5()
    }
}

@Composable
fun demo1(){
    Canvas(Modifier.size(120.dp)) {
        drawRect(color = Color.Gray) // Draw grey background
        // Inset content by 10 pixels on the left/right sides and 12 by the
        // top/bottom
        inset(30f, 60f) {
            val quadrantSize = size / 4.0f

            // Draw a rectangle within the inset bounds
            drawRect(
                //size = quadrantSize,
                color = Color.DarkGray
            )
            drawLine(Color.Red, Offset(-10f,-10f), Offset(quadrantSize.width,quadrantSize.height))

            rotate(-15.0f) {
                //drawRect(size = quadrantSize, color = Color.Blue)
                drawLine(Color.Yellow, Offset(-10f,-10f), Offset(quadrantSize.width,quadrantSize.height))
            }
            rotate(15.0f) {
                //drawRect(size = quadrantSize, color = Color.Blue)
                drawLine(Color.Magenta, Offset(-10f,-10f), Offset(quadrantSize.width,quadrantSize.height))
            }
        }
    }
}


@Composable
fun demo2() {
    Canvas(Modifier.size(120.dp)) {
        drawRect(color = Color.Gray) // Draw grey background

        translate(60f, 60f) {
            val quadrantSize = size / 4.0f

            // Draw a rectangle within the inset bounds
            drawRect(
                size = quadrantSize,
                color = Color.DarkGray,
                alpha = 0.5f
            )
            drawLine(Color.Red, Offset(-10f, -10f), Offset(quadrantSize.width, quadrantSize.height))

            rotate(-15.0f) {
                drawRect(size = quadrantSize, color = Color.Blue, alpha = 0.5f)
                drawLine(
                    Color.Yellow,
                    Offset(-10f, -10f),
                    Offset(quadrantSize.width, quadrantSize.height)
                )
            }
            rotate(15.0f) {
                drawRect(size = quadrantSize, color = Color.Green, alpha = 0.5f)
                drawLine(
                    Color.Magenta,
                    Offset(-10f, -10f),
                    Offset(quadrantSize.width, quadrantSize.height)
                )
            }
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun demo3() {
    val textMeasurer = rememberTextMeasurer()
    var textLayoutResult by remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    var str = ""
    Canvas(
        Modifier
            .fillMaxSize()
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)//得到child view的大小
                // TextLayout can be done any time prior to its use in draw, including in a
                // background thread.
                // In this sample, text layout is done in compose layout. This way the layout call
                // can be restarted when async font loading completes due to the fact that
                // `.measure` call is executed in `.layout`.
                //w=1080,h=2340,w2=1080,h2=2340
                str = "placeable: w=${placeable.width},h=${placeable.height},w2=${placeable.measuredWidth},h2=${placeable.measuredHeight}"
                textLayoutResult = textMeasurer.measure(
                    text = AnnotatedString("甲"),
                    style = TextStyle(fontSize = 16.sp)
                )

                //", textLayoutResult: w=44,h=59, lineCount=1"  when "甲" fontSize = 16.sp
                //", textLayoutResult: w=38,h=52, lineCount=1"  when "甲" fontSize = 14.sp
                //", textLayoutResult: w=44,h=59, lineCount=1"  when "子" fontSize = 16.sp
                //", textLayoutResult: w=38,h=52, lineCount=1"  when "子" fontSize = 14.sp
                str += ", textLayoutResult: w=${textLayoutResult!!.size.width},h=${textLayoutResult!!.size.height}, lineCount=${textLayoutResult!!.lineCount}"

                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            }) {

        // This happens during draw phase.
        textLayoutResult?.let {
            drawText(it, Color.Red)
            drawRect(Color.LightGray, Offset.Zero, it.getBoundingBox(0).size, 0.5f)
            drawLine(Color.DarkGray, Offset(it.size.width / 2.0f, 0f), Offset(it.size.width / 2.0f, it.size.height.toFloat()))
        }
        drawText(textMeasurer, str, Offset(textLayoutResult?.size?.width?.toFloat()?:0f,textLayoutResult?.size?.height?.toFloat()?:0f))
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun demo4() {
    val textMeasurer = rememberTextMeasurer()
    //val fontMetrics: Paint.FontMetrics = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 16f }.fontMetrics
    //val str = "FontMetrics: top=${fontMetrics.top}, bottom=${fontMetrics.bottom}, ascent=${fontMetrics.ascent}, descent=${fontMetrics.descent},leading=${fontMetrics.leading}"

    val it = textMeasurer.measure(
        text = AnnotatedString("甲"),
        style = TextStyle(fontSize = 16.sp)
    )
    val str = "textLayoutResult: w=${it.size.width},h=${it.size.height}, lineCount=${it.lineCount}" +
            ", getLineTop=${it.getLineTop(0)}, getLineBottom=${it.getLineBottom(0)}, getLineStart=${it.getLineStart(0)}, getLineEnd=${it.getLineEnd(0)}"+
            ",firstBaseline=${ it.firstBaseline},lastBaseline=${ it.lastBaseline}"


    Canvas(
        Modifier.fillMaxSize()
    ){
        drawText(it, Color.Red)
        drawRect(Color.LightGray, Offset.Zero, it.getBoundingBox(0).size, 0.5f)

        drawLine(Color.DarkGray, Offset(it.size.width / 2.0f, 0f), Offset(it.size.width / 2.0f, it.size.height.toFloat()))

        drawLine(Color.DarkGray, Offset(0f, it.firstBaseline), Offset(it.size.width.toFloat(), it.firstBaseline))

        drawText(textMeasurer, str, Offset(it.size.width.toFloat(),it.size.height.toFloat())) //默认topLeft为起始圆点
    }
}
@OptIn(ExperimentalTextApi::class)
@Composable
fun demo5() {
    val textMeasurer = rememberTextMeasurer()
    val f: Paint.FontMetrics = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 16f }.fontMetrics
    //top / bottom: 它们的作用是限制所有字形（ glyph ）的顶部和底部范围。
    // 有些字形的显示范围是会超过 ascent 和 descent 的，而 top 和 bottom 则限制的是所有字形的显示范围，包括这些特殊字形。
    //top的值为负（因为它在 baseline 的上方）； bottom 的值是 baseline 相对位移，值为正（因为它在 baseline 的下方）
    //ascent / descent: 它们的作用是限制普通字符的顶部和底部范围。
    //普通的字符，上不会高过 ascent ，下不会低过 descent ，大部分的字形都显示在 ascent 和 descent 两条线的范围内。
    // 具体到 Android 的绘制中， ascent 的值是baseline 的相对位移，它的值为负（因为它在 baseline 的上方）；
    // descent 的值是图中橙线和 baseline 相对位移，值为正（因为它在 baseline 的下方）。
    //leading: 指的是行的额外间距，即对于上下相邻的两行，上行的 bottom 线和下行的 top 线的距离

    val minHeight = abs(f.ascent) + abs(f.descent)
    val maxHeight = abs(f.top) + abs(f.bottom)
    val str = "FontMetrics: top=${f.top}, bottom=${f.bottom}, ascent=${f.ascent}, descent=${f.descent},leading=${f.leading}, minHeight=$minHeight, maxHeight=$maxHeight"
    //"FontMetrics: top=-16.898438, bottom=4.3359375, ascent=-14.84375, descent=3.90625,leading=0.0, minHeight=18.75, maxHeight=21.234375"



    val it = textMeasurer.measure(
        text = AnnotatedString("甲"),
        style = TextStyle(fontSize = 14.sp)
    )
    val str2 = "textLayoutResult: w=${it.size.width},h=${it.size.height}, lineCount=${it.lineCount}" +
            ", getLineTop=${it.getLineTop(0)}, getLineBottom=${it.getLineBottom(0)}, getLineStart=${it.getLineStart(0)}, getLineEnd=${it.getLineEnd(0)}"+
            ",firstBaseline=${ it.firstBaseline},lastBaseline=${ it.lastBaseline}"


    Canvas(
        Modifier.fillMaxSize()
    ){
        drawText(it, Color.Red)
        drawRect(Color.LightGray, Offset.Zero, it.getBoundingBox(0).size, 0.5f)
        drawLine(Color.DarkGray, Offset(it.size.width / 2.0f, 0f), Offset(it.size.width / 2.0f, it.size.height.toFloat()))
        drawLine(Color.DarkGray, Offset(0f, it.firstBaseline), Offset(it.size.width.toFloat(), it.firstBaseline))

        drawText(textMeasurer, "甲",  Offset(50f,0f))

        drawText(textMeasurer, str, Offset(50f,50f)) //默认topLeft为起始圆点
        drawText(textMeasurer, str2, Offset(50f, 200f)) //默认topLeft为起始圆点
    }
}

//https://juejin.cn/post/7137673203714899998
@OptIn(ExperimentalTextApi::class)
@Composable
fun demo50() {
    Text(
        text = "永远相信美好的事情即将发生❤️",
        modifier = Modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                val brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFFE24CE2),
                        Color(0xFF73BB70),
                        Color(0xFFE24CE2)
                    )
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(brush, blendMode = BlendMode.SrcAtop)
                }
            }
    )
}


@Composable
fun MoviesScreen() {
    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()
    Column {

        Button(
            onClick = {

                // Create a new coroutine in the event handler to show a snackbar
                scope.launch {

                    //scaffoldState.snackbarHostState.showSnackbar("Something happened!")
                }
            }
        ) {
            Text("Press me")
        }
    }
}