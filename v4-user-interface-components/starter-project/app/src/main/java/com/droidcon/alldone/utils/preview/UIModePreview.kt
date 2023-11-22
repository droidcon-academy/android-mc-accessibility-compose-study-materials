package com.droidcon.alldone.utils.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode",
    group = "color mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    group = "color mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
annotation class UIModePreview