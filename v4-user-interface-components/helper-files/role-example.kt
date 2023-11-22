Column {
    val context = LocalContext.current
    val showToast = { message: String ->
        Toast.makeText(context, "Action: $message", Toast.LENGTH_SHORT).show()
    }
    Button(onClick = { showToast("Element 1") }) {
        Text(text = "Show 1")
    }
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        modifier = Modifier.clickable { showToast("Element 2") },
        text = "Show 2"
    )
    Spacer(modifier = Modifier.height(15.dp))
    Button(onClick = { showToast("Element 3") }) {
        Text(text = "Show 3")
    }
}