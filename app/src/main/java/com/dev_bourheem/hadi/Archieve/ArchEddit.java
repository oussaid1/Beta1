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
        Edit_ad = findViewById(R.id.ArchAD);
        ItemNameMod = findViewById(R.id.ItemNameModArch);
        QuantityMod = findViewById(R.id.quantityModArch);
        PriceMod = findViewById(R.id.priceModArch);

        ShopNameMod = findViewById(R.id.ShopNmviewArch);
        DateMod = findViewById(R.id.ArchdateEdit);
        MDBCR = new MyDataBaseCreator(this);
        GetThem();
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
        ArchexampleItem archExampleItem = intent.getParcelableExtra("ArchExampleItem");
        String quantity = null;
        if (archExampleItem != null) {
            quantity = archExampleItem.getQuantity();
        }
        String ItemName = null;
        if (archExampleItem != null) {
            ItemName = archExampleItem.getItemName();
        }
        String ItemPrice = null;
        if (archExampleItem != null) {
            ItemPrice = archExampleItem.getItemPrice();
        }
        String ShopName = null;
        if (archExampleItem != null) {
            ShopName = archExampleItem.getShopName();
        }
        String dateBought = null;
        if (archExampleItem != null) {
            dateBought = archExampleItem.getDateBought();
        }

        ItemNameMod.setText(ItemName);
        QuantityMod.setText(quantity);
        PriceMod.setText(ItemPrice);
        ShopNameMod.setText(ShopName);
        DateMod.setText(dateBought);

    }

    private void UpdateDB() {
        Intent intent = getIntent();
        ArchexampleItem archExampleItem = intent.getParcelableExtra("ArchExampleItem");
        String idd = null;
        if (archExampleItem != null) {
            idd = archExampleItem.getIdno();
        }
        String EdItemNameMod = ItemNameMod.getText().toString().trim();
        double EdQuantityMod = Double.parseDouble(QuantityMod.getText().toString().trim());
        double EdPriceMod = Double.parseDouble(PriceMod.getText().toString().trim());
        EdPriceMod = EdQuantityMod * EdPriceMod;
        String EdShopNameMod = ShopNameMod.getText().toString().trim();
        String EdDateMod = DateMod.getText().toString().trim();

        boolean updateStatus = MDBCR.updateDataArch(idd, EdQuantityMod, EdItemNameMod, EdPriceMod, EdShopNameMod, EdDateMod);
        if (updateStatus) {
            MsgBox("تم الحفظ ", 1);
            OpenListItems();
        } else MsgBox("لم يتم الحفظ", 1);
    }

    private void DelItem() {

        Intent intent = getIntent();
        ArchexampleItem archExampleItem = intent.getParcelableExtra("ArchExampleItem");
        String idd = null;
        if (archExampleItem != null) {
            idd = archExampleItem.getIdno();
        }
        boolean updateStatus = MDBCR.DeleteItemSelected(DbContractor.TableColumns.ArchiveTable,idd);
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


}
