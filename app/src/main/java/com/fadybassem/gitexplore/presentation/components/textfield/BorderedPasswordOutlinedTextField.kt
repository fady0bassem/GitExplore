package com.fadybassem.gitexplore.presentation.components.textfield

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.presentation.theme.SilverSand

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BorderedPasswordOutlinedTextFieldPreview() {
    AppTheme {
        BorderedPasswordOutlinedTextField(
            modifier = Modifier,
            textState = remember { mutableStateOf("") },
            textStateError = remember { mutableStateOf(Pair(false, "")) },
            passwordVisibility = remember { mutableStateOf(true) },
            label = "Email",
            showLeadingIcon = true,
            leadingIcon = Icons.Filled.Email,
            leadingIconTint = Color.Black,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {

            })
        )
    }
}

@Composable
fun BorderedPasswordOutlinedTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String>,
    textStateError: MutableState<Pair<Boolean, String>>,
    passwordVisibility: MutableState<Boolean>,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    label: String,
    showLeadingIcon: Boolean = false,
    leadingIcon: ImageVector,
    leadingIconTint: Color = Color.Black,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = {
            textState.value = it
            textStateError.value = Pair(false, "")
        },
        shape = shape,
        label = {
            Text(
                text = label, style = MaterialTheme.typography.labelMedium, color = SilverSand
            )
        },
        leadingIcon = {
            if (showLeadingIcon) {
                Icon(
                    painter = rememberVectorPainter(leadingIcon),
                    contentDescription = stringResource(id = R.string.content_description, label),
                    tint = leadingIconTint
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(color = Black),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            errorContainerColor = Color.Transparent,
        ),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisibility.value) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(
                    imageVector = image, "password visibility icon button"
                )
            }
        },
        isError = textStateError.value.first,
    )
}