package com.dev_bourheem.hadi.ArchivePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.Payment.PaymentEddit;
import com.dev_bourheem.hadi.Payment.PaymentItemsClass;
import com.dev_bourheem.hadi.R;
import com.dev_bourheem.hadi.mainStuff.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ArPaymentEdit extends AppCompatActivity {

    private EditText ArpaidAmountEd, ArpaidShopNameEd, ArpaymentDAteEd;
    private MyDataBaseCreator MDBCR;
    private AdView Edit_ad;

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
        setContentView(R.layout.activity_ar_payment_edit);
        Edit_ad = findViewById(R.id.Edit_ad);
        ArpaidAmountEd = findViewById(R.id.ArpaidAmountView);
        ArpaidShopNameEd = findViewById(R.id.ArpaidShopNameView);
        ArpaymentDAteEd = findViewById(R.id.ArpaymentDAteView);
        MDBCR = new MyDataBaseCreator(this);
        GetThem();
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
        ArPaymentExampleItem exampleitem = intent.getParcelableExtra("ArPaymentExampleItem");

        String paidAmount = null;
        if (exampleitem != null) {
            paidAmount = exampleitem.getItemPrice();
        }
        String ShopName = null;
        if (exampleitem != null) {
            ShopName = exampleitem.getShopName();
        }
        String paymentDAte = null;
        if (exampleitem != null) {
            paymentDAte = exampleitem.getDateBought();
        }


        ArpaidAmountEd.setText(paidAmount);
        ArpaidShopNameEd.setText(ShopName);
        ArpaymentDAteEd.setText(paymentDAte);

    }

    private void UpdateDB() {
        Intent intent = getIntent();
        ArPaymentExampleItem exampleitem = intent.getParcelableExtra("ArPaymentExampleItem");
        String idd = null;
        if (exampleitem != null) {
            idd = exampleitem.getARIdno();
        }

        double PaidAmount = Double.parseDouble(ArpaidAmountEd.getText().toString().trim());
        String PaidShop = ArpaidShopNameEd.getText().toString().trim();
        String PayDate = ArpaymentDAteEd.getText().toString().trim();

        boolean updateStatus = MDBCR.UpdateArPayment(idd, PaidShop, PaidAmount, PayDate);
        if (updateStatus) {
            MsgBox("تم الحفظ ", 1);
            OpenListItems();
        } else MsgBox("لم يتم الحفظ", 1);
    }

    public void DelItem() {
        Intent intent = getIntent();
        ArPaymentExampleItem exampleitem = intent.getParcelableExtra("PaymentEdit");
        String idd = null;
        if (exampleitem != null) {
            idd = exampleitem.getARIdno();
        }
        boolean updateStatus = MDBCR.DeleteItemSelected(DbContractor.TableColumns.ArchivePaymentTable, idd);
        if (updateStatus) {
            MsgBox("تم الحذف ", 1);
            OpenListItems();
        } else MsgBox("لم يتم الحذف", 1);

    }

    public void MsgBox(String mess, int p) {
        Toast.makeText(ArPaymentEdit.this, mess, p).show();
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
        Intent inte = new Intent(ArPaymentEdit.this, MainActivity.class);
        startActivity(inte);
    }
}

