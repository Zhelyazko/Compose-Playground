package com.zheko.composeplayground.ui.preview

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        backgroundColor = if (count > 5) Color.Green else Color.White
    ) {
        Column() {
            Text("I've been clicked $count times")
            Text("How are you")
        }
    }
}

@Preview
@Composable
fun PreviewCounter() {
    val counterState = remember { mutableStateOf(0) }

    Counter(
        count = counterState.value,
        updateCount = { newCount ->
            counterState.value = newCount
        }
    )
}