package com.github.rwsbillyang.composedemo.demo2


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp


/**
 * https://juejin.cn/post/7140529542665338910
 * MainContent
 * @author TheMelody
 * email developer_melody@163.com
 * created 2022/9/2 21:30
 */
@Composable
internal fun MainContent(onItemClick: (String) -> Unit) {
    val funItemList = listOf("drawPath实现：普通文本跨行背景绘制", "扩展getBoundingBox实现：文本跨行背景绘制", "查找指定内容，底部添加波浪线动画", "文字分离动画","展开/收起文本内容")
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(items = funItemList) { index, item ->
            CardItem(text = item) {
                onItemClick.invoke(item)
            }
            if (index < funItemList.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun CardItem(text: String, onItemClick: () -> Unit) {
    val currentOnItemClick by rememberUpdatedState(newValue = onItemClick)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .rippleClickable(currentOnItemClick)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .align(Alignment.CenterStart),
            style = MaterialTheme.typography.bodyLarge
                .copy(color = Color.Black.copy(alpha = 0.66F))
        )
    }
}