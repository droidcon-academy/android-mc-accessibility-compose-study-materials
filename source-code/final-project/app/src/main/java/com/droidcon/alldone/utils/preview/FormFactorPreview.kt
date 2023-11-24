package com.droidcon.alldone.utils.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone",
    group = "form factor",
    device = Devices.PHONE,
    showSystemUi = true
)
@Preview(
    name = "Phone (Landscape)",
    group = "form factor",
    device = "spec:id=reference_phone,shape=Normal,width=891,height=411,unit=dp,dpi=420",
    showSystemUi = true
)
@Preview(
    name = "Foldable",
    group = "form factor",
    device = Devices.FOLDABLE,
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    group = "form factor",
    device = Devices.TABLET,
    showSystemUi = true
)
annotation class FormFactorPreview