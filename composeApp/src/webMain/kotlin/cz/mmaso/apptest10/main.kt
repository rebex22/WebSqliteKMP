package cz.mmaso.apptest10

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlin.js.js

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("* WebMain *")
    ComposeViewport {
        App()
    }
}