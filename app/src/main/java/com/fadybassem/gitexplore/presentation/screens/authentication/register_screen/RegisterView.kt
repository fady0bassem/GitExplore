package com.fadybassem.gitexplore.presentation.screens.authentication.register_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import com.fadybassem.gitexplore.presentation.components.textfield.BorderedPasswordOutlinedTextField
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.presentation.theme.White
import com.fadybassem.gitexplore.utils.Language

@Preview(apiLevel = 33, showBackground = true, showSystemUi = true)
@Composable
private fun RegisterPreview() {
    AppTheme(language = Language.ARABIC) {
        RegisterView(
            firstNameTextState = remember { mutableStateOf("John") },
            lastNameTextState = remember { mutableStateOf("Doe") },
            emailTextState = remember { mutableStateOf("john_doe@xyz.com") },
            passwordTextState = remember { mutableStateOf("123456") },
            confirmPasswordTextState = remember { mutableStateOf("123456") },
            passwordVisibility = remember { mutableStateOf(true) },
            confirmPasswordVisibility = remember { mutableStateOf(true) },
            firstNameTextStateError = remember { mutableStateOf(Pair(false, "first name error")) },
            lastNameTextStateError = remember { mutableStateOf(Pair(false, "last name error")) },
            emailTextStateError = remember { mutableStateOf(Pair(false, "email error")) },
            passwordTextStateError = remember { mutableStateOf(Pair(false, "password error")) },
            confirmPasswordTextStateError = remember {
                mutableStateOf(
                    Pair(
                        false, "confirm password error"
                    )
                )
            },
            passwordMatchTextStateError = remember {
                mutableStateOf(
                    Pair(
                        false, "password do not match"
                    )
                )
            },
            onValidateFirstName = {},
            onValidateLastName = {},
            onValidateEmail = {},
            onValidatePassword = {},
            onValidatePasswordMatch = {},
            onValidateConfirmPassword = {},
            onClickSignUp = {},
        )
    }
}

@Composable
internal fun RegisterView(
    firstNameTextState: MutableState<String>,
    lastNameTextState: MutableState<String>,
    emailTextState: MutableState<String>,
    passwordTextState: MutableState<String>,
    confirmPasswordTextState: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    confirmPasswordVisibility: MutableState<Boolean>,
    firstNameTextStateError: MutableState<Pair<Boolean, String>>,
    lastNameTextStateError: MutableState<Pair<Boolean, String>>,
    emailTextStateError: MutableState<Pair<Boolean, String>>,
    passwordTextStateError: MutableState<Pair<Boolean, String>>,
    confirmPasswordTextStateError: MutableState<Pair<Boolean, String>>,
    passwordMatchTextStateError: MutableState<Pair<Boolean, String>>,
    onValidateFirstName: () -> Unit,
    onValidateLastName: () -> Unit,
    onValidateEmail: () -> Unit,
    onValidatePassword: () -> Unit,
    onValidatePasswordMatch: () -> Unit,
    onValidateConfirmPassword: () -> Unit,
    onClickSignUp: () -> Unit,
) {

    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val windowInfo = rememberWindowInfo()

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

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

                // screen title
                Text(
                    modifier = Modifier
                        .padding(
                            start = windowInfo.windowDimensions.verticalPadding * 2,
                            end = windowInfo.windowDimensions.verticalPadding * 2
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.sign_up),
                    color = Black,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

                // first name input
                BorderedLabeledOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = windowInfo.windowDimensions.verticalPadding * 2,
                            end = windowInfo.windowDimensions.verticalPadding * 2
                        ),
                    textState = firstNameTextState,
                    textStateError = firstNameTextStateError,
                    label = stringResource(id = R.string.first_name),
                    showLeadingIcon = true,
                    leadingIcon = Icons.Filled.Person,
                    leadingIconTint = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        onValidateFirstName.invoke()
                        if (!firstNameTextStateError.value.first) {
                            localFocusManager.moveFocus(FocusDirection.Next)
                        }
                    })
                )

                // first name error
                ErrorValidationText(textStateError = firstNameTextStateError)

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

                // last name input
                BorderedLabeledOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = windowInfo.windowDimensions.verticalPadding * 2,
                            end = windowInfo.windowDimensions.verticalPadding * 2
                        ),
                    textState = lastNameTextState,
                    textStateError = lastNameTextStateError,
                    label = stringResource(id = R.string.last_name),
                    showLeadingIcon = true,
                    leadingIcon = Icons.Filled.Person,
                    leadingIconTint = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        onValidateLastName.invoke()
                        if (!lastNameTextStateError.value.first) {
                            localFocusManager.moveFocus(FocusDirection.Down)
                        }
                    })
                )

                // last name error
                ErrorValidationText(textStateError = lastNameTextStateError)

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

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

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
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        onValidatePassword.invoke()
                        onValidatePasswordMatch.invoke()
                        if (!passwordTextStateError.value.first) {
                            localFocusManager.moveFocus(FocusDirection.Next)
                        }
                    })
                )

                ErrorValidationText(textStateError = passwordTextStateError)

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

                // confirm password input
                BorderedPasswordOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = windowInfo.windowDimensions.verticalPadding * 2,
                            end = windowInfo.windowDimensions.verticalPadding * 2
                        ),
                    textState = confirmPasswordTextState,
                    textStateError = confirmPasswordTextStateError,
                    passwordVisibility = confirmPasswordVisibility,
                    label = stringResource(id = R.string.confirm_password),
                    showLeadingIcon = true,
                    leadingIcon = Icons.Filled.Lock,
                    leadingIconTint = Color.Black,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        onValidateConfirmPassword.invoke()
                        onValidatePasswordMatch.invoke()
                        if (!confirmPasswordTextStateError.value.first && !passwordMatchTextStateError.value.first) {
                            localFocusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    })
                )

                ErrorValidationText(textStateError = confirmPasswordTextStateError)

                ErrorValidationText(textStateError = passwordMatchTextStateError)

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

                // sign up button
                SolidOutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = windowInfo.windowDimensions.verticalPadding * 2,
                        end = windowInfo.windowDimensions.verticalPadding * 2
                    )
                    .align(alignment = Alignment.CenterHorizontally),
                    textModifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding),
                    text = stringResource(id = R.string.sign_up),
                    shapeSize = windowInfo.windowDimensions.verticalPadding * 2,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Black,
                    textColor = White,
                    textStyle = MaterialTheme.typography.bodySmall,
                    onClick = {
                        localFocusManager.clearFocus()
                        keyboardController?.hide()
                        onClickSignUp.invoke()
                    })

                Spacer(modifier = Modifier.padding(windowInfo.windowDimensions.verticalPadding))

            }
        }
    }
}