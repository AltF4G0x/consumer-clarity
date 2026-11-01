package app.consumerclarity.patches.talabat

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.returnEarly

@Suppress("unused")
val disablePlayLicensePatch = bytecodePatch(
    name = "Disable Play license gate",
    description = "Skips the pairip license check and its repeated scheduler so the modified app can start " +
        "without Google Play licensing. Other Google-dependent features are unchanged.",
    default = true,
) {
    compatibleWith(TALABAT_COMPATIBILITY)

    execute {
        mutableClassDefBy("Lcom/pairip/licensecheck/LicenseClient;").methods.apply {
            single { it.name == "checkLicense" }.returnEarly()
            single { it.name == "scheduleRepeatedLicenseCheck" }.returnEarly()
        }
    }
}
