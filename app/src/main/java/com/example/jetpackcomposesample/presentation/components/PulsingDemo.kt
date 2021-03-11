package com.example.jetpackcomposesample.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.*
import androidx.compose.animation.*

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.infiniteRepeatable
import com.example.jetpackcomposesample.presentation.components.PulseAnimationDefinitions.PulseState.*


@Composable
fun PulsingDemo(){
    /**
     * PropKeys are used in Jetpack Compose animations to hold properties that are going to be
     * updated by the animation transitions. In this example, we use a [FloatPropKey] to hold a float
     * value that represents the value of rotation.
     */

}

object PulseAnimationDefinitions {

    enum class PulseState {
        INITIAL, FINAL
    }
}
//    val pulsePropKey = FloatPropKey("pulseKey")
//
//    val pulseDefinition = transitionDefinition<PulseState> {
//        state(INITIAL) { this[pulsePropKey] = 40f }
//        state(FINAL) { this[pulsePropKey] = 50f }
//
//        transition(
//            INITIAL to FINAL,
//        ) {
//            pulsePropKey using infiniteRepeatable(
//                animation = tween(
//                    durationMillis = 500,
//                    easing = FastOutSlowInEasing
//                ),
//                repeatMode = RepeatMode.Restart
//            )
//        }
//    }
//}


