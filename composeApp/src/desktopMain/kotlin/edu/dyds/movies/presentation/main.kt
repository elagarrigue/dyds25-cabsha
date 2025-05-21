package edu.dyds.movies.presentation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import edu.dyds.movies.presentation.utils.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DYDSProject",
    ) {
        App()
    }
}