package com.example.smartpantry;
import android.os.Bundle; import android.widget.*;
public class IngredientFormActivity extends BaseActivity {
    DatabaseHelper db; long id=-1; EditText name,qty,unit,expiry;
    @Override protected void onCreate(Bundle b) { super.onCreate(b); db=new DatabaseHelper(this); id=getIntent().getLongExtra("id",-1); setup(id==-1?"Add ingredient":"Edit ingredient");
        name=field("Ingredient name"); qty=field("Quantity (number)"); unit=field("Unit (e.g. g, ml, pieces)"); expiry=field("Expiry date (optional, YYYY-MM-DD)");
        if(id!=-1){name.setText(getIntent().getStringExtra("name"));qty.setText(String.valueOf(getIntent().getDoubleExtra("quantity",1)));unit.setText(getIntent().getStringExtra("unit"));expiry.setText(getIntent().getStringExtra("expiry"));}
        page.addView(button("Save",()->save())); page.addView(button("Cancel",()->finish()));
    }
    EditText field(String hint) { EditText e=new EditText(this); e.setSingleLine(true); e.setHint(hint); page.addView(e); return e; }
    void save() {
        String n=name.getText().toString().trim(), u=unit.getText().toString().trim(), q=qty.getText().toString().trim(), ex=expiry.getText().toString().trim();
        if(n.isEmpty()||u.isEmpty()||q.isEmpty()){Toast.makeText(this,"Name, quantity and unit are required.",Toast.LENGTH_LONG).show();return;}
        double amount; try { amount=Double.parseDouble(q); if(amount<=0) throw new NumberFormatException(); } catch(NumberFormatException e){qty.setError("Enter a quantity greater than zero");return;}
        if(id==-1) db.addIngredient(n,amount,u,ex); else db.updateIngredient(id,n,amount,u,ex);
        Toast.makeText(this,"Ingredient saved",Toast.LENGTH_SHORT).show(); finish();
    }
}
