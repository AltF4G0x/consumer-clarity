package app.consumerclarity.patches.talabat

import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val hideBrazePopupsPatch = bytecodePatch(
    name = "Hide Braze in-app popups",
    description = "Prevents Braze promotional in-app messages from being queued or displayed. Functional app " +
        "dialogs and push notifications are unchanged.",
    default = true,
) {
    compatibleWith(TALABAT_COMPATIBILITY)
    extendWith("extensions/extension.mpe")

    execute {
        mutableClassDefBy("Lcom/braze/ui/inappmessage/BrazeInAppMessageManager;").methods.apply {
            single { it.name == "registerInAppMessageManager" }.skipWhen("hideBrazePopups")
            single { it.name == "addInAppMessage" }.skipWhen("hideBrazePopups")
        }
    }
}
