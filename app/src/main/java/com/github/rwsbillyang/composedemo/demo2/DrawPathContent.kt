package com.github.rwsbillyang.composedemo.demo2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme


/**
 * DrawPath：普通的文本跨度背景绘制
 * @author TheMelody
 * email developer_melody@163.com
 * created 2022/9/2 21:50
 */
@Composable
internal fun DrawPathContent() {
    val testContent = "话说天下大势，分久必合，合久必分。周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义，一统天下，后来光武中兴，传至献帝，遂分为三国。推其致乱之由，殆始于桓、灵二帝。桓帝禁锢善类，崇信宦官。及桓帝崩，灵帝即位，大将军窦武、太傅陈蕃共相辅佐。时有宦官曹节等弄权，窦武、陈蕃谋诛之，机事不密，反为所害，中涓自此愈横。"
    val textSpansContent = "周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义"
    var onDraw: DrawScope.() -> Unit by remember { mutableStateOf({}) }
    var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(null) }

    LaunchedEffect(textLayoutResultState) {
        textLayoutResultState?.let {
            val textStartIndex = testContent.indexOf(textSpansContent)
            val path = it.getPathForRange(textStartIndex,textSpansContent.length)
            onDraw = {
                drawPath(
                    path = path,
                    brush = SolidColor(Color(0xFF899BBE)),
                    style = Stroke(width = 1.sp.toPx())
                )
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
fun PreviewDrawPathContent()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        DrawPathContent()
    }
}