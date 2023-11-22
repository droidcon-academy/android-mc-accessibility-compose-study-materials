package com.droidcon.alldone.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.alldone.ui.theme.AllDoneTheme

@Composable
fun TrailingWarningIcon(isValid: Boolean) {
    if (!isValid) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null)
    }
}

@Preview
@Composable
private fun TrailingWarningIconPreview_Visible() {
    AllDoneTheme {
        Surface {
            TrailingWarningIcon(true)
        }
    }
}