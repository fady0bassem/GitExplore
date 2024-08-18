package com.fadybassem.gitexplore.presentation.components.text

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo

@Composable
fun ErrorValidationText(textStateError: MutableState<Pair<Boolean, String>>) {
    val windowInfo = rememberWindowInfo()

    Column {
        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Start),
            visible = textStateError.value.first,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            Text(
                text = textStateError.value.second,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}