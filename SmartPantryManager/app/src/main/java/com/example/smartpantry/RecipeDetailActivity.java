package com.example.smartpantry;
import android.os.Bundle; import android.widget.*; import java.util.*;
public class RecipeDetailActivity extends BaseActivity {
    @Override protected void onCreate(Bundle b){super.onCreate(b);int id=getIntent().getIntExtra("recipeId",-1);Recipe found=null;
        for(Recipe r:new DatabaseHelper(this).getRecipes())if(r.id==id)found=r;
        setup(found==null?"Recipe details":found.name);nav();
        if(found==null){page.addView(new TextView(this){{setText("Recipe not found.");}});return;}
        TextView ingredients=new TextView(this);ingredients.setTextSize(18);StringBuilder sb=new StringBuilder("Required ingredients\n");
        for(Map.Entry<String,Double> e:found.ingredients.entrySet())sb.append("• ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        ingredients.setText(sb.toString());page.addView(ingredients);
        TextView method=new TextView(this);method.setTextSize(17);method.setPadding(0,20,0,0);method.setText("Preparation method\n"+found.method);page.addView(method);
    }
}
