<p align="center">
  <a href="CHANGELOG.md"><img alt="version" src="https://img.shields.io/badge/version-1.0.0-6f42c1.svg" /></a>
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

- **Consistent restaurant and review sorting:** Places entries with a positive `sponsored_rank` after organic entries, then orders restaurants by descending rating and ascending ID. Reviews are ordered newest-first using their ISO date, with ascending ID as a stable tie-breaker. Later pages are processed as they load. Settings also supports hiding sponsored entries, original order, oldest-first reviews, and highest-rated reviews.
- **Reduce analytics tracking:** Disables confirmed Firebase, Adjust, Perseus and Braze event-logging paths without blocking restaurant, checkout, login or order-tracking requests.
- **Hide Braze in-app popups:** Prevents Braze promotional in-app messages from being queued or displayed. Functional dialogs and push notifications are unchanged.
- **Disable Play license gate:** Skips the pairip license check and its repeated scheduler so a modified build can start without Google Play licensing. Other Google-dependent features are unchanged.
- **Open links in default browser:** Routes links that would open in Talabat's in-app WebView to the default browser when enabled.
- **Consumer Clarity settings:** Adds a native settings activity through Talabat's About page, with working sponsored/review sorting, analytics, popup, license-gate and external-link toggles, plus developer information and links to the original Facebook and FAQ pages.

Review text and star values are never modified. Server-provided aggregate ratings cannot be made independently truthful or prevented from changing server-side. Display-ad inventory and sponsored-deals popups outside Braze are not claimed as blocked. Video autoplay is not changed because the available play hook cannot distinguish autoplay from manual playback. No separate Talabat developer menu was found; server-controlled experiments are not presented as safe universal switches.

Native sorting hooks check the expected original instructions before activating and refuse to run on a mismatched binary.

## Patches

<!-- PATCHES_START EXPANDED -->
> **[v1.1.1](https://github.com/AltF4G0x/consumer-clarity/releases/tag/v1.1.1)**&nbsp;&nbsp;•&nbsp;&nbsp;`main`&nbsp;&nbsp;•&nbsp;&nbsp;5 patches total
<details open>
<summary>📦 talabat&nbsp;&nbsp;•&nbsp;&nbsp;5 patches</summary>
<br>

**Package name:** `com.talabat`

**🎯 Supported versions:**

| 14.4.0 |
| :---: |
| ARM64 build analyzed with REA and Jadart |

| 💊&nbsp;Patch | 📜&nbsp;Description | ⚙️&nbsp;Options |
|----------|----------------|-----------|
| [Consistent restaurant and review sorting](#consistent-restaurant-and-review-sorting) | Sorts restaurants by organic placement, rating and ID, and reviews by date and ID. Requires Talabat 14.4.0 (2368) on ARM64. |  |
| [Disable Play license gate](#disable-play-license-gate) | Skips the pairip license check and its repeated scheduler so the modified app can start without Google Play licensing. Other Google-dependent features are unchanged. |  |
| [Hide Braze in-app popups](#hide-braze-in-app-popups) | Prevents Braze promotional in-app messages from being queued or displayed. Functional app dialogs and push notifications are unchanged. |  |
| [Open links in default browser](#open-links-in-default-browser) | Routes links that would open in Talabat's in-app WebView to the default browser. Can be toggled in Consumer Clarity settings. |  |
| [Reduce analytics tracking](#reduce-analytics-tracking) | Disables confirmed Firebase, Adjust, Perseus and Braze event logging paths without blocking restaurant, checkout or order-tracking network requests. |  |

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

The generated bundle is `patches/build/libs/patches-1.0.0.mpp`.

Morphe Desktop requires Java 21:

```bash
java -jar morphe-desktop-*-all.jar patch \
  --validate \
  -p patches/build/libs/patches-1.0.0.mpp \
  com.talabat.xapk
```

## Verification and limitations

The patches were built and applied with Morphe, and both native sorting hooks activated successfully in an ARM64 emulator session. A later emulator outage prevented complete restaurant/review UI verification. No claim is made that every screen or Google-independent feature has been exercised.

Bugs and concrete feature requests belong in the [issue tracker](https://github.com/AltF4G0x/consumer-clarity/issues), with the talabat version and patch involved.

## License

GPLv3. See [LICENSE](LICENSE) and [NOTICE](NOTICE). Consumer Clarity is an independent patch collection and is not authored or endorsed by talabat or the Morphe project.
