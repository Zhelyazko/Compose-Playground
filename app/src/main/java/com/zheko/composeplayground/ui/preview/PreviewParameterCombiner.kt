package com.zheko.composeplayground.ui.preview

import androidx.ui.tooling.preview.PreviewParameterProvider

open class PreviewParameterCombiner<T, U>(
    private val first: PreviewParameterProvider<T>,
    private val second: PreviewParameterProvider<U>,
) : PreviewParameterProvider<Pair<T, U>> {
    override val values = first.values.flatMap { firstValue ->
        second.values.map { secondValue ->
            firstValue to secondValue
        }
    }
}