package com.dev_bourheem.hadi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.Login.ForQuotas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public Button addBut, plus, minus;
    public Spinner qtypSpinner;
    AutoCompleteTextView NameIn, moolhanotNm;
    public EditText PriceIn, quantity;
    public TextView DateV1, RedL, OrangeL, GreenL, QuotaLeftNm, LeftOut, TotalOut, TotalallOut, hereisyourQuota;
    public Switch Guestmode;
    public boolean ischecked;
    public double LeftOfQuota, ItemPriceDbl, Quotafrom_database, GestQuotafrom_databse;
    public String date, ItemNameStr, Sir;
    public double Quota, Qnt;
    public ArrayList<String> allList;
    public ArrayList<String> Molhanot;
    public ArrayAdapter<String> MolhntautoCompleteAdapter;
    public ArrayAdapter<String> AutoCompleteAdapter, QuanSpinAdapter;
    MyDataBaseCreator MDBC;
    ForQuotas forQutaOC;
    AdView admain;
    String[] QTypes = {"","واحدة"," كيلو", "لتر", "متر", "صندوق","علبة"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ShowList_M:
                OpentAvtivity2();
                return true;
            case R.id.Settings:
                OpentSettings();
                return true;
            case R.id.exit_M:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButonsDeclare();
        MDBC = new MyDataBaseCreator(getApplicationContext());
        forQutaOC = new ForQuotas(getApplicationContext());
        GetQuotaFromDataBZ();
        TraficLight();
        fillsugest();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        admain.loadAd(adRequest);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qnt = Double.valueOf(quantity.getText().toString().trim());
                Qnt = Qnt + 0.5;
                quantity.setText(""+Qnt);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qnt = Double.valueOf(quantity.getText().toString().trim());
                if (Qnt > 0) Qnt = Qnt - 0.5;
                quantity.setText("" + Qnt);
            }
        });

        DateV1.setText(GetDate());


        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PriceIn.getText().toString().trim().length() != 0 && NameIn.getText().toString().trim().length() != 0) {
                    LoadDatabase();
                    GetItemNameFromdatabase();
                    GetmolhanotFromdatabase();
                    GetQuotaFromDataBZ();
                    TraficLight();
                    PriceIn.getText().clear();
                    NameIn.getText().clear();
                    fillsugest();
                } else MsgBox("المرجو ادخال المعلومات");
            }
        });

        Guestmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked = Guestmode.isChecked();
                if (ischecked) {
                    MsgBox("وضع (الضيوف )");
                    GetQuotaFromDataBZ();
                    TraficLight();
                } else {
                    MsgBox("الوضع العائلي");
                    GetQuotaFromDataBZ();
                    TraficLight();
                }


            }
        });

    }

    public void ButonsDeclare() {
        DateV1 = findViewById(R.id.dateView);
        QuotaLeftNm = findViewById(R.id.QuotaLeftNm);
        GreenL = findViewById(R.id.BtnGreen);
        TotalallOut = findViewById(R.id.TotalallOut);
        Guestmode = findViewById(R.id.SwitchGuest);
        OrangeL = findViewById(R.id.BtnOrange);
        hereisyourQuota = findViewById(R.id.hereisurqt);
        RedL = findViewById(R.id.BtnRed);
        plus = findViewById(R.id.plus);
        addBut = findViewById(R.id.AddBtn);
        minus = findViewById(R.id.minus);
        qtypSpinner = findViewById(R.id.QuanType);
        NameIn = findViewById(R.id.ItemNameIn);
        moolhanotNm = findViewById(R.id.molhanoutNameIn);
        PriceIn = findViewById(R.id.ItemPriceIn);
        LeftOut = findViewById(R.id.QuotaLeftOut);
        TotalOut = findViewById(R.id.TotalTodayOut);
        quantity = findViewById(R.id.quantity);
        admain = findViewById(R.id.admain);


    }
