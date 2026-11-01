package app.consumerclarity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ConsumerClarityActivity extends Activity {
    private static final int BACKGROUND = Color.rgb(18, 19, 23);
    private static final int CARD = Color.rgb(30, 32, 38);
    private static final int TEXT = Color.rgb(244, 244, 246);
    private static final int MUTED = Color.rgb(174, 177, 186);
    private static final int ACCENT = Color.rgb(255, 90, 0);

    private SharedPreferences preferences;
    private LinearLayout content;

    private static final class Entry {
        String key;
        String title;
        String description;
        String category;
        String kind;
        boolean initial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = ConsumerClaritySettings.get(this);
        getWindow().setStatusBarColor(BACKGROUND);
        getWindow().setNavigationBarColor(BACKGROUND);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BACKGROUND);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(36, 40, 36, 56);
        content.setBackgroundColor(BACKGROUND);
        scroll.addView(content, new ScrollView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView eyebrow = text("CONSUMER CLARITY", 13, ACCENT);
        content.addView(eyebrow, margins(0, 0, 0, 8));
        content.addView(text("Choose what reaches you.", 28, TEXT), margins(0, 0, 0, 8));
        content.addView(text("Patch controls, privacy, discovery and diagnostics. Only patches selected in Morphe appear here.", 16, MUTED), margins(0, 0, 0, 28));

        List<Entry> entries = loadEntries();
        if (hasRuntime(entries, "Promotions") || hasKind(entries, "sorting")) {
            section("Quick setup");
            cardButton("Use Calm feed", "Hide sponsored entries and enable selected promotion controls.", view -> {
                preferences.edit().putInt("sponsored_mode", 1).apply();
                enableCategory(entries, "Promotions");
                recreate();
            });
        }
        if (hasRuntime(entries, "Privacy") || hasRuntime(entries, "Diagnostics")) {
            cardButton("Use Maximum privacy", "Enable selected analytics and diagnostic privacy switches.", view -> {
                enableCategory(entries, "Privacy");
                enableCategory(entries, "Diagnostics");
                recreate();
            });
        }

        Map<String, List<Entry>> categories = new LinkedHashMap<>();
        for (Entry entry : entries) {
            if (!categories.containsKey(entry.category)) categories.put(entry.category, new ArrayList<>());
            categories.get(entry.category).add(entry);
        }
        for (Map.Entry<String, List<Entry>> category : categories.entrySet()) {
            section(category.getKey());
            for (Entry entry : category.getValue()) {
                if (entry.kind.equals("sorting")) addSortingControls();
                else if (entry.kind.equals("build")) addBuildRow(entry);
                else addRuntimeRow(entry);
            }
        }

        section("Settings tools");
        cardButton("Reset live switches", "Restore original app behavior for live hooks. Build-time changes require repatching without those patches.", view -> {
            for (Entry entry : entries) {
                if (entry.kind.equals("runtime")) preferences.edit().putBoolean(entry.key, false).apply();
            }
            preferences.edit().putInt("sponsored_mode", 2).putInt("review_sort_mode", 3).apply();
            recreate();
        });

        section("Developer information");
        TextView developer = text("Native hooks check app version and instruction bytes before activating. Server experiments are not safe universal switches. Build-time permission and component controls are fixed in the patched APK; live switches can be changed here.", 16, MUTED);
        developer.setPadding(24, 24, 24, 24);
        developer.setBackgroundColor(CARD);
        content.addView(developer, margins(0, 0, 0, 16));
        addLink("Talabat FAQ", "https://www.talabat.com/en/uae/faq?src=app&v=2");
        addLink("Talabat on Facebook", "https://www.facebook.com/talabat");
        addLink("Research and limitations", "https://github.com/AltF4G0x/consumer-clarity-research");
        addLink("Patch source and releases", "https://github.com/AltF4G0x/consumer-clarity/releases/latest");
        setContentView(scroll);
    }

    private List<Entry> loadEntries() {
        List<Entry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            getAssets().open("consumerclarity/settings.tsv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\t", -1);
                if (fields.length != 6) continue;
                Entry entry = new Entry();
                entry.key = fields[0];
                entry.title = fields[1];
                entry.description = fields[2];
                entry.category = fields[3];
                entry.kind = fields[4];
                entry.initial = Boolean.parseBoolean(fields[5]);
                entries.add(entry);
            }
        } catch (Exception error) {
            section("Settings error");
            content.addView(text("The selected-patch catalog could not be read: " + error, 16, TEXT));
        }
        return entries;
    }

    private boolean hasRuntime(List<Entry> entries, String category) {
        for (Entry entry : entries) if (entry.kind.equals("runtime") && entry.category.equals(category)) return true;
        return false;
    }

    private boolean hasKind(List<Entry> entries, String kind) {
        for (Entry entry : entries) if (entry.kind.equals(kind)) return true;
        return false;
    }

    private void enableCategory(List<Entry> entries, String category) {
        for (Entry entry : entries) {
            if (entry.kind.equals("runtime") && entry.category.equals(category)) {
                preferences.edit().putBoolean(entry.key, true).apply();
            }
        }
    }

    private void addSortingControls() {
        LinearLayout card = card();
        card.addView(text("Sponsored restaurants", 19, TEXT));
        Spinner sponsored = new Spinner(this);
        String[] sponsoredValues = {"Demote sponsored", "Hide sponsored", "Original order"};
        sponsored.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sponsoredValues));
        sponsored.setSelection(preferences.getInt("sponsored_mode", 0));
        sponsored.setOnItemSelectedListener(new SimpleSelectionListener() {
            @Override public void selected(int position) {
                preferences.edit().putInt("sponsored_mode", position).apply();
            }
        });
        card.addView(sponsored, margins(0, 8, 0, 24));
        card.addView(text("Review order", 19, TEXT));
        Spinner reviews = new Spinner(this);
        String[] reviewValues = {"Newest first", "Oldest first", "Highest rated", "Original order"};
        reviews.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reviewValues));
        reviews.setSelection(preferences.getInt("review_sort_mode", 0));
        reviews.setOnItemSelectedListener(new SimpleSelectionListener() {
            @Override public void selected(int position) {
                preferences.edit().putInt("review_sort_mode", position).apply();
            }
        });
        card.addView(reviews, margins(0, 8, 0, 0));
        content.addView(card, margins(0, 0, 0, 16));
    }

    private void addRuntimeRow(Entry entry) {
        LinearLayout card = card();
        card.addView(text(entry.title, 19, TEXT));
        card.addView(text(entry.description, 14, MUTED), margins(0, 8, 0, 12));
        Switch toggle = new Switch(this);
        toggle.setText("Enabled");
        toggle.setTextColor(TEXT);
        toggle.setChecked(preferences.getBoolean(entry.key, entry.initial));
        toggle.setOnCheckedChangeListener((button, checked) ->
            preferences.edit().putBoolean(entry.key, checked).apply());
        card.addView(toggle);
        content.addView(card, margins(0, 0, 0, 16));
    }

    private void addBuildRow(Entry entry) {
        LinearLayout card = card();
        card.addView(text(entry.title, 19, TEXT));
        card.addView(text(entry.description, 14, MUTED), margins(0, 8, 0, 12));
        card.addView(text("APPLIED DURING PATCHING", 12, ACCENT));
        content.addView(card, margins(0, 0, 0, 16));
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(24, 24, 24, 24);
        card.setBackgroundColor(CARD);
        return card;
    }

    private TextView text(String value, int size, int color) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setLineSpacing(0, 1.15f);
        return view;
    }

    private void section(String title) {
        content.addView(text(title, 15, ACCENT), margins(0, 28, 0, 12));
    }

    private void cardButton(String title, String description, View.OnClickListener listener) {
        LinearLayout card = card();
        card.addView(text(title, 19, TEXT));
        card.addView(text(description, 14, MUTED), margins(0, 8, 0, 12));
        Button button = new Button(this);
        button.setText(title);
        button.setOnClickListener(listener);
        card.addView(button);
        content.addView(card, margins(0, 0, 0, 16));
    }

    private LinearLayout.LayoutParams margins(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, right, bottom);
        return params;
    }

    private void addLink(String label, String url) {
        Button button = new Button(this);
        button.setText(label);
        button.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        content.addView(button, margins(0, 0, 0, 8));
    }

    private abstract static class SimpleSelectionListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
            selected(position);
        }
        @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        abstract void selected(int position);
    }
}
