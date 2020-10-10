package com.zheko.composeplayground.ui.preview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import androidx.ui.tooling.preview.PreviewParameter
import androidx.ui.tooling.preview.PreviewParameterProvider
import androidx.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.ui.tooling.preview.datasource.LoremIpsum
import com.zheko.composeplayground.Greeting
import com.zheko.composeplayground.ui.resources.ComposePlaygroundTheme

// Preview with 1 input parameter

class NameProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "Darth Vader",
        "Luke Skywalker",
        "Paodok’Draba’Takat Sap’De’Rekti Nik’Linke’Ti’ Ki’Vef’Nik’NeSevef’Li’Kek"
    )
}

@Preview
@Composable
fun PreviewWithNames(@PreviewParameter(NameProvider::class) name: String) {
    Greeting(name)
}

// Preview with more than 1 input parameter

// Option 1

data class NameAndDarkTheme(
    val name: String,
    val isDarkTheme: Boolean
)

class NameAndDarkThemeParamProvider : CollectionPreviewParameterProvider<NameAndDarkTheme>(
    listOf(
        NameAndDarkTheme("Darth Vader", true),
        NameAndDarkTheme("Luke Skywalker", true),
        NameAndDarkTheme("Paodok’Draba’Takat Sap’De’Rekti Nik’Linke’Ti’ Ki’Vef’Nik’NeSevef’Li’Kek", true),
        NameAndDarkTheme("Darth Vader", false),
        NameAndDarkTheme("Luke Skywalker", false),
        NameAndDarkTheme("Paodok’Draba’Takat Sap’De’Rekti Nik’Linke’Ti’ Ki’Vef’Nik’NeSevef’Li’Kek", false)
    )
)

@Preview
@Composable
fun PreviewWithNames(@PreviewParameter(NameAndDarkThemeParamProvider::class) args: NameAndDarkTheme) {
    ComposePlaygroundTheme(darkTheme = args.isDarkTheme) {
        Surface(color = MaterialTheme.colors.background) {
            Greeting(args.name)
        }
    }
}


// Option 2

class DarkThemePreviewParamProvider : CollectionPreviewParameterProvider<Boolean>(
    listOf(true, false)
)

class NameAndDarkModeProvider :
    PreviewParameterCombiner<String, Boolean>(NameProvider(), DarkThemePreviewParamProvider())

@Preview
@Composable
fun PreviewWithNamesAndDarkMode(
    @PreviewParameter(NameAndDarkModeProvider::class) nameAndDarkMode: Pair<String, Boolean>
) {
    ComposePlaygroundTheme(darkTheme = nameAndDarkMode.second) {
        Surface(color = MaterialTheme.colors.background) {
            Greeting(nameAndDarkMode.first)
        }
    }
}

@Preview(
    name = "Lorem Ipsum"
)
@Composable
fun PreviewWithLoremIpsum(@PreviewParameter(LoremIpsum::class) loremIpsum: String) {
    ComposePlaygroundTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greeting(loremIpsum)
        }
    }
}

