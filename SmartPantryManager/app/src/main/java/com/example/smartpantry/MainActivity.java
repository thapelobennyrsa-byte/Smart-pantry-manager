package com.example.smartpantry;
import android.content.Intent; import android.os.Bundle; import android.view.*; import android.widget.*; import java.util.*;
public class MainActivity extends BaseActivity {
    DatabaseHelper db;
    @Override protected void onCreate(Bundle b) { super.onCreate(b); db=new DatabaseHelper(this); }
    @Override protected void onResume() { super.onResume(); render(); }
    void render() {
        setup("Smart Pantry"); nav();
        page.addView(button("+ Add ingredient",()->startActivity(new Intent(this,IngredientFormActivity.class))));
        ListView list=new ListView(this); List<Ingredient> items=db.getIngredients();
        ArrayList<String> labels=new ArrayList<>();
        for(Ingredient i:items) labels.add(i.name+"  •  "+i.quantity+" "+i.unit+(i.expiry==null||i.expiry.isEmpty()?"":"  •  exp "+i.expiry));
        list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,labels));
        page.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        list.setOnItemClickListener((p,v,pos,id)->{
            Ingredient i=items.get(pos); Intent in=new Intent(this,IngredientFormActivity.class);
            in.putExtra("id",i.id); in.putExtra("name",i.name); in.putExtra("quantity",i.quantity); in.putExtra("unit",i.unit); in.putExtra("expiry",i.expiry); startActivity(in);
        });
        list.setOnItemLongClickListener((p,v,pos,id)->{
            Ingredient i=items.get(pos);
            new android.app.AlertDialog.Builder(this).setTitle("Delete "+i.name+"?").setMessage("This removes the pantry item.")
              .setPositiveButton("Delete",(d,w)->{db.deleteIngredient(i.id);render();}).setNegativeButton("Cancel",null).show(); return true;
        });
        if(items.isEmpty()) { TextView empty=new TextView(this); empty.setText("Your pantry is empty. Add ingredients to get started."); page.addView(empty); }
    }
}
