package app.consumerclarity.patches.talabat

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod

fun MutableMethod.skipWhen(setting: String, contextRegister: String? = null) {
    val arguments = contextRegister?.let { "{$it}" } ?: "{}"
    val signature = contextRegister?.let { "(Landroid/content/Context;)Z" } ?: "()Z"
    addInstructions(
        0,
        """
            invoke-static $arguments, Lapp/consumerclarity/ConsumerClaritySettings;->$setting$signature
            move-result v0
            if-eqz v0, :cc_return
            return-void
            :cc_return
            nop
        """,
    )
}

fun MutableMethod.returnFalseWhen(setting: String) {
    addInstructions(
        0,
        """
            invoke-static {}, Lapp/consumerclarity/ConsumerClaritySettings;->$setting()Z
            move-result v0
            if-eqz v0, :cc_return
            const/4 v0, 0x0
            return v0
            :cc_return
            nop
        """,
    )
}
