package com.droidcon.alldone.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.alldone.ui.theme.AllDoneTheme

/**
 * [androidx.compose.material3.FilterChip] is still experimental
 */
@Composable
fun ChoiceChip(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 6.dp)
            .defaultMinSize(minHeight = 36.dp, minWidth = 36.dp)
            .toggleable(
                role = Role.Switch,
                value = checked,
                onValueChange = onValueChange
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (checked) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.Transparent
                }
            )
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically),
            imageVector = if (checked) {
                Icons.Filled.CheckCircle
            } else {
                Icons.Outlined.AddCircle
            }, contentDescription = null
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 6.dp, end = 12.dp),
            text = text
        )
    }
}

@Preview
@Composable
private fun ChoiceChipPreview() {
    Surface {
        AllDoneTheme {
            Row {
                ChoiceChip("Checked", true) {}
                Spacer(modifier = Modifier.width(5.dp))
                ChoiceChip("Not Checked", false) {}
            }
        }
    }
}