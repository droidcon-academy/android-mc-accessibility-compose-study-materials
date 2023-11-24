
    item {
        Row {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .height(22.dp)
                    .width(22.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(
                    id = when (toDoCategory) {
                        ToDoCategory.PERSONAL -> R.drawable.ic_category_personal
                        ToDoCategory.PROFESSIONAL -> R.drawable.ic_category_professional
                        ToDoCategory.SOCIAL -> R.drawable.ic_category_social
                        ToDoCategory.TRAVEL -> R.drawable.ic_category_travel
                    }
                ), contentDescription = null
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                modifier = Modifier
                    .testTag("Heading")
                    .semantics { heading() },
                text = stringResource(
                    when (toDoCategory) {
                        ToDoCategory.PERSONAL -> R.string.category_personal
                        ToDoCategory.PROFESSIONAL -> R.string.category_professional
                        ToDoCategory.SOCIAL -> R.string.category_social
                        ToDoCategory.TRAVEL -> R.string.category_travel
                    }
                ),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
