package com.github.rwsbillyang.composedemo.demo2


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme

/**
 * ExpandableTextContent
 * @author TheMelody
 * email developer_melody@163.com
 * created 2022/9/6 20:35
 */
@Composable
internal fun ExpandableTextContent() {
    Box(modifier = Modifier
        .fillMaxSize()
        //.systemBarsPadding()
    )
    {
        ExpandableText(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth(),
            // 读者可自行设置文本，查看效果
            //text = buildAnnotatedString { append(stringResource(id = R.string.test_expand_content)) },
            text = buildAnnotatedString { append("话说天下大势，分久必合，合久必分。周末七国分争，并入于秦。及秦灭之后，楚、汉分争，又并入于汉。汉朝自高祖斩白蛇而起义，一统天下，后来光武中兴，传至献帝，遂分为三国。推其致乱之由，殆始于桓、灵二帝。桓帝禁锢善类，崇信宦官。及桓帝崩，灵帝即位，大将军窦武、太傅陈蕃共相辅佐。时有宦官曹节等弄权，窦武、陈蕃谋诛之，机事不密，反为所害，中涓自此愈横。") },
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            expandStateText = "展开",
            collapseStateText = "收起"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewExpandableTextContent()
{
    ComposeDemoTheme(
        darkTheme = true
    ) {
        ExpandableTextContent()
    }
}