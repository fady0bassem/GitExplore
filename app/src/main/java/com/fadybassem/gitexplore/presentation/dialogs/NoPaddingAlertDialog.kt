package com.fadybassem.gitexplore.presentation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = properties
    ) {
        Surface(
            modifier = modifier, shape = shape, color = backgroundColor, contentColor = contentColor
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                title?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.titleLarge
                        ProvideTextStyle(textStyle, it)
                    }
                }
                text?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.titleLarge
                        ProvideTextStyle(textStyle, it)
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }
}