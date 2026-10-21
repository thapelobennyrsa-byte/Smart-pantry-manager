package com.example.smartpantry;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="smart_pantry.db";
    public DatabaseHelper(Context c) { super(c, DB_NAME, null, 1); }
    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes (_id INTEGER PRIMARY KEY, name TEXT NOT NULL, ingredients TEXT NOT NULL, method TEXT NOT NULL)");
        seed(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) { }
    private void seed(SQLiteDatabase db) {
        String[][] data = {
          {"Tomato egg scramble","tomato:2,egg:2","Chop tomatoes. Cook briefly, add beaten eggs and stir until set."},
          {"Garlic tomato pasta","pasta:100,tomato:2,garlic:1,oil:1","Boil pasta. Cook garlic and tomato in oil, then combine."},
          {"Potato omelette","potato:2,egg:2,oil:1","Slice and cook potato. Add beaten egg and cook through."},
          {"Banana oat bowl","banana:1,oats:50,milk:100","Slice banana and mix with oats and milk."},
          {"Peanut banana oats","banana:1,oats:50,peanut butter:1","Mix oats with water or milk, then top with banana and peanut butter."},
          {"Cheese tomato toast","bread:2,cheese:1,tomato:1","Layer tomato and cheese on bread and toast until melted."},
          {"Simple fried rice","rice:150,egg:1,carrot:1,oil:1","Cook rice. Dice carrot, stir-fry, add rice and egg, and cook through."},
          {"Carrot potato soup","carrot:2,potato:2,onion:1,water:300","Chop vegetables, simmer in water until soft, then mash."},
          {"Onion tomato relish","onion:1,tomato:2,oil:1","Chop onion and tomato. Cook slowly in oil until soft."},
          {"Garlic potato wedges","potato:3,garlic:1,oil:1","Cut potatoes into wedges, coat with garlic and oil, then bake until tender."},
          {"Apple oat porridge","apple:1,oats:50,milk:150","Cook oats in milk and stir in chopped apple."},
          {"Tuna pasta","pasta:100,tuna:1,onion:1","Boil pasta. Cook onion, mix in tuna and combine."},
          {"Bean tomato stew","beans:150,tomato:2,onion:1","Cook onion and tomato, add cooked beans and simmer."},
          {"Spinach egg saute","spinach:100,egg:2,oil:1","Saute spinach in oil and add eggs; cook until set."},
          {"Chicken rice bowl","chicken:150,rice:150,onion:1","Cook chicken fully with onion and serve with cooked rice."},
          {"Yoghurt fruit cup","yoghurt:150,banana:1,apple:1","Chop fruit and stir through yoghurt."},
          {"Corn fritters","corn:100,flour:50,egg:1,oil:1","Mix corn, flour and egg. Pan-cook spoonfuls in a little oil."},
          {"Cucumber tomato salad","cucumber:1,tomato:2","Chop cucumber and tomato and toss together."}
        };
        for(int i=0;i<data.length;i++) {
            ContentValues v=new ContentValues(); v.put("_id",i+1); v.put("name",data[i][0]); v.put("ingredients",data[i][1]); v.put("method",data[i][2]);
            db.insert("recipes",null,v);
        }
    }
    public long addIngredient(String name,double qty,String unit,String expiry) {
        ContentValues v=new ContentValues(); v.put("name",name.trim()); v.put("quantity",qty); v.put("unit",unit.trim()); v.put("expiry",expiry);
        return getWritableDatabase().insert("pantry",null,v);
    }
    public int updateIngredient(long id,String name,double qty,String unit,String expiry) {
        ContentValues v=new ContentValues(); v.put("name",name.trim()); v.put("quantity",qty); v.put("unit",unit.trim()); v.put("expiry",expiry);
        return getWritableDatabase().update("pantry",v,"_id=?",new String[]{String.valueOf(id)});
    }
    public int deleteIngredient(long id) { return getWritableDatabase().delete("pantry","_id=?",new String[]{String.valueOf(id)}); }
    public List<Ingredient> getIngredients() {
        List<Ingredient> out=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT * FROM pantry ORDER BY name",null);
        try { while(c.moveToNext()) out.add(new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4))); } finally { c.close(); }
        return out;
    }
    public List<Recipe> getRecipes() {
        List<Recipe> out=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT _id,name,ingredients,method FROM recipes ORDER BY name",null);
        try { while(c.moveToNext()) out.add(new Recipe(c.getInt(0),c.getString(1),c.getString(3),c.getString(2))); } finally { c.close(); }
        return out;
    }
    private String normalize(String s) {
        String n=s.toLowerCase(Locale.ROOT).trim();
        if(n.endsWith("ies") && n.length()>3) n=n.substring(0,n.length()-3)+"y";
        else if(n.endsWith("oes") && n.length()>3) n=n.substring(0,n.length()-2);
        else if(n.endsWith("s") && !n.endsWith("ss") && n.length()>2) n=n.substring(0,n.length()-1);
        return n;
    }
    public List<Recipe> getStrictSuggestions() {
        List<Ingredient> pantry=getIngredients(); List<Recipe> matches=new ArrayList<>();
        for(Recipe r:getRecipes()) {
            boolean all=true;
            for(Map.Entry<String,Double> req:r.ingredients.entrySet()) {
                double available=0;
                for(Ingredient p:pantry) if(normalize(p.name).equals(normalize(req.getKey()))) {
                    // Quantities are compared only when pantry and recipe quantities use a compatible base unit.
                    // Seed recipes use base quantities; users should enter quantities in the same base unit.
                    available += p.quantity;
                }
                if(available < req.getValue()) { all=false; break; }
            }
            if(all) matches.add(r);
        }
        return matches;
    }
}
