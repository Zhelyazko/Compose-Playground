package com.zheko.composeplayground.ui.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import androidx.ui.tooling.preview.PreviewParameter
import androidx.ui.tooling.preview.PreviewParameterProvider
import androidx.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.ui.tooling.preview.datasource.LoremIpsum
import com.zheko.composeplayground.Greeting
import com.zheko.composeplayground.ui.resources.ComposePlaygroundTheme
import com.zheko.composeplayground.ui.resources.teal200

@Preview
@Composable
fun DefaultPreview() {
    Greeting("Android")
}

@Preview(name = "Named Preview")
@Composable
fun PreviewWithName() {
    Greeting("Millennium Falcon")
}

@Preview(
    name = "In a group: Ship 1",
    group = "Star Wars"
)
@Composable
fun PreviewInGroup() {
    Greeting("Death Star")
}

@Preview(
    name = "In a group: Ship 2",
    group = "Star Wars"
)
@Composable
fun PreviewInGroupShip2() {
    Greeting("Millennium Falcon")
}

@Preview(
    name = "Font scale",
    fontScale = 3f
)
@Composable
fun PreviewWithFontScale() {
    Greeting("Font scale")
}

@Preview(
    name = "Default background",
    group = "With Background",
    showBackground = true
)
@Composable
fun PreviewDefaultBackground() {
    Greeting("Default background")
}

@Preview(
    name = "Having a background color",
    group = "With Background",
    showBackground = true,
    backgroundColor = 0xFF03DAC5
)
@Composable
fun PreviewCustomBackgroundColor() {
    Greeting("Background color")
}

@Preview(
    name = "Set width & height",
    showBackground = true,
    widthDp = 360,
    heightDp = 100
)
@Composable
fun PreviewCustomWidthAndHeight() {
    Greeting("Custom width & height")
}

@Preview(
    name = "Default Decoration",
    showDecoration = true
)
@Composable
fun PreviewWithDecoration() {
    Greeting("Decorations")
}

@Preview(
    name = "Nexus 10 frame",
    showDecoration = true,
    device = Devices.NEXUS_10
)
@Composable
fun PreviewWithDecorationAndDeviceFrame() {
    Greeting("Decoration with specific device frame")
}

@Preview(
    name = "Without background"
)
@Preview(
    name = "With background",
    showBackground = true
)
@Composable
fun PreviewWithMultipleAnnotations() {
    Greeting("Multiple Preview annotations")
}
