package com.droidcon.alldone.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.R
import com.droidcon.alldone.ui.theme.AllDoneTheme

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(56.dp),
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null)
        Text(text = stringResource(id = R.string.empty_list))
    }
}

@Preview
@Composable
private fun EmptyListPreview() {
    AllDoneTheme {
        Surface {
            EmptyList()
        }
    }
}