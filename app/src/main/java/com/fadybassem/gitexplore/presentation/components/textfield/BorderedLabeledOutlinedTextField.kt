package com.fadybassem.gitexplore.presentation.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.presentation.theme.AppTheme
import com.fadybassem.gitexplore.presentation.theme.Black
import com.fadybassem.gitexplore.presentation.theme.SilverSand

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun BorderedLabeledOutlinedTextFieldPreview() {
    AppTheme {
        BorderedLabeledOutlinedTextField(
            modifier = Modifier,
            textState = remember { mutableStateOf("") },
            textStateError = remember { mutableStateOf(Pair(false, "")) },
            label = "Email",
            showLeadingIcon = false,
            leadingIcon = Icons.Filled.Email,
            leadingIconTint = Color.Black,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {

            }),
        )
    }
}

@Composable
fun BorderedLabeledOutlinedTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String>,
    textStateError: MutableState<Pair<Boolean, String>>?,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    label: String,
    showLeadingIcon: Boolean = false,
    showTrailingIcon: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: ImageVector = Icons.Filled.Image,
    trailingIcon: ImageVector = Icons.Filled.Image,
    leadingIconTint: Color = Color.Black,
    trailingIconTint: Color = Color.Black,
    trailingIconClickable: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    minLines: Int = 1,
    onTrailingIconClick: (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = {
            textState.value = it
            textStateError?.value = Pair(false, "")
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
        trailingIcon = {
            if (showTrailingIcon) {
                Icon(
                    modifier = Modifier.clickable(trailingIconClickable){
                        onTrailingIconClick?.invoke()
                    },
                    painter = rememberVectorPainter(trailingIcon),
                    contentDescription = stringResource(id = R.string.content_description, label),
                    tint = trailingIconTint
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
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = if (textStateError?.value?.first == true) MaterialTheme.colorScheme.error else Color.Black,
            disabledTextColor = if (textStateError?.value?.first == true) MaterialTheme.colorScheme.error else Color.Black,
        ),
        isError = textStateError?.value?.first == true,
        readOnly = readOnly,
        enabled = enabled,
        minLines = minLines,
    )
}