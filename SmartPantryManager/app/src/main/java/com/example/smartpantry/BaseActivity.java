package com.example.smartpantry;
import android.app.Activity; import android.graphics.Color; import android.view.*; import android.widget.*;
public class BaseActivity extends Activity {
    LinearLayout page;
    void setup(String title) {
        page=new LinearLayout(this); page.setOrientation(LinearLayout.VERTICAL); page.setPadding(24,24,24,16);
        page.setBackgroundColor(Color.rgb(248,250,247));
        TextView heading=new TextView(this); heading.setText(title); heading.setTextSize(26); heading.setTextColor(Color.rgb(30,75,55)); heading.setPadding(0,4,0,16); page.addView(heading);
        setContentView(page);
    }
    Button button(String label, Runnable action) {
        Button b=new Button(this); b.setText(label); b.setOnClickListener(v->action.run()); return b;
    }
    void nav() {
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(button("Pantry",()->startActivity(new android.content.Intent(this,MainActivity.class))),new LinearLayout.LayoutParams(0,-2,1));
        row.addView(button("Suggestions",()->startActivity(new android.content.Intent(this,SuggestedRecipesActivity.class))),new LinearLayout.LayoutParams(0,-2,1));
        row.addView(button("Settings",()->startActivity(new android.content.Intent(this,SettingsActivity.class))),new LinearLayout.LayoutParams(0,-2,1));
        page.addView(row);
    }
}
