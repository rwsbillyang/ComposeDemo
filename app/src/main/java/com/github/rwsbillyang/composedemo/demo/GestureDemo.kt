package com.github.rwsbillyang.composedemo.demo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


//https://juejin.cn/post/6979777894104956935


@Composable
fun DragGestureDemo() {

    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .size(300.dp)
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .background(Color.Green)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        // 拖动开始
                    },
                    onDragEnd = {
                        // 拖动结束
                    },
                    onDragCancel = {
                        // 拖动取消
                    },
                    onDrag = { change: PointerInputChange, dragAmount: Offset ->
                        // 拖动中
                        offset += dragAmount
                        //若不移动box（即注释掉上面的offset），向上滑动后将产生如下数据，即下一行的previousPosition是上一行的position，dragAmount为两个点的距离
                        //因不移动box，所有坐标值都为同一个参考坐标系下
                        //2023-03-16 11:57:05.163 32245-32245 GestureDemo             D  dragAmount=(0.0，-5.3727417), change: previousPosition:Offset(497.0, 251.0), position:Offset(497.0, 243.6), type=Touch
                        //2023-03-16 11:57:05.185 32245-32245 GestureDemo             D  dragAmount=(0.0，-10.590942), change: previousPosition:Offset(497.0, 243.6), position:Offset(497.0, 233.0), type=Touch
                        //2023-03-16 11:57:05.201 32245-32245 GestureDemo             D  dragAmount=(1.0，-8.073914), change: previousPosition:Offset(497.0, 233.0), position:Offset(498.0, 225.0), type=Touch

                        //若移动box，向下滑动后将产生如下数据， 下一行的previousPosition实际上仍是上一行的点，但因为box位置移动了，故其值也变成了移动offset后新的坐标值，即同一行的previousPosition和position仍在同一个参考坐标系下，二者的位置之差即dragAmount
                        //2023-03-16 12:03:30.692 32404-32404 GestureDemo             D  dragAmount=(-0.31280088，7.111038), change: previousPosition:Offset(489.0, 525.9), position:Offset(488.0, 534.1), type=Touch
                        //2023-03-16 12:03:30.711 32404-32404 GestureDemo             D  dragAmount=(0.015930176，9.41803), change: previousPosition:Offset(488.0, 527.1), position:Offset(488.0, 536.5), type=Touch
                        //2023-03-16 12:03:30.729 32404-32404 GestureDemo             D  dragAmount=(0.0，11.505859), change: previousPosition:Offset(488.0, 526.5), position:Offset(488.0, 538.0), type=Touch
                        //2023-03-16 12:03:30.745 32404-32404 GestureDemo             D  dragAmount=(-1.0，9.291199), change: previousPosition:Offset(488.0, 527.0), position:Offset(487.0, 536.3), type=Touch
                        //2023-03-16 12:03:30.761 32404-32404 GestureDemo             D  dragAmount=(0.0，8.531128), change: previousPosition:Offset(488.0, 526.3), position:Offset(488.0, 534.8), type=Touch
                        //2023-03-16 12:03:30.776 32404-32404 GestureDemo             D  dragAmount=(0.0，8.753723), change: previousPosition:Offset(488.0, 526.8), position:Offset(488.0, 535.6), type=Touch
                        //2023-03-16 12:03:30.791 32404-32404 GestureDemo             D  dragAmount=(0.0，9.138977), change: previousPosition:Offset(488.0, 526.6), position:Offset(488.0, 535.7), type=Touch
                        //2023-03-16 12:03:30.805 32404-32404 GestureDemo             D  dragAmount=(0.0，7.310974), change: previousPosition:Offset(488.0, 526.7), position:Offset(488.0, 534.0), type=Touch
                        //2023-03-16 12:03:30.822 32404-32404 GestureDemo             D  dragAmount=(1.5，6.954529), change: previousPosition:Offset(488.0, 527.0), position:Offset(489.5, 534.0), type=Touch
                        val str = "previousPosition:${change.previousPosition}, position:${change.position}, type=${change.type}"
                        Log.d("GestureDemo", "dragAmount=(${dragAmount.x}，${dragAmount.y}), change: $str")
                    }
                )
            }
        )
    }
}

