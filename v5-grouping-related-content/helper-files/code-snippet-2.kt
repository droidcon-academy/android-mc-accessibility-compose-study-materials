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