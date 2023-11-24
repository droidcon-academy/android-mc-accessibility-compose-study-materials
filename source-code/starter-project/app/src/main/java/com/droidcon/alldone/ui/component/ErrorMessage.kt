package com.droidcon.alldone.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.ui.theme.AllDoneTheme

@Composable
fun ErrorMessage(errorMessage: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(56.dp),
            imageVector = Icons.Outlined.Warning,
            contentDescription = null)
        Text(text = errorMessage)
    }
}

@Preview
@Composable
private fun ErrorMessagePreview() {
    AllDoneTheme {
        Surface {
            ErrorMessage(errorMessage = "Error!")
        }
    }
}