@Composable
fun TapGestureDemo() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(Modifier
            .size(300.dp)
            .background(Color.Green)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset: Offset ->
                        // 双击
                        //2023-03-16 12:12:38.264 32541-32541 GestureDemo             D  onPress=(136.0，72.0)
                        //2023-03-16 12:12:38.430 32541-32541 GestureDemo             D  onPress=(156.0，71.0)
                        //2023-03-16 12:12:38.492 32541-32541 GestureDemo             D  onDoubleTap=(156.0，71.0)
                        Log.d("GestureDemo", "onDoubleTap=(${offset.x}，${offset.y})")
                    },
                    onLongPress = { offset: Offset ->
                        // 长按
                        //2023-03-16 12:11:18.309 32541-32541 GestureDemo             D  onPress=(115.0，73.0)
                        //2023-03-16 12:11:18.612 32541-32541 GestureDemo             D  onLongPress=(115.0，73.0)
                        Log.d("GestureDemo", "onLongPress=(${offset.x}，${offset.y})")
                    },
                    onPress = {  offset: Offset ->
                        // 按下
                        Log.d("GestureDemo", "onPress=(${offset.x}，${offset.y})")
                    },
                    onTap = {  offset: Offset ->
                        // 轻触
                        //2023-03-16 12:11:11.745 32541-32541 GestureDemo             D  onPress=(81.0，61.0)
                        //2023-03-16 12:11:12.098 32541-32541 GestureDemo             D  onTap=(81.0，61.0)
                        Log.d("GestureDemo", "onTap=(${offset.x}，${offset.y})")
                    }
                )
            }
        )
    }
}


// 双指操作旋转、放大、平移，单指也可平移
@Composable
fun TransformGestureDemo() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var ratationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(Modifier
            .size(300.dp)
            .rotate(ratationAngle) // 需要注意offset与rotate的调用先后顺序
            .scale(scale)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = true, // 平移或放大时是否可以旋转
                    onGesture = { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
                        offset += pan
                        scale *= zoom
                        ratationAngle += rotation
                        
                    //2023-03-16 12:18:30.997 32693-32693 TransformGestureDemo    D  offset: Offset(-5.6, -47.8), scale: 0.5055107, ratationAngle: 4.9005766
                    //2023-03-16 12:18:31.016 32693-32693 TransformGestureDemo    D  offset: Offset(-7.7, -44.9), scale: 0.50776285, ratationAngle: 5.830949
                    //2023-03-16 12:18:31.035 32693-32693 TransformGestureDemo    D  offset: Offset(-8.0, -41.9), scale: 0.51263046, ratationAngle: 6.417468
                    //...
                        Log.d("TransformGestureDemo", "offset: $offset, scale: $scale, ratationAngle: $ratationAngle")
                    }
                )
            }
        )
    }
}

// 双指操作旋转  https://developer.android.google.cn/reference/kotlin/androidx/compose/foundation/gestures/package-summary#(androidx.compose.ui.input.pointer.PointerEvent).calculateCentroid(kotlin.Boolean)
@Composable
fun DemoRotation() {
    var angle by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .background(Color.Blue)
            .graphicsLayer(rotationZ = angle)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                    do {
                        val event = awaitPointerEvent()
                        val rotation = event.calculateRotation()
                        angle += rotation
                        val pos2 = event.changes.joinToString(" ") { "(${it.position.x}, ${it.position.y})"  }
                        Log.d("GestureDemo", "rotation=$rotation，angle=$angle. type=${event.type}, $pos2")
                    } while (event.changes.any { it.pressed })
                }
            }.fillMaxSize()
    ){
        Text(text = "永远相信美好的事情即将发生❤️")
    }
}
// 双指操作旋转 https://developer.android.google.cn/reference/kotlin/androidx/compose/foundation/gestures/package-summary#(androidx.compose.ui.input.pointer.PointerEvent).calculateCentroid(kotlin.Boolean)
@Composable
fun DemoZoom() {
    var zoom by remember { mutableStateOf(1f) }
    Box(
        Modifier
            .background(Color.Blue)
            .graphicsLayer(scaleX = zoom, scaleY = zoom)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                    do {
                        val event = awaitPointerEvent()
                        zoom *= event.calculateZoom()
                    } while (event.changes.any { it.pressed })
                }
            }.fillMaxSize()
    ){
        Text(text = "永远相信美好的事情即将发生❤️")
    }
}

