package com.github.rwsbillyang.composedemo.demo2


import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * GesturesExtensions
 * @author TheMelody
 * email developer_melody@163.com
 * created 2022/9/6 21:44
 */
internal suspend fun PointerInputScope.detectTouchGestures(
    onDown: ((Offset) -> Unit),
    onMove: ((Offset) -> Unit),
    onUp: (Offset) -> Unit
): Unit = coroutineScope {
    launch {
        forEachGesture {
            awaitPointerEventScope {
                val down = awaitFirstDown().also { it.consume() }
                // ACTION_DOWN
                onDown.invoke(down.position)

                var pointer = down
                var pointerId = down.id

                // 第一次触摸后添加延迟
                var waitedAfterDown = false

                launch {
                    delay(16)
                    waitedAfterDown = true
                }
                while (true) {
                    val event: PointerEvent = awaitPointerEvent()
                    if (event.changes.any { it.pressed }) {
                        val pointerInputChange =
                            event.changes.firstOrNull { it.id == pointerId }
                                ?: event.changes.first()
                        pointerId = pointerInputChange.id
                        pointer = pointerInputChange
                        if (waitedAfterDown) {
                            // ACTION_MOVE
                            onMove(pointer.position)
                        }
                    } else {
                        // ACTION_UP
                        onUp.invoke(pointer.position)
                        break
                    }
                }
            }
        }
    }
}