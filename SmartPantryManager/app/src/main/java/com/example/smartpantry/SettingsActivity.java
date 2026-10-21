package com.example.smartpantry;
import android.os.Bundle; import android.widget.*;
public class SettingsActivity extends BaseActivity {
    @Override protected void onCreate(Bundle b){super.onCreate(b);setup("Settings");nav();
        TextView info=new TextView(this);info.setTextSize(17);info.setText("Smart Pantry Manager\n\nDatabase: SQLite (stored locally on this device).\n\nRecipe quantities use base quantities defined in each recipe. Enter pantry quantities using the same unit/quantity convention for accurate strict matching.\n\nExpiry dates are optional notes in YYYY-MM-DD format.");page.addView(info);
        Switch alerts=new Switch(this);alerts.setText("Show expiring-soon reminder preference");alerts.setChecked(getPreferences(0).getBoolean("alerts",true));alerts.setOnCheckedChangeListener((v,c)->getPreferences(0).edit().putBoolean("alerts",c).apply());page.addView(alerts);
    }
}
