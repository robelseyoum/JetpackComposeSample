package com.example.jetpackcomposesample.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet



@Composable
fun circularIndeterminateProgressBar(isDisplayed: Boolean, verticalBias: Float) {
    if (isDisplayed) {
        ConstraintLayout(modifier = Modifier.fillMaxSize())
        {
            val (progressBar, text) = createRefs()
            val topBias = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topBias)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun circularIndeterminateProgressBar2(isDisplayed: Boolean) {
    if (isDisplayed) {
        ConstraintLayout(modifier = Modifier.fillMaxSize())
        {
            val (progressBar, text) = createRefs()
            val topGuideline = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Loading...",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(progressBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}
/** Here below the same task but it will be used to control the layout based on the screen
 * orientation it helps to decouple the Constraint Layout
 */
@Composable
fun decoupleCircularIndeterminateProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ){
            val constraints = if (minWidth < 600.dp){
                myDecoupledConstraintSet(0.3f)
            } else {
                myDecoupledConstraintSet(0.7f)
            }
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
                constraintSet = constraints
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"),
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Loading...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.layoutId("text")
                )
            }
        }
    }
}

private fun myDecoupledConstraintSet(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("text")

        constrain(progressBar){
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text){
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    }
}