// this opens list activity
    public void OpentAvtivity2() {
        final Intent intent1;
        intent1 = new Intent(this, Main2Activity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent1);
    }

    /**
     * this method fills the autocomplete Edittextview
     */
    public void fillsugest() {
        Molhanot = new ArrayList<>();
        allList = new ArrayList<String>();

        QuanSpinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, QTypes);
     //   qtypSpinner.
        qtypSpinner.setAdapter(QuanSpinAdapter);
        Molhanot.add(getString(R.string.unknown));
        GetItemNameFromdatabase();
        AutoCompleteAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, allList);
        NameIn.setAdapter(AutoCompleteAdapter);
        GetmolhanotFromdatabase();
        MolhntautoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Molhanot);
        moolhanotNm.setAdapter(MolhntautoCompleteAdapter);
    }

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent(this, Settings.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    public String GetDate() {
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        date = dateFormat.format(currentTime);
        ///Intent dateIntent= new Intent(date);
        //startActivity(dateIntent);
        return date;
    }


    // this method calculates the limit (Quota ) according to the switch and according to the user settings
    public double GetQuota(double guesQt, double quta) {
        if (Guestmode.isChecked()) {
            Quota = quta;
            hereisyourQuota.setText("" + quta);
            // MsgBox(getString(R.string.guestmodeon));
        } else {
            Quota = guesQt;
            hereisyourQuota.setText("" + guesQt);
        }
        // MsgBox(getString(R.string.guestmodeoff));
        return Quota;
    }

    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void LoadDatabase() {
// get !!!!!!!!!edditext input to vars.
        String Quantifiier = qtypSpinner.getSelectedItem().toString().trim();
        Qnt = Double.valueOf(quantity.getText().toString().trim());
        String d = GetDate();
        ItemPriceDbl = Double.parseDouble(PriceIn.getText().toString().trim());
        ItemPriceDbl = Qnt * ItemPriceDbl;
        ItemNameStr = NameIn.getText().toString().trim();
        Sir = moolhanotNm.getText().toString().trim();
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectData(Qnt, Quantifiier, ItemNameStr, ItemPriceDbl, Sir, d);
        if (newRowAdded) {
            MsgBox("المعلومات تسجلت");
        } else MsgBox("المعلومات لم تسجل");
    }


    //this method gets the Sum of all elements in ItemPrice Column
    public void getTotal(double DefQuota, double DefGuestQuotq) {

        NumberFormat mfr = new DecimalFormat("0.00");
        double itemsSum;
        Cursor c = MDBC.GetSum();
        if (c.getCount() == 0) {
            MsgBox("لايوجد مجموع");

        } else {
            while (c.moveToNext()) {
                itemsSum = c.getDouble(0);
                //closing cursor so as not to bring anything else or ruin sth

                TotalOut.setText("" + mfr.format(itemsSum));
                GetQuota(DefQuota, DefGuestQuotq);
                LeftOfQuota = Quota - itemsSum;
                LeftOut.setText(" " + mfr.format(LeftOfQuota));
                //localDatabase.close();
            }
        }
    }

    // this method controlls Traffic Light and the text with it
    public void TraficLight() {
        double halfquota = Quota / 2;
        if (LeftOfQuota < Quota && LeftOfQuota > 0) {
            RedL.setVisibility(View.INVISIBLE);
            OrangeL.setVisibility(View.INVISIBLE);
            GreenL.setVisibility(View.VISIBLE);
            QuotaLeftNm.setTextColor(Color.parseColor("#64DD17"));
        }
        if (LeftOfQuota < halfquota && LeftOfQuota > 0) {
            GreenL.setVisibility(View.INVISIBLE);
            OrangeL.setVisibility(View.VISIBLE);
            QuotaLeftNm.setTextColor(Color.parseColor("#FF6D00"));
        }
        if (LeftOfQuota < 0) {
            GreenL.setVisibility(View.INVISIBLE);
            OrangeL.setVisibility(View.INVISIBLE);
            RedL.setVisibility(View.VISIBLE);
            QuotaLeftNm.setTextColor(Color.parseColor("#D50000"));
        }
    }


    public void onDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("تحذير")
                .setMessage(getString(R.string.surewannaadd))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        LoadDatabase();
                        GetItemNameFromdatabase();
                        GetmolhanotFromdatabase();
                        GetQuotaFromDataBZ();
                        TraficLight();
                        PriceIn.getText().clear();
                        NameIn.getText().clear();

                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void GetQuotaFromDataBZ() {
        forQutaOC = new ForQuotas(getApplicationContext());

        Cursor qfinder = forQutaOC.JibData();

        if (qfinder.getCount() == 0) {
            MsgBox(getString(R.string.noquotafound));
            OpentSettings();
            getTotal(0, 0);
        } else {
            while (qfinder.moveToNext()) {
                Quotafrom_database = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col11));
                GestQuotafrom_databse = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col22));
                getTotal(Quotafrom_database, GestQuotafrom_databse);
                getTotalAll();
                // setusername.setText(""+GQttoDabz);
                //setGuestQta.setText(""+QttoDabz);
            }
            qfinder.close();
        }
    }
// this method gets product names from database
    public void GetItemNameFromdatabase() {
        allList.clear();
        MDBC = new MyDataBaseCreator(this);
        Cursor itemNameCursor = MDBC.GetItemNames();

        if (itemNameCursor.getCount() == 0) MsgBox(getString(R.string.noitemnamefound));
        else {
            while (itemNameCursor.moveToNext()) {
                allList.add(itemNameCursor.getString(itemNameCursor.getColumnIndex(MyDataBaseCreator.col1)));

            }
        }
    }
// this method gets shop name from database
    public void GetmolhanotFromdatabase() {
        Molhanot.clear();
        MDBC = new MyDataBaseCreator(this);
        Cursor itemNameCursor = MDBC.GetMolhanot();

        if (itemNameCursor.getCount() == 0) {
            MsgBox(getString(R.string.noshopname));

        } else {
            while (itemNameCursor.moveToNext()) {
                Molhanot.add(itemNameCursor.getString(itemNameCursor.getColumnIndex(MyDataBaseCreator.person)));

            }
        }
    }
//this method gets the total of all product items from data base
    public void getTotalAll() {
        NumberFormat mfr = new DecimalFormat("0.00");
        double itemsSumall;
        Cursor c = MDBC.GetSumall();
        if (c.getCount() == 0) {
            MsgBox("لايوجد مجموع");

        } else {
            while (c.moveToNext()) {
                itemsSumall = c.getDouble(0);
                //closing cursor so as not to bring anything else or ruin sth

                TotalallOut.setText(String.valueOf(mfr.format(itemsSumall)));

            }
        }
    }
}