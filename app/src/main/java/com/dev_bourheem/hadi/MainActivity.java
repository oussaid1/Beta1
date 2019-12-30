package com.dev_bourheem.hadi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public Button addBut;
    public EditText NameIn, PriceIn, moolhanotNm;
    public TextView DateV1, RedL, OrangeL, GreenL, LeftOut, TotalOut, RedText, OrangeText, GreenText, hereisyourQuota;
    public Spinner ItMSpinner, molhanotSpinner;
    public ListView ListaOut;
    public Switch Guestmode;
    public boolean ischecked;
    public double LeftOfQuota, ItemPriceDbl, Quotafrom_database, GestQuotafrom_databse;
    public String date, ItemNameStr, Sir;
    public double Quota;
    public ArrayList<String> allList;
    public ArrayList<String> Molhanot;
    public ArrayAdapter<String> MolhntSpinnerAdapter;
    public ArrayAdapter<String> SpinnerAdapter;
    MyDataBaseCreator MDBC;
    ForQuotas forQutaOC;
AdView mAdView;

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
        DateV1 = findViewById(R.id.dateView);
        RedText = findViewById(R.id.Limit);
        OrangeText = findViewById(R.id.Carefull);
        GreenText = findViewById(R.id.Good);
        GreenL = findViewById(R.id.BtnGreen);
        Guestmode = findViewById(R.id.SwitchGuest);
        ListaOut = findViewById(R.id.list_VActivity2);
        OrangeL = findViewById(R.id.BtnOrange);
        hereisyourQuota = findViewById(R.id.hereisurqt);
        RedL = findViewById(R.id.BtnRed);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ItMSpinner = findViewById(R.id.ItemNameInSp);
        NameIn = findViewById(R.id.ItemNameIn);
        moolhanotNm = findViewById(R.id.molhanoutNameIn);
        molhanotSpinner = findViewById(R.id.molhanoutNameInSp);
        molhanotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String molhanotname = String.valueOf(parent.getItemAtPosition(position));
                moolhanotNm.setText("" + molhanotname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PriceIn = findViewById(R.id.ItemPriceIn);
        LeftOut = findViewById(R.id.QuotaLeftOut);
        TotalOut = findViewById(R.id.TotalTodayOut);
        addBut = findViewById(R.id.AddBtn);
        ItMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SpineerText = String.valueOf(parent.getItemAtPosition(position));
                NameIn.setText("" + SpineerText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DateV1.setText("" + GetDate());
        MDBC = new MyDataBaseCreator(getApplicationContext());
        Molhanot = new ArrayList<>();
        allList = new ArrayList<String>();
        forQutaOC = new ForQuotas(getApplicationContext());
        GetQuotaFromDataBZ();
        TraficLight();
        Molhanot.clear();
        allList.clear();
        allList.add(getString(R.string.milk));
        Molhanot.add(getString(R.string.unknown));
        SpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, allList);
        ItMSpinner.setAdapter(SpinnerAdapter);
        MolhntSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Molhanot);
        molhanotSpinner.setAdapter(MolhntSpinnerAdapter);
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Molhanot.clear();
                allList.clear();
                if (PriceIn.length() != 0 && NameIn.length() != 0) {
                    onDialogue();

                } else MsgBox("المرجو ادخال المعلومات");
            }
        });

        Guestmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischecked = Guestmode.isChecked();
                if (ischecked) {
                    MsgBox("وضع (الضيوف )");
                } else {
                    MsgBox("الوضع العائلي");
                }
                GetQuotaFromDataBZ();
                TraficLight();
            }
        });
    }

    public void OpentAvtivity2() {
        final Intent intent1;
        intent1 = new Intent(this, Main2Activity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent1);
    }

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent(this, Settings.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    public String GetDate() {
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(" dd / MMM / yy");
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
        String hiTp = PriceIn.getText().toString();
        ItemPriceDbl = Double.parseDouble(PriceIn.getText().toString().trim());
        ItemNameStr = NameIn.getText().toString().trim();
        Sir = moolhanotNm.getText().toString().trim();
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectData(Sir, ItemNameStr, ItemPriceDbl);
        if (newRowAdded) {
            MsgBox("المعلومات تسجلت");
        } else MsgBox("المعلومات لم تسجل");
    }


    //this method gets the Sum of all elements in ItemPrice Column
    public void getTotal(double DefQuota, double DefGuestQuotq) {

        double itemsSum;
        Cursor c = MDBC.GetSum();
        if (c.getCount() == 0) {
            MsgBox("لايوجد مجموع");

        } else {
            while (c.moveToNext()) {
                itemsSum = c.getInt(0);
                //closing cursor so as not to bring anything else or ruin sth
                c.close();
                TotalOut.setText("" + itemsSum);
                GetQuota(DefQuota, DefGuestQuotq);
                LeftOfQuota = Quota - itemsSum;
                LeftOut.setText(" " + LeftOfQuota);
                //localDatabase.close();
            }
        }
    }

    // this method controlls Traffic Light and the text with it
    public void TraficLight() {
        double halfquota = Quota / 2;
        if (LeftOfQuota < Quota && LeftOfQuota > 0) {
            RedL.setVisibility(View.INVISIBLE);
            RedText.setText("");
            OrangeL.setVisibility(View.INVISIBLE);
            OrangeText.setText("");
            GreenL.setVisibility(View.VISIBLE);
            GreenText.setVisibility(View.VISIBLE);
            GreenText.setText(R.string.good);
        }
        if (LeftOfQuota < halfquota && LeftOfQuota > 0) {
            GreenL.setVisibility(View.INVISIBLE);
            GreenText.setText("");
            OrangeL.setVisibility(View.VISIBLE);
            OrangeText.setText("");
            OrangeText.setVisibility(View.VISIBLE);
            OrangeText.setText(R.string.carefull);
        }
        if (LeftOfQuota < 0) {
            GreenL.setVisibility(View.INVISIBLE);
            GreenText.setText("");
            OrangeL.setVisibility(View.INVISIBLE);
            OrangeText.setText("");
            RedL.setVisibility(View.VISIBLE);
            RedText.setVisibility(View.VISIBLE);
            RedText.setText(R.string.limit_exceeded);

        }
    }


    public void onDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("تحذير")
                .setMessage(getString (R.string.surewannaadd))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        LoadDatabase();
                        GetItemNameFromdatabase();
                        GetmolhanotFromdatabase();
                        PriceIn.setText("");
                        GetQuotaFromDataBZ();
                        TraficLight();
                        //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void GetQuotaFromDataBZ() {
        forQutaOC = new ForQuotas(getApplicationContext());

        Cursor qfinder = forQutaOC.JibData();

        if (qfinder.getCount() == 0) {
            MsgBox(getString (R.string.noquotafound));
            OpentSettings();
            getTotal(0, 0);
        } else {
            while (qfinder.moveToNext()) {
                Quotafrom_database = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col11));
                GestQuotafrom_databse = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col22));
                getTotal(Quotafrom_database, GestQuotafrom_databse);
                // setusername.setText(""+GQttoDabz);
                //setGuestQta.setText(""+QttoDabz);
            }
            qfinder.close();
        }
    }

    public void GetItemNameFromdatabase() {


        MDBC = new MyDataBaseCreator(this);
        Cursor itemNameCursor = MDBC.GetItemNames();

        if (itemNameCursor.getCount() == 0) MsgBox(getString(R.string.noitemnamefound));
        else {
            while (itemNameCursor.moveToNext()) {
                allList.add(itemNameCursor.getString(itemNameCursor.getColumnIndex(MyDataBaseCreator.col1)));

            }
        }
    }

    public void GetmolhanotFromdatabase() {

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
}