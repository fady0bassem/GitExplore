package com.fadybassem.gitexplore.presentation.ui.authentication.forgot_pssword_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.components.button.SolidOutlinedButton
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.components.text.ErrorValidationText
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedLabeledOutlinedTextField
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.Language


@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "en")
@Composable
private fun ForgotPasswordPreview() {
    AppTheme(language = Language.ENGLISH) {
        ForgotPasswordView(emailTextState = remember { mutableStateOf("john_doe@xyz.com") },
            emailTextStateError = remember { mutableStateOf(Pair(false, "")) },
            onValidateEmail = {},
            onClickSubmit = {})
    }
}

@Composable
internal fun ForgotPasswordView(
    emailTextState: MutableState<String>,
    emailTextStateError: MutableState<Pair<Boolean, String>>,
    onValidateEmail: () -> Unit,
    onClickSubmit: () -> Unit,
) {

    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val windowInfo = rememberWindowInfo()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        // logo image
        Image(
            painter = painterResource(id = R.drawable.colored_logo),
            contentDescription = stringResource(
                id = R.string.content_description, "logo"
            ),
            modifier = Modifier
                .width(windowInfo.windowDimensions.verticalPadding * 25)
                .height(windowInfo.windowDimensions.verticalPadding * 25)
                .align(alignment = Alignment.CenterHorizontally),
            contentScale = ContentScale.Inside,
        )

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        // screen title
        Text(
            modifier = Modifier
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 2,
                    end = windowInfo.windowDimensions.verticalPadding * 2
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.forgot_password),
            color = Black,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

        // email input
        BorderedLabeledOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = windowInfo.windowDimensions.verticalPadding * 2,
                    end = windowInfo.windowDimensions.verticalPadding * 2
                ),
            textState = emailTextState,
            textStateError = emailTextStateError,
            label = stringResource(id = R.string.email),
            showLeadingIcon = true,
            leadingIcon = Icons.Filled.Email,
            leadingIconTint = Color.Black,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                onValidateEmail.invoke()
                if (!emailTextStateError.value.first) {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            })
        )

        ErrorValidationText(textStateError = emailTextStateError)

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding * 2f))

        // submit button
        SolidOutlinedButton(modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = windowInfo.windowDimensions.verticalPadding * 10,
                end = windowInfo.windowDimensions.verticalPadding * 10
            )
            .align(alignment = Alignment.CenterHorizontally),
            textModifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding),
            text = stringResource(id = R.string.submit),
            shapeSize = windowInfo.windowDimensions.verticalPadding * 2,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Black,
            textColor = White,
            textStyle = MaterialTheme.typography.bodySmall,
            onClick = { onClickSubmit.invoke() })

        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))
    }
}