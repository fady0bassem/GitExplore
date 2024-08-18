package com.fadybassem.gitexplore.presentation.components.radiobutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.presentation.theme.AppTheme

@Preview(showBackground = true, showSystemUi = true, locale = "en")
@Composable
private fun HorizontalRadioButtonsPreview() {
    AppTheme {

        val radioOptions = listOf("A", "B", "C")
        val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
            mutableStateOf(
                radioOptions[1]
            )
        }

        HorizontalRadioButtons(
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
    }
}

@Composable
fun HorizontalRadioButtons(
    radioOptions: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit,
) {
    Column {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(selected = (text == selectedOption), onClick = {
                        onOptionSelected(text)
                    })
                    .padding(horizontal = 0.dp)
                    .align(Alignment.CenterHorizontally)) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) })
                Text(
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .align(Alignment.CenterVertically),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.merge()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "en")
@Composable
private fun VerticalRadioButtonsPreview() {
    AppTheme {

        val radioOptions = listOf("A", "B", "C")
        val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
            mutableStateOf(radioOptions[2])
        }

        VerticalRadioButtons(
            radioOptions = radioOptions,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
    }
}

@Composable
fun VerticalRadioButtons(
    radioOptions: List<String>, selectedOption: String?, onOptionSelected: (String) -> Unit,
) {
    Row {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .selectable(selected = (text == selectedOption), onClick = {
                        onOptionSelected(text)
                    })
                    .padding(horizontal = 0.dp)
                    .align(Alignment.CenterVertically)) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) })
                Text(
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .align(Alignment.CenterVertically),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.merge()
                )
            }
        }
    }
}