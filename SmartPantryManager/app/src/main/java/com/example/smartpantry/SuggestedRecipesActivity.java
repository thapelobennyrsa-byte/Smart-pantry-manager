package com.example.smartpantry;
import android.content.Intent; import android.os.Bundle; import android.view.*; import android.widget.*; import java.util.*;
public class SuggestedRecipesActivity extends BaseActivity {
    @Override protected void onCreate(Bundle b){super.onCreate(b);setup("Suggested Recipes");nav();
        List<Recipe> recipes=new DatabaseHelper(this).getStrictSuggestions();
        if(recipes.isEmpty()){TextView t=new TextView(this);t.setText("No recipes match your pantry yet. Add more ingredients, or check quantities.");t.setTextSize(18);page.addView(t);return;}
        ListView list=new ListView(this); ArrayList<String> names=new ArrayList<>();for(Recipe r:recipes)names.add(r.name);
        list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,names));page.addView(list,new android.widget.LinearLayout.LayoutParams(-1,0,1));
        list.setOnItemClickListener((p,v,pos,id)->{Intent in=new Intent(this,RecipeDetailActivity.class);in.putExtra("recipeId",recipes.get(pos).id);startActivity(in);});
    }
}
