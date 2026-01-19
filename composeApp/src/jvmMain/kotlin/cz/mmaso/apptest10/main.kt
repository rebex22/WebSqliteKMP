package cz.mmaso.apptest10

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AppTest10",
    ) {
        App()
    }
}