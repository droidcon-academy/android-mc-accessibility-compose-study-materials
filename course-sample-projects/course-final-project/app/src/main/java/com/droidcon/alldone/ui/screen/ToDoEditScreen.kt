package com.droidcon.alldone.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.alldone.R
import com.droidcon.alldone.model.ToDoCategory
import com.droidcon.alldone.ui.component.RadioButtonGroup
import com.droidcon.alldone.ui.component.TrailingWarningIcon
import com.droidcon.alldone.ui.theme.AllDoneTheme
import com.droidcon.alldone.utils.validation.ValidateDescription
import com.droidcon.alldone.utils.validation.ValidateTitle

@Composable
fun ToDoEditScreen(
    isEditing: Boolean,
    title: String,
    description: String,
    category: ToDoCategory,
    modifier: Modifier = Modifier,
    onSave: (String, String, ToDoCategory) -> Unit,
    onDelete: () -> Unit
) {
    var itemTitle by rememberSaveable { mutableStateOf(title) }
    var itemDescription by rememberSaveable { mutableStateOf(description) }
    var itemCategory by rememberSaveable { mutableStateOf(category) }

    var isTitleValid by rememberSaveable { mutableStateOf(true) }
    var isDescriptionValid by rememberSaveable { mutableStateOf(true) }

    val titleError = stringResource(id = R.string.invalid_title, ValidateTitle.minimumLength)
    val descriptionError = stringResource(id = R.string.invalid_description, ValidateDescription.minimumLength)
    val saveReadyDescription = stringResource(R.string.ready_to_save)
    val localView = LocalView.current

    fun validateInputs() {
        ValidateTitle(itemTitle).fold({
            isTitleValid = false
        }, {
            isTitleValid = true
        })
        ValidateDescription(itemDescription).fold({
            isDescriptionValid = false
        }, {
            isDescriptionValid = true
        })

        if (isTitleValid && isDescriptionValid) {
            localView.announceForAccessibility(saveReadyDescription)
        }
    }

    Column(
        modifier = modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp)
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(
                id = if (isEditing) {
                    R.string.title_edit_screen
                } else {
                    R.string.title_create_screen
                }
            )
        )
        OutlinedTextField(
            value = itemTitle,
            label = { Text(text = stringResource(id = R.string.edit_label_title)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = stringResource(id = R.string.edit_placeholder_title)) },
            isError = !isTitleValid,
            trailingIcon = { TrailingWarningIcon(isTitleValid) },
            supportingText = {
                if (!isTitleValid) {
                    Text(
                        text = titleError,
                        color = MaterialTheme.colorScheme.error
                    )

                }
            },
            onValueChange = {
                itemTitle = it
                if (!isTitleValid) {
                    validateInputs()
                }
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = itemDescription,
            label = { Text(text = stringResource(id = R.string.edit_label_description)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = stringResource(id = R.string.edit_placeholder_description)) },
            isError = !isDescriptionValid,
            trailingIcon = { TrailingWarningIcon(isDescriptionValid) },
            supportingText = {
                if (!isDescriptionValid) {
                    Text(
                        text = descriptionError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            onValueChange = {
                itemDescription = it
                if (!isDescriptionValid) {
                    validateInputs()
                }
            }
        )

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.edit_label_category),
            modifier = Modifier.semantics { heading() }
        )
        RadioButtonGroup(
            selectedOptionIndex = ToDoCategory.values().indexOf(itemCategory),
            radioOptions = ToDoCategory.values().map { categoryElement ->
                object : RadioButtonGroup.Describable<ToDoCategory> {
                    override val value = categoryElement
                    override val description: String =
                        stringResource(
                            id = when (categoryElement) {
                                ToDoCategory.PERSONAL -> R.string.category_personal
                                ToDoCategory.PROFESSIONAL -> R.string.category_professional
                                ToDoCategory.SOCIAL -> R.string.category_social
                                ToDoCategory.TRAVEL -> R.string.category_travel
                            }
                        )
                }
            },
        ) { itemCategory = it }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = isTitleValid && isDescriptionValid,
            onClick = {
                validateInputs()
                if (isTitleValid && isDescriptionValid) {
                    onSave(itemTitle, itemDescription, itemCategory)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.cta_save),
                fontSize = 20.sp,
            )
        }
        if (isEditing) {
            Spacer(modifier = Modifier.weight(1.0f))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.cta_delete),
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
private fun ToDoEditScreenPreview() {
    AllDoneTheme {
        Surface {
            ToDoEditScreen(
                isEditing = false,
                title = "Title",
                description = "Description",
                category = ToDoCategory.SOCIAL,
                onSave = { _, _, _ -> },
                onDelete = {}
            )
        }
    }
}