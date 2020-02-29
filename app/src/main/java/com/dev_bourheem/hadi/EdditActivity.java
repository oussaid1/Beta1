package com.dev_bourheem.hadi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

import static com.dev_bourheem.hadi.MyDataBaseCreator.ID;
import static com.dev_bourheem.hadi.MyDataBaseCreator.TABLE_NAME;

public class EdditActivity extends AppCompatActivity {
    EditText ItemNameMod, QuantityMod, PriceMod, ShopNameMod, DateMod;
    MyDataBaseCreator MDBCR;
    AdView Edit_ad;
    Button delete;
    Spinner delSpinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edditmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save:
                //func here
                onDialogueUpdate();
                return true;
            case R.id.deletMod:
                onDialogueDelete();

                return true;
            case R.id.exit_Mod:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eddit);
        Edit_ad = findViewById(R.id.Edit_ad);
        ItemNameMod = findViewById(R.id.ItemNameMod);
        QuantityMod = findViewById(R.id.quantityMod);
        PriceMod = findViewById(R.id.priceMod);
        delSpinner = findViewById(R.id.spinnerDelete);
        delete = findViewById(R.id.dele);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EdditActivity.this)
                        .setTitle("تحذير")
                        .setMessage(getString(R.string.surewannadelall))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteBy(delSpinner.getSelectedItem().toString());
                            }
                        })
                        .setNegativeButton(R.string.no, null).show();

            }
        });
        ShopNameMod = findViewById(R.id.shopnameMod);
        DateMod = findViewById(R.id.dateMod);
        MDBCR = new MyDataBaseCreator(this);
        GetThem();
        FillWithByShop();
        AdsEditActivity();
    }

    public void AdsEditActivity() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        Edit_ad.loadAd(adRequest);
    }

    public void GetThem() {
        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("exampleItem");
        String quantity = null;
        if (exampleitem != null) {
            quantity = exampleitem.getQuantity();
        }
        String ItemName = null;
        if (exampleitem != null) {
            ItemName = exampleitem.getItemName();
        }
        String ItemPrice = null;
        if (exampleitem != null) {
            ItemPrice = exampleitem.getItemPrice();
        }
        String ShopName = null;
        if (exampleitem != null) {
            ShopName = exampleitem.getShopName();
        }
        String dateBought = null;
        if (exampleitem != null) {
            dateBought = exampleitem.getDateBought();
        }

        ItemNameMod.setText(ItemName);
        QuantityMod.setText(quantity);
        PriceMod.setText(ItemPrice);
        ShopNameMod.setText(ShopName);
        DateMod.setText(dateBought);

    }

    public void UpdateDB() {
        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("exampleItem");
        String idd = null;
        if (exampleitem != null) {
            idd = exampleitem.getIdno();
        }
        String EdItemNameMod = ItemNameMod.getText().toString().trim();
        double EdQuantityMod = Double.parseDouble(QuantityMod.getText().toString().trim());
        double EdPriceMod = Double.parseDouble(PriceMod.getText().toString().trim());
        EdPriceMod = EdQuantityMod * EdPriceMod;
        String EdShopNameMod = ShopNameMod.getText().toString().trim();
        String EdDateMod = DateMod.getText().toString().trim();

        boolean updateStatus = MDBCR.updateData(idd, EdQuantityMod, EdItemNameMod, EdPriceMod, EdShopNameMod, EdDateMod);
        if (updateStatus) {
            MsgBox("تم الحفظ ", 1);
            OpenListItems();
        } else MsgBox("لم يتم الحفظ", 1);
    }

    public void DelItem() {

        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("exampleItem");
        String idd = null;
        if (exampleitem != null) {
            idd = exampleitem.getIdno();
        }
        boolean updateStatus = MDBCR.DeleteItemSelected(idd);
        if (updateStatus) {
            MsgBox("تم الحذف ", 1);
            OpenListItems();
        } else MsgBox("لم يتم الحذف", 1);

    }

    public void MsgBox(String mess, int p) {
        Toast.makeText(EdditActivity.this, mess, p);
    }


    public void onDialogueUpdate() {
        new AlertDialog.Builder(this)
                .setTitle("تحذير")
                .setMessage(getString(R.string.surewannaupdate))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        UpdateDB();

                    }
                })
                .setNegativeButton(R.string.no, null).show();
    }

    public void FillWithByShop() {
        List<String> persons = new ArrayList<>();
        SQLiteDatabase db = MDBCR.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct " + MyDataBaseCreator.person + " from " + TABLE_NAME + " ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String shopName = cursor.getString(cursor.getColumnIndex(MyDataBaseCreator.person));
                persons.add(shopName);
                cursor.moveToNext();
            }
            ArrayAdapter<String> Arada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, persons);
            delSpinner = findViewById(R.id.spinnerDelete);
            delSpinner.setAdapter(Arada);
        }
        cursor.close();
    }

    public void onDialogueDelete() {
        new AlertDialog.Builder(this)
                .setTitle("تحذير")
                .setMessage(getString(R.string.surewannadel))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DelItem();

                    }
                })
                .setNegativeButton(R.string.no, null).show();
    }

    public void OpenListItems() {
        Intent inte = new Intent(EdditActivity.this, MainActivity.class);
        startActivity(inte);
    }

    public void DeleteBy(String molhanot) {
        SQLiteDatabase db = MDBCR.getWritableDatabase();
        long deleted = db.delete(TABLE_NAME, MyDataBaseCreator.person + " = ?", new String[]{molhanot});
        if (deleted > 0) {
            Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
