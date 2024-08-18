package com.fadybassem.gitexplore.presentation.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.White

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SimpleInfoDialogPreview() {
    AppTheme {
        SimpleInfoDialog(titleText = "Title",
            infoText = "Info",
            showSuccessImage = true,
            actionText = "ok",
            onActionClick = {},
            onDismiss = {})
    }
}

@Composable
fun SimpleInfoDialog(
    titleText: String? = null,
    infoText: String,
    titleColor: Color = MaterialTheme.colorScheme.primary,
    infoColor: Color = MaterialTheme.colorScheme.onSurface,
    showSuccessImage: Boolean? = false,
    actionText: String,
    onDismiss: () -> Unit,
    onActionClick: () -> Unit,
    textAlign: TextAlign = TextAlign.Center,
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
            color = White
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.padding(8.dp))

                // done image
                if (showSuccessImage == true) Image(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

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
                    textAlign = textAlign
                )

                Spacer(modifier = Modifier.padding(8.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { onActionClick() },
                    border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = actionText,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}