package com.github.rwsbillyang.composedemo.demo2



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme


/**
 * 扩展getBoundingBox实现：文本跨行背景绘制
 * @author TheMelody
 * email developer_melody@163.com
 * created 2022/9/3 08:20
 */
@Composable
internal fun CustomTextSpansContent() {
    val testContent = "话说天下大势，分久必合，合久必分。周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义，一统天下，后来光武中兴，传至献帝，遂分为三国。推其致乱之由，殆始于桓、灵二帝。桓帝禁锢善类，崇信宦官。及桓帝崩，灵帝即位，大将军窦武、太傅陈蕃共相辅佐。时有宦官曹节等弄权，窦武、陈蕃谋诛之，机事不密，反为所害，中涓自此愈横。"
    val textSpansContent = "周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义"
    var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }
    var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(null) }
    val path by remember { mutableStateOf(Path()) }
    LaunchedEffect(textLayoutResultState) {
        textLayoutResultState?.let {
            val findIndex = testContent.indexOf(textSpansContent)
            val rectList = it.getBoundingBoxRectList(findIndex,findIndex.plus(textSpansContent.length))
            onDraw = {
                rectList.forEachIndexed { index, rect ->
                    // 清除路径中的所有直线和曲线，保留内部数据结构便于更快地重用
                    path.asAndroidPath().rewind()
                    path.addRoundRect(
                        RoundRect(
                            rect = Rect(
                                left = rect.left - 2.sp.toPx(),
                                top = rect.top + 2.sp.toPx(),
                                right = rect.right + 2.sp.toPx(),
                                bottom = rect.bottom - 2.sp.toPx()
                            ),
                            topLeft = if(index == 0) CornerRadius(10F) else CornerRadius.Zero,
                            bottomLeft = if(index == 0) CornerRadius(10F) else CornerRadius.Zero,
                            topRight = if(index == rectList.lastIndex) CornerRadius(10F) else CornerRadius.Zero,
                            bottomRight = if(index == rectList.lastIndex) CornerRadius(10F) else CornerRadius.Zero,
                        )
                    )
                    drawPath(
                        path = path,
                        brush = SolidColor(Color(0xFF276FFF).copy(alpha = 0.3F)),
                        style = Fill
                    )
                    drawPath(
                        path = path,
                        brush = SolidColor(Color(0xFF276FFF)),
                        style = Stroke(width = 1.sp.toPx())
                    )
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = testContent,
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 20.sp),
            modifier = Modifier
                .align(Alignment.Center)
                .drawBehind { onDraw() },
            onTextLayout = {
                textLayoutResultState = it
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextSpansContent()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        CustomTextSpansContent()
    }
}