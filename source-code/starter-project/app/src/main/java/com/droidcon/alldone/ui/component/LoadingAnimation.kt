package com.droidcon.alldone.ui.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.R
import com.droidcon.alldone.ui.theme.AllDoneTheme

object LoadingAnimation {
    @Composable
    operator fun invoke(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedLoadingIndicator(modifier.clearAndSetSemantics {  })
            Text(
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Polite
                },
                text = stringResource(id = R.string.to_do_list_loading)
            )
        }
    }

    @Composable
    private fun AnimatedLoadingIndicator(modifier: Modifier = Modifier) {
        val infiniteTransition =
            rememberInfiniteTransition(label = "AnimatedLoadingIndicator_InfiniteTransition")

        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 500
                }
            ), label = "AnimatedLoadingIndicator_InfiniteTransition_AnimateFloat"
        )

        CircularProgressIndicator(
            progress = 1f,
            modifier = modifier
                .size(48.dp)
                .rotate(angle)
                .border(
                    8.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.onBackground
                        )
                    ),
                    shape = CircleShape
                ),
            strokeWidth = 1.dp,
            color = Color.Transparent
        )
    }
}

@Preview
@Composable
private fun LoadingAnimationPreview() {
    AllDoneTheme {
        Surface {
            LoadingAnimation()
        }
    }
}