@Composable
fun NestedBoxDemo() {
    //单点一下且只能执行一次：
    //2023-03-16 14:27:02.394 12140-12140 GestureDemo    D  first layer: awaitPointerEvent: previousPosition=Offset(414.0, 728.0), position=Offset(414.0, 728.0), uptimeMillis=280244542, type=Touch, currentEvent: previousPosition=Offset(414.0, 728.0), position=Offset(414.0, 728.0), uptimeMillis=280244542, type=Touch
    //2023-03-16 14:27:02.396 12140-12140 GestureDemo    D  third layer: awaitPointerEvent: previousPosition=Offset(254.0, 203.0), position=Offset(254.0, 203.0), uptimeMillis=280244542, type=Touch, currentEvent: previousPosition=Offset(254.0, 203.0), position=Offset(254.0, 203.0), uptimeMillis=280244542, type=Touch
    //2023-03-16 14:27:02.397 12140-12140 GestureDemo    D  second layer: awaitPointerEvent: previousPosition=Offset(414.0, 403.0), position=Offset(414.0, 403.0), uptimeMillis=280244542, type=Touch, currentEvent: previousPosition=Offset(414.0, 403.0), position=Offset(414.0, 403.0), uptimeMillis=280244542, type=Touch

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    val e = awaitPointerEvent(PointerEventPass.Initial)

                    val str1 = e.changes.joinToString(" . ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                    val str2 = currentEvent.changes.joinToString(" . ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                    Log.d("GestureDemo", "first layer: awaitPointerEvent: $str1, currentEvent: $str2")

                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        val e =awaitPointerEvent(PointerEventPass.Final)

                        val str1 = e.changes.joinToString(" . ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                        val str2 = currentEvent.changes.joinToString(" . ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                        Log.d("GestureDemo", "second layer: awaitPointerEvent: $str1, currentEvent: $str2")

                    }
                }
        ) {
            Box(
                Modifier
                    .size(200.dp)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            val e = awaitPointerEvent()

                            val str1 = e.changes.joinToString(" | ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                            val str2 = currentEvent.changes.joinToString(" . ") { "previousPosition=${it.previousPosition}, position=${it.position}, uptimeMillis=${it.uptimeMillis}, type=${it.type}" }
                            Log.d("GestureDemo", "third layer: awaitPointerEvent: $str1, currentEvent: $str2")

                        }
                    }
            )
        }
    }
}


@Composable
fun DemoGestureConsume() {
    //单点一下且只能执行一次：
    //2023-03-16 14:50:31.983 13703-13703 GestureDemo    D  first layer, downChange: false
    //2023-03-16 14:50:31.985 13703-13703 GestureDemo    D  third layer, downChange: true
    //2023-03-16 14:50:31.986 13703-13703 GestureDemo    D  second layer, downChange: true
    //2023-03-16 14:50:32.000 13703-13703 GestureDemo    D  first layer Outside
    //2023-03-16 14:50:32.000 13703-13703 GestureDemo    D  third layer Outside
    //2023-03-16 14:50:32.001 13703-13703 GestureDemo    D  second layer Outside
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    val event = awaitPointerEvent(PointerEventPass.Initial)
                    Log.d("GestureDemo", "first layer, downChange: ${event.changes[0].isConsumed}")
                }
                Log.d("GestureDemo", "first layer Outside")
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        val event = awaitPointerEvent(PointerEventPass.Final)
                        Log.d("GestureDemo", "second layer, downChange: ${event.changes[0].isConsumed}")
                    }
                    Log.d("GestureDemo", "second layer Outside")
                }
        ) {
            Box(
                Modifier
                    .size(200.dp)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            val event = awaitPointerEvent()
                            val t = event.changes[0]
                            if (t.pressed != t.previousPressed) t.consume()
                            Log.d("GestureDemo", "third layer, downChange: ${t.isConsumed}")
                        }
                        Log.d("GestureDemo", "third layer Outside")
                    }
            )
        }
    }
}


@Composable
fun BaseDragGestureDemo() {

    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .size(300.dp)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                // 循环监听每一组事件序列
                awaitEachGesture {
                    val downEvent = awaitFirstDown()//等待第一根手指按下事件时恢复执行，并将手指按下事件返回

                    //drag 需要主动传入一个 PointerId 用以表示要具体获取到哪根手指的拖动事件
                    drag(downEvent.id) {
                        offset += it.positionChange()

                        Log.d("GestureDemo", "after drag: $it")
                    }
                }
            }
        )
    }
}
