package app.consumerclarity.patches.talabat

import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val disablePlayLicensePatch = bytecodePatch(
    name = "Disable Play license gate",
    description = "Skips the pairip license check and its repeated scheduler so the modified app can start " +
        "without Google Play licensing. Other Google-dependent features are unchanged.",
    default = true,
) {
    compatibleWith(TALABAT_COMPATIBILITY)
    extendWith("extensions/extension.mpe")

    execute {
        mutableClassDefBy("Lcom/pairip/licensecheck/LicenseClient;").methods.apply {
            single { it.name == "checkLicense" }.skipWhen("disableLicenseGate", "p0")
            single { it.name == "scheduleRepeatedLicenseCheck" }.skipWhen("disableLicenseGate")
        }
    }
}
