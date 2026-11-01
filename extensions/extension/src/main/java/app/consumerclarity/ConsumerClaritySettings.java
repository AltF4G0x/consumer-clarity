package app.consumerclarity;

import android.content.Context;
import android.content.SharedPreferences;

public final class ConsumerClaritySettings {
    private static final String PREFS = "consumerclarity";

    private ConsumerClaritySettings() {}

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private static Context appContext() {
        try {
            return (Context) Class.forName("android.app.ActivityThread")
                .getMethod("currentApplication")
                .invoke(null);
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException(error);
        }
    }

    public static boolean enabled(String key) {
        return get(appContext()).getBoolean(key, true);
    }

    public static boolean reduceTracking() {
        return reduceTracking(appContext());
    }

    public static boolean hideBrazePopups() {
        return hideBrazePopups(appContext());
    }

    public static boolean disableLicenseGate() {
        return disableLicenseGate(appContext());
    }

    public static boolean openLinksExternally() {
        return openLinksExternally(appContext());
    }

    public static boolean reduceTracking(Context context) {
        return get(context).getBoolean("reduce_tracking", true);
    }

    public static boolean hideBrazePopups(Context context) {
        return get(context).getBoolean("hide_braze_popups", true);
    }

    public static boolean disableLicenseGate(Context context) {
        return get(context).getBoolean("disable_license_gate", true);
    }

    public static boolean openLinksExternally(Context context) {
        return get(context).getBoolean("open_links_externally", false);
    }
}
