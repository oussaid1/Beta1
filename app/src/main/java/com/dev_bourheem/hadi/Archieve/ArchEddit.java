package com.dev_bourheem.hadi.Archieve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.mainStuff.MainActivity;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.R;
import com.dev_bourheem.hadi.mainStuff.exampleitem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;


public class ArchEddit extends AppCompatActivity {
    private EditText ItemNameMod, QuantityMod, PriceMod, ShopNameMod, DateMod;
    private  MyDataBaseCreator MDBCR;
    private  AdView Edit_ad;
    private  Button delete;
    private Spinner delSpinner;

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
        setContentView(R.layout.activity_arch_eddit);
        Edit_ad = findViewById(R.id.Edit_ad);
        ItemNameMod = findViewById(R.id.ItemNameModArch);
        QuantityMod = findViewById(R.id.quantityModArch);
        PriceMod = findViewById(R.id.priceModArch);
        delSpinner = findViewById(R.id.spinnerDeleteArch);
        delete = findViewById(R.id.deleArch);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder( ArchEddit.this)
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
        AdsEditActivityArch();
    }

    private void AdsEditActivityArch() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        Edit_ad.loadAd(adRequest);
    }

    private void GetThem() {
        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("ArchExampleItem");
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

    private void UpdateDB() {
        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("ArchExampleItem");
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

    private void DelItem() {

        Intent intent = getIntent();
        exampleitem exampleitem = intent.getParcelableExtra("ArchExampleItem");
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

    private void MsgBox(String mess, int p) {
        Toast.makeText(ArchEddit.this, mess, p);
    }


    private void onDialogueUpdate() {
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

    private void FillWithByShop() {
        List<String> persons = new ArrayList<>();
        SQLiteDatabase db = MDBCR.getWritableDatabase();
        Cursor cursor = db.rawQuery("select distinct " + DbContractor.TableColumns.MShopName + " from " + DbContractor.TableColumns.MainTable + " ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String shopName = cursor.getString(cursor.getColumnIndex(DbContractor.TableColumns.MShopName));
                persons.add(shopName);
                cursor.moveToNext();
            }
            ArrayAdapter<String> Arada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, persons);
            delSpinner = findViewById(R.id.spinnerDelete);
            delSpinner.setAdapter(Arada);
        }
        cursor.close();
    }

    private void onDialogueDelete() {
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

    private void OpenListItems() {
        Intent inte = new Intent(ArchEddit.this, MainActivity.class);
        startActivity(inte);
    }

    private void DeleteBy(String molhanot) {
        SQLiteDatabase db = MDBCR.getWritableDatabase();
        long deleted = db.delete( DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MShopName + " = ?", new String[]{molhanot});
        if (deleted > 0) {
            Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
