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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

public final class ConsumerClarityActivity extends Activity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = ConsumerClaritySettings.get(this);
        ScrollView scroll = new ScrollView(this);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(48, 36, 48, 48);
        scroll.addView(content);

        TextView title = new TextView(this);
        title.setText("Consumer Clarity");
        title.setTextSize(26);
        title.setTextColor(Color.rgb(255, 90, 0));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        content.addView(title, margins(0, 0, 0, 36));

        TextView sorting = new TextView(this);
        sorting.setText("Sponsored restaurants");
        sorting.setTextSize(20);
        content.addView(sorting, margins(0, 0, 0, 8));
        Spinner sponsored = new Spinner(this);
        String[] sponsoredValues = {"Demote sponsored", "Hide sponsored", "Original order"};
        sponsored.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sponsoredValues));
        sponsored.setSelection(preferences.getInt("sponsored_mode", 0));
        sponsored.setOnItemSelectedListener(new SimpleSelectionListener() {
            @Override public void selected(int position) {
                preferences.edit().putInt("sponsored_mode", position).apply();
            }
        });
        content.addView(sponsored, margins(0, 0, 0, 24));

        TextView reviews = new TextView(this);
        reviews.setText("Review order");
        reviews.setTextSize(20);
        content.addView(reviews, margins(0, 0, 0, 8));
        Spinner reviewOrder = new Spinner(this);
        String[] reviewValues = {"Newest first", "Oldest first", "Highest rated", "Original order"};
        reviewOrder.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reviewValues));
        reviewOrder.setSelection(preferences.getInt("review_sort_mode", 0));
        reviewOrder.setOnItemSelectedListener(new SimpleSelectionListener() {
            @Override public void selected(int position) {
                preferences.edit().putInt("review_sort_mode", position).apply();
            }
        });
        content.addView(reviewOrder, margins(0, 0, 0, 24));

        addSwitch(content, "Reduce analytics tracking", "reduce_tracking");
        addSwitch(content, "Hide Braze in-app popups", "hide_braze_popups");
        addSwitch(content, "Disable Play license gate", "disable_license_gate");
        addSwitch(content, "Open links in default browser", "open_links_externally");

        TextView developer = new TextView(this);
        developer.setText("Developer information\n\nNo separate Talabat developer menu was found. Feature flags are server-controlled experiments, not safe universal switches. Native hooks check app version and instruction bytes before activating.");
        developer.setTextSize(16);
        content.addView(developer, margins(0, 24, 0, 24));

        addLink(content, "Talabat FAQ", "https://www.talabat.com/en/uae/faq?src=app&v=2");
        addLink(content, "Talabat on Facebook", "https://www.facebook.com/talabat");
        addLink(content, "Research and limitations", "https://github.com/AltF4G0x/consumer-clarity-research");
        addLink(content, "Patch source and releases", "https://github.com/AltF4G0x/consumer-clarity/releases/latest");

        setContentView(scroll);
    }

    private LinearLayout.LayoutParams margins(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, right, bottom);
        return params;
    }

    private void addSwitch(LinearLayout parent, String label, String key) {
        Switch toggle = new Switch(this);
        toggle.setText(label);
        toggle.setTextSize(18);
        toggle.setChecked(preferences.getBoolean(key, !key.equals("open_links_externally")));
        toggle.setOnCheckedChangeListener((button, checked) ->
            preferences.edit().putBoolean(key, checked).apply());
        parent.addView(toggle, margins(0, 0, 0, 12));
    }

    private void addLink(LinearLayout parent, String label, String url) {
        Button button = new Button(this);
        button.setText(label);
        button.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        parent.addView(button, margins(0, 0, 0, 8));
    }

    private abstract static class SimpleSelectionListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
            selected(position);
        }
        @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        abstract void selected(int position);
    }
}
