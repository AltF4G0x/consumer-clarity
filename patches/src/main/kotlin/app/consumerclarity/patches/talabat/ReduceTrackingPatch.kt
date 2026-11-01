package app.consumerclarity.patches.talabat

import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val reduceTrackingPatch = bytecodePatch(
    name = "Reduce analytics tracking",
    description = "Disables confirmed Firebase, Adjust, Perseus and Braze event logging paths without blocking " +
        "restaurant, checkout or order-tracking network requests.",
    default = true,
) {
    compatibleWith(TALABAT_COMPATIBILITY)
    extendWith("extensions/extension.mpe")

    execute {
        mapOf(
            "Lcom/google/firebase/analytics/connector/AnalyticsConnectorImpl;" to listOf("logEvent"),
            "Lcom/adjust/sdk/Adjust;" to listOf(
                "trackEvent",
                "trackAdRevenue",
                "trackPlayStoreSubscription",
            ),
            "Lcom/deliveryhero/perseus/repository/PerseusHitsRepositoryImpl;" to listOf("insertEvent"),
            "Lcom/braze/Braze;" to listOf(
                "logCustomEvent",
                "logPurchase",
                "logBannerClick",
                "logFeatureFlagImpression",
                "setGoogleAdvertisingId",
            ),
        ).forEach { (classType, methodNames) ->
            val methods = mutableClassDefBy(classType).methods
            methodNames.forEach { name ->
                methods.single { it.name == name }.skipWhen("reduceTracking")
            }
        }
        mutableClassDefBy("Lcom/braze/Braze;").methods
            .single { it.name == "logBannerImpression" }
            .returnFalseWhen("reduceTracking")
    }
}
