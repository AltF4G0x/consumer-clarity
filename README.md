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

- **Consistent restaurant and review sorting:** Places entries with a positive `sponsored_rank` after organic entries, then orders restaurants by descending rating and ascending ID. Reviews are ordered newest-first using their ISO date, with ascending ID as a stable tie-breaker. Later pages are processed as they load.
- **Reduce analytics tracking:** Disables confirmed Firebase, Adjust, Perseus and Braze event-logging paths without blocking restaurant, checkout, login or order-tracking requests.
- **Hide Braze in-app popups:** Prevents Braze promotional in-app messages from being queued or displayed. Functional dialogs and push notifications are unchanged.
- **Disable Play license gate:** Skips the pairip license check and its repeated scheduler so a modified build can start without Google Play licensing. Other Google-dependent features are unchanged.

Review text and star values are never modified. Server-provided aggregate ratings cannot be made independently truthful or prevented from changing server-side. Display-ad inventory and sponsored-deals popups outside Braze are not claimed as blocked.

Native sorting hooks check the expected original instructions before activating and refuse to run on a mismatched binary.

## Patches

<!-- PATCHES_START EXPANDED -->
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
