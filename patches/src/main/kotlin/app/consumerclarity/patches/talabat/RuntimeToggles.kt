package app.consumerclarity.patches.talabat

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod

fun MutableMethod.skipWhen(setting: String) {
    addInstructions(
        0,
        """
            invoke-static {}, Lapp/consumerclarity/ConsumerClaritySettings;->$setting()Z
            move-result v0
            if-nez v0, :cc_return
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
            if-nez v0, :cc_return
            const/4 v0, 0x0
            return v0
            :cc_return
            nop
        """,
    )
}
