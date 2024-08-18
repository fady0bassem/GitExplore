package com.fadybassem.gitexplore.presentation.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fadybassem.gitexplore.presentation.theme.AppTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ConfirmDialogPreview() {
    AppTheme {
        ConfirmDialog(
            titleText = "Warning",
            infoText = "Are you sure you want to delete?",
            actionPositiveText = "Ok",
            actionNegativeText = "Cancel",
            onPositiveActionClick = {},
            onNegativeActionClick = {},
            onDismiss = {},
        )
    }
}

@Composable
fun ConfirmDialog(
    titleText: String? = null,
    infoText: String,
    titleColor: Color = MaterialTheme.colorScheme.primary,
    infoColor: Color = MaterialTheme.colorScheme.onSurface,
    actionPositiveText: String,
    actionNegativeText: String,
    onPositiveActionClick: () -> Unit,
    onNegativeActionClick: () -> Unit,
    onDismiss: () -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
    ),
) {
    Dialog(onDismissRequest = onDismiss, properties = properties) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.padding(8.dp))

                // title optional
                titleText?.let {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.padding(8.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        visible = it.isNotEmpty(),
                        enter = fadeIn(initialAlpha = 0.4f),
                    ) {
                        Text(
                            text = titleText.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp, end = 0.dp)
                                .align(alignment = Alignment.CenterHorizontally),
                            color = titleColor,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                // info text
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = infoText,
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterVertically)
                            .weight(1f),
                        onClick = { onPositiveActionClick() },
                        border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = actionPositiveText,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterVertically)
                            .weight(1f),
                        onClick = { onNegativeActionClick() },
                        border = BorderStroke(0.dp, MaterialTheme.colorScheme.onPrimary),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = actionNegativeText,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}