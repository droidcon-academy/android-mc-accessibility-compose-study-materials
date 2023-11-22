package com.droidcon.alldone

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.isHeading

// is an accessibility heading
fun SemanticsNodeInteraction.assertIsHeading(): SemanticsNodeInteraction = assert(isHeading())

// has an accessibility action
fun SemanticsNodeInteraction.assertHasAccessibilityAction(label: String): SemanticsNodeInteraction {
    return assert(
        SemanticsMatcher.actionIsDefined(label)
    )
}

fun SemanticsMatcher.Companion.actionIsDefined(label: String): SemanticsMatcher {
    return SemanticsMatcher("$label is defined in CustomActions") {
        label in it
            .config[SemanticsActions.CustomActions]
            .map { action -> action.label }
    }
}

// has an specific UI role
fun SemanticsNodeInteraction.assertHasRole(role: Role): SemanticsNodeInteraction =
    assert(hasRole(role))

fun hasRole(role: Role): SemanticsMatcher = SemanticsMatcher.expectValue(
    SemanticsProperties.Role, role
)