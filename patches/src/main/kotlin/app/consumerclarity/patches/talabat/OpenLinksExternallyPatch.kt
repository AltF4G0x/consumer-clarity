package app.consumerclarity.patches.talabat

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions

@Suppress("unused")
val openLinksExternallyPatch = consumerBytecodePatch(
    name = "Open links in default browser",
    description = "Routes links that would open in Talabat's in-app WebView to the default browser. " +
        "Can be toggled in Consumer Clarity settings.",
    category = "Navigation",
    key = "open_links_externally",
    default = true,
    defaultSetting = false,
) {

    execute {
        mutableClassDefBy("LAc/w;").methods.single { it.name == "F" }.addInstructions(
            0,
            """
                invoke-static {}, Lapp/consumerclarity/ConsumerClaritySettings;->openLinksExternally()Z
                move-result v0
                if-eqz v0, :cc_original
                new-instance v0, Landroid/content/Intent;
                const-string v1, "android.intent.action.VIEW"
                invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V
                invoke-static {p1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;
                move-result-object v1
                invoke-virtual {v0, v1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;
                iget-object v1, p0, LAc/w;->c:Ljava/lang/Object;
                check-cast v1, Landroid/app/Activity;
                invoke-virtual {v1, v0}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;)V
                const/4 v0, 0x1
                return v0
                :cc_original
                nop
            """,
        )
    }
}
