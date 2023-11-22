package com.droidcon.alldone.ui.component


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.alldone.R
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.model.ToDoItem
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.EditMode
import com.popovanton0.blueprint.Blueprint
import com.popovanton0.blueprint.blueprintId
import com.popovanton0.blueprint.dsl.Position.Horizontal.Companion.End
import com.popovanton0.blueprint.dsl.Position.Vertical.Companion.Bottom

@Composable
fun ToDoListItem(
    toDoItem: ToDoItem,
    modifier: Modifier = Modifier,
    updateItem: (ToDoItem, EditMode) -> Unit
) {
    val context = LocalContext.current
    val editDescription = stringResource(id = R.string.cta_edit_item, toDoItem.title)
    val editAction: () -> Unit = {
        updateItem(toDoItem, EditMode.FULL_SCREEN)
    }
    val shareDescription = stringResource(id = R.string.cta_share, toDoItem.title)
    val shareAction: () -> Unit = {
        Toast.makeText(context, shareDescription, Toast.LENGTH_SHORT).show()
    }
    val addToCalendarDescription = stringResource(id = R.string.cta_add_to_calendar, toDoItem.title)
    val addToCalendarAction: () -> Unit = {
        Toast.makeText(context, addToCalendarDescription, Toast.LENGTH_SHORT).show()
    }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.inverseOnSurface),
        modifier = modifier
            .blueprintId("ToDoListItemCard")
            .testTag("OutlinedCard_ToDoListItem")
            .padding(horizontal = 12.dp, vertical = 1.dp)
            .toggleable(
                role = Role.Checkbox,
                value = toDoItem.complete,
                onValueChange = {
                    updateItem(
                        toDoItem.copy(
                            complete = !toDoItem.complete
                        ), EditMode.IN_PLACE
                    )
                }
            )
            .semantics {
                customActions = listOf(
                    CustomAccessibilityAction(editDescription) {
                        editAction()
                        true
                    },
                    CustomAccessibilityAction(shareDescription) {
                        shareAction()
                        true
                    },
                    CustomAccessibilityAction(addToCalendarDescription) {
                        addToCalendarAction()
                        true
                    }
                )
            }

    ) {
        Spacer(modifier = modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = toDoItem.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = toDoItem.description
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Checkbox(
                checked = toDoItem.complete,
                onCheckedChange = null,
                modifier = Modifier
                    .testTag("OutlinedCard_ToDoListItem_Checkbox")
                    .padding(horizontal = 12.dp)
                    .clearAndSetSemantics {}
            )
        }
        Row(modifier = Modifier.clearAndSetSemantics { }) {
            IconButton(
                modifier = Modifier
                    .blueprintId("ShareButton"),
                onClick = shareAction
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = shareDescription
                )
            }
            IconButton(
                modifier = Modifier
                    .blueprintId("AddToCalendarButton"),
                onClick = addToCalendarAction
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = addToCalendarDescription
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                modifier = Modifier
                    .blueprintId("EditButton"),
                onClick = { updateItem(toDoItem, EditMode.FULL_SCREEN) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = editDescription
                )
            }
        }
        Spacer(modifier = modifier.height(6.dp))
    }
}

@Preview
@Composable
private fun ToDoListItemPreview() {
    AllDoneTheme {
        Surface(modifier = Modifier.padding(5.dp)) {
            Blueprint(
                blueprintBuilder = {
                    widths {
                        group {
                            "ShareButton".left lineTo "ShareButton".right
                            "AddToCalendarButton".left lineTo "AddToCalendarButton".right
                            "EditButton".left lineTo "EditButton".right
                        }
                        group(Bottom) {
                            "ToDoListItemCard".left lineTo "ToDoListItemCard".right
                        }
                    }
                    heights {
                        group { "ShareButton".top lineTo "ShareButton".bottom }
                        group(End) { "ToDoListItemCard".top lineTo "ToDoListItemCard".bottom }
                    }
                }
            ) {
                ToDoListItem(
                    ToDoItem(
                        id = 0,
                        title = "Write App",
                        description = "You need to write an app",
                        category = ToDoCategory.PERSONAL,
                    )
                ) { _, _ -> /* Update: No-op */ }
            }
        }
    }
}