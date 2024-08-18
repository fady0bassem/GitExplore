package com.fadybassem.gitexplore.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.presentation.theme.AppTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SolidOutlinedButtonPreview() {
    AppTheme {
        SolidOutlinedButton(text = "Button", onClick = {})
    }
}

@Composable
fun SolidOutlinedButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    shapeSize: Dp = 25.dp,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    textColor: Color = MaterialTheme.colorScheme.inversePrimary,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    textFontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedButton(
            modifier = modifier,
            onClick = { onClick.invoke() },
            border = BorderStroke(0.dp, containerColor),
            shape = RoundedCornerShape(shapeSize),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = containerColor,
                contentColor = contentColor,
            )
        ) {
            Text(
                modifier = textModifier,
                text = text,
                color = textColor,
                style = textStyle,
                fontWeight = textFontWeight,
                textAlign = textAlign
            )
        }
    }
}