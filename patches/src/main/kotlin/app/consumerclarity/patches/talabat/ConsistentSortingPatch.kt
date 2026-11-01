package app.consumerclarity.patches.talabat

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch

private const val NATIVE_LIBRARY = "lib/arm64-v8a/libconsumerclarity.so"

private object NativeLibrary

private object FlutterLandingScreenConstructor : Fingerprint(
    definingClass = "Lcom/talabat/FlutterLandingScreen;",
    name = "<init>",
    returnType = "V",
    parameters = emptyList(),
)

private val sortingNativeLoaderPatch = bytecodePatch {
    compatibleWith(TALABAT_COMPATIBILITY)
    extendWith("extensions/extension.mpe")

    execute {
        FlutterLandingScreenConstructor.method.addInstructions(
            1,
            """
                const-string v0, "consumerclarity"
                invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V
            """,
        )
    }
}

@Suppress("unused")
val consistentSortingPatch = resourcePatch(
    name = "Consistent restaurant and review sorting",
    description = "Sorts restaurants by organic placement, rating and ID, and reviews by date and ID. " +
        "Requires Talabat 14.4.0 (2368) on ARM64.",
    default = true,
) {
    compatibleWith(TALABAT_COMPATIBILITY)
    dependsOn(sortingNativeLoaderPatch)

    execute {
        decode("AndroidManifest.xml")
        document("AndroidManifest.xml").use { manifest ->
            val application = manifest.getElementsByTagName("application").item(0) as org.w3c.dom.Element
            application.setAttributeNS("http://schemas.android.com/apk/res/android", "android:extractNativeLibs", "true")
            val activity = manifest.createElement("activity").apply {
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:name", "app.consumerclarity.ConsumerClarityActivity")
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:exported", "true")
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:theme", "@android:style/Theme.Material.Light.NoActionBar")
            }
            val filter = manifest.createElement("intent-filter")
            filter.appendChild(manifest.createElement("action").apply {
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:name", "android.intent.action.VIEW")
            })
            filter.appendChild(manifest.createElement("category").apply {
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:name", "android.intent.category.DEFAULT")
            })
            filter.appendChild(manifest.createElement("category").apply {
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:name", "android.intent.category.BROWSABLE")
            })
            filter.appendChild(manifest.createElement("data").apply {
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:scheme", "consumerclarity")
                setAttributeNS("http://schemas.android.com/apk/res/android", "android:host", "settings")
            })
            activity.appendChild(filter)
            application.appendChild(activity)
        }
        NativeLibrary::class.java.getResourceAsStream("/$NATIVE_LIBRARY")!!.use { library ->
            get(NATIVE_LIBRARY, false).writeBytes(library.readBytes())
        }
        listOf("en", "ar", "ckb").forEach { locale ->
            val file = get("assets/flutter_assets/i18n/$locale.json")
            val updated = file.readText()
                .replace(Regex("\"about\\.app\\.facebook\"\\s*:\\s*\"[^\"]*\""), "\"about.app.facebook\": \"Consumer Clarity\"")
                .replace(Regex("\"about\\.app\\.facebook\\.url\"\\s*:\\s*\"[^\"]*\""), "\"about.app.facebook.url\": \"consumerclarity://settings\"")
            file.writeText(updated)
        }
    }
}
