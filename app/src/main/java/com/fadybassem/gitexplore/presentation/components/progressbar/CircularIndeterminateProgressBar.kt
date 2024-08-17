package com.fadybassem.gitexplore.presentation.components.progressbar

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.fadybassem.gitexplore.presentation.theme.AppTheme

private const val progressBar = "progressBar"
private const val text = "text"

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CircularIndeterminateProgressBarPreview() {
    AppTheme {
        CircularIndeterminateProgressBar(isDisplayed = true)
    }
}

@Composable
fun CircularIndeterminateProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val constraints = if (minWidth < 600.dp) { // portrait
                myDecoupledConstraints(0.5f)
            } else {
                myDecoupledConstraints(0.5f)
            }

            ConstraintLayout(modifier = Modifier.fillMaxSize(), constraintSet = constraints) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary, modifier = Modifier.layoutId(
                        progressBar
                    )
                )

                /*
                                Text(
                                    text = "Loading...",
                                    style = TextStyle(color = Color.Black, fontSize = 15.sp),
                                    modifier = Modifier.padding(5.dp).layoutId(text))
                */
            }
        }

        /*ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (progressBar, text) = createRefs()
            val topGuideline = createGuidelineFromTop(0.4f)

            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = "Loading...",
                style = TextStyle(color = Color.Black, fontSize = 15.sp),
                modifier = Modifier
                    .padding(5.dp)
                    .constrainAs(text) {
                        top.linkTo(progressBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }*/

        /*Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }*/
    }
}

private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor(progressBar)
        val text = createRefFor(text)

        constrain(progressBar) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text) {
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}