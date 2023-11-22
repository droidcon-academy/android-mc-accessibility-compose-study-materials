Modifier
    .onFocusEvent(::applyFocusColor)
    .padding(vertical = 6.dp)
    .height(36.dp)
    .toggleable(
        role = Role.Switch,
        value = checked,
        onValueChange = onValueChange
    )
    .border(
        width = 1.dp,
        shape = RoundedCornerShape(20.dp),
        color = borderColor
    )