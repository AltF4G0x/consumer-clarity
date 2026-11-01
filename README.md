<p align="center">
  <a href="CHANGELOG.md"><img alt="version" src="https://img.shields.io/badge/version-1.3.1-6f42c1.svg" /></a>
  <a href="LICENSE"><img alt="license" src="https://img.shields.io/badge/license-GPLv3-blue.svg" /></a>
  <a href="https://www.android.com/"><img alt="platform" src="https://img.shields.io/badge/platform-Android-3ddc84.svg" /></a>
  <a href="https://github.com/MorpheApp/morphe-manager"><img alt="Morphe" src="https://img.shields.io/badge/works%20with-Morphe-00b894.svg" /></a>
</p>

# Consumer Clarity

Consumer Clarity is a set of [Morphe](https://github.com/MorpheApp/morphe-manager) patches for talabat. You bring a compatible talabat APK, Morphe Manager patches it on your phone, and the result has deterministic restaurant and review ordering, less promotional noise and no Google Play license gate.

**[Add Consumer Clarity to Morphe](https://morphe.software/add-source?github=AltF4G0x%2Fconsumer-clarity)** | [Download the latest patch bundle](https://github.com/AltF4G0x/consumer-clarity/releases/latest)

> [!IMPORTANT]
> This repository publishes an `.mpp` patch bundle, not a pre-patched APK. Target `com.talabat` **14.4.0, version code 2368, ARM64 only**. Other versions and architectures are not supported by the sorting patch.

## What it changes

Consumer Clarity now contains **76 independently selectable patches**:

- **Discovery:** Demote or hide sponsored restaurants and sort reviews deterministically without changing review text or star values.
- **Privacy:** Control Firebase, Adjust, Perseus, Braze, advertising identifiers, install attribution, analytics uploads, location, sensors, Bluetooth, NFC, camera, microphone, and legacy storage.
- **Diagnostics:** Independently control Sentry crashes, traces, profiling, replay, screenshots, breadcrumbs, device context, and Shake Bugs reporting, network inspection, identity, and feedback.
- **WebView:** Require media gestures, block third-party cookies, disable geolocation, restrict file/content access, use incognito mode, reduce image traffic, disable background audio, enable safe browsing, request dark content, disable form saving, and block popup windows.
- **Compatibility and navigation:** Disable the Play license gate and open in-app links in the default browser.

The categorized Consumer Clarity page in Talabat's About section only lists patches selected in Morphe. It includes live switches, build-time labels, Calm feed and Maximum privacy presets, sorting controls, reset tools, and developer notes. Native sorting hooks check the expected original instructions before activating and refuse to run on a mismatched binary.

Review text and star values are never modified. Server-provided aggregate ratings cannot be made independently truthful or prevented from changing server-side. Display-ad inventory and sponsored-deals popups outside Braze are not claimed as blocked. Video autoplay is not changed in Flutter because the available play hook cannot distinguish autoplay from manual playback. No separate Talabat developer menu was found; server-controlled experiments are not presented as safe universal switches.

## Patches

<!-- PATCHES_START EXPANDED -->
> **[v1.3.1](https://github.com/AltF4G0x/consumer-clarity/releases/tag/v1.3.1)**&nbsp;&nbsp;•&nbsp;&nbsp;`main`&nbsp;&nbsp;•&nbsp;&nbsp;76 patches total
<details open>
<summary>📦 talabat&nbsp;&nbsp;•&nbsp;&nbsp;76 patches</summary>
<br>

**Package name:** `com.talabat`

**🎯 Supported versions:**

| 14.4.0 |
| :---: |
| ARM64 build analyzed with REA and Jadart |

| 💊&nbsp;Patch | 📜&nbsp;Description | ⚙️&nbsp;Options |
|----------|----------------|-----------|
| [Block WebView content access](#block-webview-content-access) | Prevents in-app WebView pages from opening Android content URLs. |  |
| [Block WebView file access](#block-webview-file-access) | Prevents in-app WebView pages from reading arbitrary file URLs. |  |
| [Block WebView network images](#block-webview-network-images) | Reduces WebView data usage by blocking network-loaded images. |  |
| [Block WebView popup windows](#block-webview-popup-windows) | Prevents WebView JavaScript from opening additional windows automatically. |  |
| [Block third-party WebView cookies](#block-third-party-webview-cookies) | Blocks cross-site cookies inside Talabat's in-app WebView pages. |  |
| [Consistent restaurant and review sorting](#consistent-restaurant-and-review-sorting) | Sorts restaurants by organic placement, rating and ID, and reviews by date and ID. Requires Talabat 14.4.0 (2368) on ARM64. |  |
| [Disable Adjust install referrer](#disable-adjust-install-referrer) | Disables Adjust's install-referrer receiver without changing normal deep links. |  |
| [Disable Analytics upload services](#disable-analytics-upload-services) | Disables Google Analytics measurement upload services and its receiver. |  |
| [Disable Bluetooth access](#disable-bluetooth-access) | Removes Bluetooth scan, connect, advertise, and legacy Bluetooth permissions. |  |
| [Disable Braze promotional push](#disable-braze-promotional-push) | Disables Braze's push receiver. Talabat's separate Firebase order-messaging service is unchanged. |  |
| [Disable Firebase session reporting](#disable-firebase-session-reporting) | Disables Firebase's session lifecycle reporting service. Crash and analytics controls remain separate. |  |
| [Disable Google Tag Manager](#disable-google-tag-manager) | Disables the Tag Manager background service used for marketing measurement. |  |
| [Disable Google telemetry transport](#disable-google-telemetry-transport) | Disables Google's background telemetry upload scheduler and alarm receivers. |  |
| [Disable Huawei ads identifier](#disable-huawei-ads-identifier) | Removes Huawei's advertising-identifier service declaration while leaving Huawei login and maps intact. |  |
| [Disable NFC access](#disable-nfc-access) | Removes NFC access from Talabat. |  |
| [Disable Play license gate](#disable-play-license-gate) | Skips the pairip license check and its repeated scheduler so the modified app can start without Google Play licensing. Other Google-dependent features are unchanged. |  |
| [Disable Sentry ANR profiling](#disable-sentry-anr-profiling) | Sets Sentry's ANR profiling sample rate to zero. |  |
| [Disable Sentry ANR reports](#disable-sentry-anr-reports) | Disables Sentry application-not-responding reports. |  |
| [Disable Sentry app-start tracing](#disable-sentry-app-start-tracing) | Disables automatic startup and activity lifecycle performance traces. |  |
| [Disable Sentry breadcrumbs](#disable-sentry-breadcrumbs) | Prevents application-supplied breadcrumbs from being recorded by Sentry. |  |
| [Disable Sentry crash reports](#disable-sentry-crash-reports) | Suppresses Sentry events, exceptions, messages, and user-feedback reports while returning an empty event ID. |  |
| [Disable Sentry crash screenshots](#disable-sentry-crash-screenshots) | Prevents Sentry from attaching screenshots to crash events. |  |
| [Disable Sentry device context](#disable-sentry-device-context) | Prevents Sentry from collecting additional device context. |  |
| [Disable Sentry error replay](#disable-sentry-error-replay) | Disables Sentry replay capture associated with errors. |  |
| [Disable Sentry frame tracking](#disable-sentry-frame-tracking) | Disables Sentry frame-rate and rendering performance collection. |  |
| [Disable Sentry interaction breadcrumbs](#disable-sentry-interaction-breadcrumbs) | Prevents taps and other user interactions from becoming Sentry breadcrumbs. |  |
| [Disable Sentry interaction tracing](#disable-sentry-interaction-tracing) | Disables Sentry performance traces built from user interactions. |  |
| [Disable Sentry lifecycle breadcrumbs](#disable-sentry-lifecycle-breadcrumbs) | Disables activity, application, and component lifecycle breadcrumbs. |  |
| [Disable Sentry logcat collection](#disable-sentry-logcat-collection) | Prevents Sentry from turning logcat messages into breadcrumbs or Sentry logs. Logcat output itself is unchanged. |  |
| [Disable Sentry native crash preload](#disable-sentry-native-crash-preload) | Disables Sentry's native crash-handler preload provider. |  |
| [Disable Sentry native reporting](#disable-sentry-native-reporting) | Disables Sentry's native NDK integration. |  |
| [Disable Sentry network breadcrumbs](#disable-sentry-network-breadcrumbs) | Prevents network changes from becoming Sentry breadcrumbs. |  |
| [Disable Sentry performance V2](#disable-sentry-performance-v2) | Disables Sentry's second-generation automatic performance collection. |  |
| [Disable Sentry performance traces](#disable-sentry-performance-traces) | Sets Sentry's performance trace sample rate to zero. |  |
| [Disable Sentry profiling](#disable-sentry-profiling) | Sets Sentry's profiling sample rate to zero. |  |
| [Disable Sentry root reporting](#disable-sentry-root-reporting) | Disables Sentry's root-status checks and reporting. |  |
| [Disable Sentry session replay](#disable-sentry-session-replay) | Displays no Sentry session-replay capture. This is separate from crash screenshots. |  |
| [Disable Sentry startup performance](#disable-sentry-startup-performance) | Disables Sentry's automatic startup performance provider. |  |
| [Disable Sentry storage context](#disable-sentry-storage-context) | Prevents Sentry from collecting external-storage context. |  |
| [Disable Sentry system breadcrumbs](#disable-sentry-system-breadcrumbs) | Disables system-event breadcrumbs and their extra device details. |  |
| [Disable Sentry tombstone reports](#disable-sentry-tombstone-reports) | Disables Sentry tombstone collection and historical tombstone reporting. |  |
| [Disable Sentry view hierarchy capture](#disable-sentry-view-hierarchy-capture) | Prevents Sentry from attaching the app's view hierarchy to events. |  |
| [Disable Shake Bugs activity history](#disable-shake-bugs-activity-history) | Prevents Shake Bugs from recording lifecycle and touch activity. |  |
| [Disable Shake Bugs console logs](#disable-shake-bugs-console-logs) | Prevents application log messages from being added to Shake Bugs reports. |  |
| [Disable Shake Bugs crash reports](#disable-shake-bugs-crash-reports) | Suppresses Shake Bugs error handling, silent reports, and crash-report enablement. |  |
| [Disable Shake Bugs identity tracking](#disable-shake-bugs-identity-tracking) | Prevents Shake Bugs from receiving user IDs, metadata, and push tokens. |  |
| [Disable Shake Bugs initialization](#disable-shake-bugs-initialization) | Prevents Shake Bugs from starting. Other Shake Bugs switches are unnecessary when this is selected. |  |
| [Disable Shake Bugs network inspection](#disable-shake-bugs-network-inspection) | Prevents Shake Bugs from recording network requests and responses. |  |
| [Disable Shake Bugs notification history](#disable-shake-bugs-notification-history) | Prevents Shake Bugs from recording notification events. |  |
| [Disable Shake Bugs screen recording](#disable-shake-bugs-screen-recording) | Disables Shake Bugs' foreground screen-recording service. |  |
| [Disable Shake Bugs user feedback](#disable-shake-bugs-user-feedback) | Disables Shake Bugs' user-feedback collection. |  |
| [Disable WebView background audio](#disable-webview-background-audio) | Prevents in-app WebView pages from continuing audio in the background. |  |
| [Disable WebView form saving](#disable-webview-form-saving) | Prevents in-app WebView pages from saving form data. |  |
| [Disable WebView geolocation](#disable-webview-geolocation) | Prevents in-app WebView pages from requesting device location through the browser API. |  |
| [Disable background location](#disable-background-location) | Removes background-location access. Foreground delivery addresses and order tracking can still work. |  |
| [Disable camera access](#disable-camera-access) | Removes camera permission. QR scanning, document capture, and profile-photo capture will be unavailable. |  |
| [Disable country geofence notifications](#disable-country-geofence-notifications) | Disables the receiver that reacts to country-boundary location changes. |  |
| [Disable high-rate sensors](#disable-high-rate-sensors) | Removes access to high-sampling-rate motion sensors used by diagnostics and gesture detection. |  |
| [Disable legacy external storage](#disable-legacy-external-storage) | Removes legacy broad external-storage read and write permissions. App-owned media sharing remains separate. |  |
| [Disable microphone access](#disable-microphone-access) | Removes microphone permission and prevents in-app audio recording. |  |
| [Disable screenshot detection](#disable-screenshot-detection) | Removes Android's screen-capture detection permission. This does not prevent Android screenshots. |  |
| [Disable system overlays](#disable-system-overlays) | Removes Talabat's ability to draw over other apps. |  |
| [Enable WebView safe browsing](#enable-webview-safe-browsing) | Requests Android Safe Browsing checks for in-app WebView pages. |  |
| [Force dark WebView content](#force-dark-webview-content) | Requests dark rendering for supported in-app WebView pages. Flutter screens are unchanged. |  |
| [Hide Braze content cards](#hide-braze-content-cards) | Disables Braze's promotional content-card activity. |  |
| [Hide Braze in-app popups](#hide-braze-in-app-popups) | Prevents Braze promotional in-app messages from being queued or displayed. Functional app dialogs and push notifications are unchanged. |  |
| [Hide Shake Bugs chat notifications](#hide-shake-bugs-chat-notifications) | Prevents Shake Bugs from displaying support-chat notifications. |  |
| [Hide Shake Bugs introduction](#hide-shake-bugs-introduction) | Suppresses Shake Bugs' introductory message. |  |
| [Hide Shake Bugs report UI](#hide-shake-bugs-report-ui) | Prevents Shake Bugs from opening its feedback and bug-report interface. |  |
| [Open links in default browser](#open-links-in-default-browser) | Routes links that would open in Talabat's in-app WebView to the default browser. Can be toggled in Consumer Clarity settings. |  |
| [Reduce analytics tracking](#reduce-analytics-tracking) | Disables confirmed Firebase, Adjust, Perseus and Braze event logging paths without blocking restaurant, checkout or order-tracking network requests. |  |
| [Remove ad attribution access](#remove-ad-attribution-access) | Removes access to Android Privacy Sandbox attribution reporting. |  |
| [Remove ad services identifier access](#remove-ad-services-identifier-access) | Removes access to Android Privacy Sandbox advertising identifiers. |  |
| [Remove advertising ID permission](#remove-advertising-id-permission) | Removes Android's advertising-identifier permission. Talabat can no longer request the Google advertising ID. |  |
| [Require WebView media gesture](#require-webview-media-gesture) | Prevents in-app WebView media from starting without a user gesture. Manual playback remains available. |  |
| [Use incognito WebView pages](#use-incognito-webview-pages) | Requests private browsing behavior for in-app WebView pages. Signed-in pages may stop working. |  |

</details>

<!-- PATCHES_END -->

## Install

1. Obtain a compatible talabat 14.4.0 ARM64 APK or XAPK with its required splits.
2. [Add Consumer Clarity to Morphe](https://morphe.software/add-source?github=AltF4G0x%2Fconsumer-clarity), or add `https://github.com/AltF4G0x/consumer-clarity` manually as a patch source in Morphe Manager.
3. Select the compatible talabat package and the patches you want.
4. Patch with Morphe Manager.
5. Uninstall the original app if Android reports a signature conflict, then install the patched result. Uninstalling can remove app data and sign you out.

## Build from source

Requirements: JDK 17, the Android SDK/NDK, and the official Morphe patcher/plugin artifacts in Maven Local. This project uses plugin 2.0.0 and patcher 2.1.0.

```bash
export JAVA_HOME=/path/to/jdk17
export ANDROID_HOME=/path/to/android-sdk
export ANDROID_NDK_HOME=/path/to/android-ndk
./gradlew buildAndroid
```

The generated bundle is `patches/build/libs/patches-1.3.1.mpp`.

Morphe Desktop requires Java 21:

```bash
java -jar morphe-desktop-*-all.jar patch \
  --validate \
  -p patches/build/libs/patches-1.3.1.mpp \
  com.talabat.xapk
```

## Verification and limitations

All 76 patches were built and applied together with Morphe validation and no failed patches. The fully enabled APK opened Talabat and the categorized settings page, displayed all 76 catalog entries, exercised live switches and presets, removed the selected permissions, and activated both native sorting hooks without a new crash. Complete restaurant/review sorting behavior, every screen, and every patch's server-visible effect have not been exhaustively verified.

Bugs and concrete feature requests belong in the [issue tracker](https://github.com/AltF4G0x/consumer-clarity/issues), with the talabat version and patch involved.

## License

GPLv3. See [LICENSE](LICENSE) and [NOTICE](NOTICE). Consumer Clarity is an independent patch collection and is not authored or endorsed by talabat or the Morphe project.
