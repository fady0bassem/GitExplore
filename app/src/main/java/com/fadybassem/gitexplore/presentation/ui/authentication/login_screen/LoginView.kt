package com.fadybassem.gitexplore.presentation.ui.authentication.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.components.button.SolidOutlinedButton
import com.fadybassem.gitexplore.presentation.components.button.TransparentOutlinedButton
import com.fadybassem.gitexplore.presentation.components.screen_size.rememberWindowInfo
import com.fadybassem.gitexplore.presentation.components.text.ErrorValidationText
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedLabeledOutlinedTextField
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedPasswordOutlinedTextField
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.presentation.theme.SilverSand
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.Language

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun LoginPreview() {
    AppTheme(language = Language.ARABIC) {
        LoginView(
            language = remember { mutableStateOf(Language.ENGLISH) },
            version = "version number",
            emailTextState = remember { mutableStateOf("john_doe@xyz.com") },
            passwordTextState = remember { mutableStateOf("123456") },
            passwordVisibility = remember { mutableStateOf(true) },
            emailTextStateError = remember { mutableStateOf(Pair(false, "")) },
            passwordTextStateError = remember { mutableStateOf(Pair(false, "")) },
            onLanguageChange = {},
            onValidateEmail = {},
            onValidatePassword = {},
            onClickSignIn = {},
            onClickSignOut = {},
            onClickForgotPassword = {},
        )
    }
}

@Composable
internal fun LoginView(
    language: MutableState<Language>,
    version: String,
    emailTextState: MutableState<String>,
    passwordTextState: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    emailTextStateError: MutableState<Pair<Boolean, String>>,
    passwordTextStateError: MutableState<Pair<Boolean, String>>,
    onLanguageChange: () -> Unit,
    onValidateEmail: () -> Unit,
    onValidatePassword: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickSignOut: () -> Unit,
    onClickForgotPassword: () -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val windowInfo = rememberWindowInfo()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
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

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding * 1.5f))

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
                            label = stringResource(id = R.string.email_placeholder),
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

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding * 1.5f))

                        // password input
                        BorderedPasswordOutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = windowInfo.windowDimensions.verticalPadding * 2,
                                    end = windowInfo.windowDimensions.verticalPadding * 2
                                ),
                            textState = passwordTextState,
                            textStateError = passwordTextStateError,
                            passwordVisibility = passwordVisibility,
                            label = stringResource(id = R.string.password_placeholder),
                            showLeadingIcon = true,
                            leadingIcon = Icons.Filled.Lock,
                            leadingIconTint = Color.Black,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                onValidatePassword.invoke()
                                if (!passwordTextStateError.value.first) {
                                    keyboardController?.hide()
                                }
                            })
                        )

                        ErrorValidationText(textStateError = passwordTextStateError)

                        Text(
                            text = stringResource(id = R.string.login_forgot_password),
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(
                                    start = windowInfo.windowDimensions.verticalPadding * 2,
                                    end = windowInfo.windowDimensions.verticalPadding * 2
                                )
                                .align(alignment = Alignment.End)
                                .clickable { onClickForgotPassword.invoke() },
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.SemiBold

                        )

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding * 2f))

                        // sign in button
                        SolidOutlinedButton(modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = windowInfo.windowDimensions.verticalPadding * 10,
                                end = windowInfo.windowDimensions.verticalPadding * 10
                            )
                            .align(alignment = Alignment.CenterHorizontally),
                            textModifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding),
                            text = stringResource(id = R.string.sign_in),
                            shapeSize = windowInfo.windowDimensions.verticalPadding * 2,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Black,
                            textColor = White,
                            textStyle = MaterialTheme.typography.bodySmall,
                            onClick = { onClickSignIn.invoke() })

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

                        // or
                        Text(
                            text = stringResource(id = R.string.or),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = windowInfo.windowDimensions.verticalPadding * 2,
                                    end = windowInfo.windowDimensions.verticalPadding * 2
                                )
                                .align(alignment = Alignment.CenterHorizontally),
                            color = Black,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding / 2))

                        // sign up button
                        TransparentOutlinedButton(modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = windowInfo.windowDimensions.verticalPadding * 10,
                                end = windowInfo.windowDimensions.verticalPadding * 10
                            )
                            .align(alignment = Alignment.CenterHorizontally),
                            textModifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding),
                            text = stringResource(id = R.string.sign_up),
                            shapeSize = windowInfo.windowDimensions.verticalPadding * 2,
                            containerColor = MaterialTheme.colorScheme.primary,
                            //contentColor = SignOutButtonContentColor,
                            textColor = Black,
                            textStyle = MaterialTheme.typography.bodySmall,
                            onClick = {
                                localFocusManager.clearFocus()
                                keyboardController?.hide()
                                onClickSignOut.invoke()
                            })

                        Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding * 8))
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // version text
            Text(
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .align(Alignment.CenterHorizontally),
                text = version,
                color = SilverSand,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}