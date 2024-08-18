package com.fadybassem.gitexplore.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fadybassem.gitexplore.presentation.theme.AppTheme

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TransparentOutlinedIconButtonPreview() {
    AppTheme {
        TransparentOutlinedIconButton(modifier = Modifier, text = "Button", onClick = {})
    }
}

@Composable
fun TransparentOutlinedIconButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    textFontWeight: FontWeight = FontWeight.Bold,
    icon: ImageVector = Icons.Filled.Home,
    iconTint: Color = Color.Black,
    onClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedIconButton(
            modifier = modifier,
            onClick = { onClick.invoke() },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(25.dp),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier,
                    painter = rememberVectorPainter(image = icon),
                    tint = iconTint,
                    contentDescription = "copy code icon"
                )

                text?.let {
                    Text(
                        modifier = textModifier,
                        text = it,
                        color = textColor,
                        style = textStyle,
                        fontWeight = textFontWeight
                    )
                }
            }
        }
    }
}