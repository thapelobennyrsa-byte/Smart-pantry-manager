package com.example.smartpantry;
import java.util.*;
public class Recipe {
    public int id; public String name, method; public Map<String,Double> ingredients = new LinkedHashMap<>();
    public Recipe(int id, String name, String method, String ingredientText) {
        this.id=id; this.name=name; this.method=method;
        for(String part: ingredientText.split(",")) {
            String[] p=part.trim().split(":");
            ingredients.put(p[0].trim(), p.length>1 ? Double.parseDouble(p[1].trim()) : 1.0);
        }
    }
}
