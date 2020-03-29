package com.dev_bourheem.hadi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dev_bourheem.hadi.Login.ForQuotas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {
    public Spinner SearchSpinerSub1, SearchSpinerSub2, SearchSpinerSub3;
    public TextView DateV1, RedL, RedL2, yellowL, yellowL2, OrangeL, OrangeL2, GreenL, GreenL2, QuotaLeftNm, LeftOut, SumOutBy, TotalallOut, hereisyourQuota;
    public Switch Guestmode;
    public boolean ischecked;
    public double Quotafrom_database, GestQuotafrom_databse;
    public double Quota = 0, sumtoday = 0, leftOfQuota = 0, Qnt1 = 1;
    ArrayList<String> allList;
    ArrayList<String> CategoryList;
    ArrayList<String> Sub2List;
    ArrayList<String> Sub1List;
    ArrayList<String> Molhanot;
    ArrayAdapter<String> MolhntautoCompleteAdapter, SearchSpinnerAdapter, SearchSpinnerSub2Adapter, SearchSpinnerSub3Adapter;
    ArrayAdapter<String> AutoCompleteAdapter;
    ArrayAdapter<String> QuanSpinAdapter;
    MyDataBaseCreator MDBC;
    ForQuotas forQutaOC;
    AdView admain;
    FloatingActionButton floatingButon;
    String[] QTypes = {"واحدة", " كيلو", "لتر", "متر", "صندوق", "علبة"};
    private double SumAll = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateV1 = findViewById(R.id.dateView);
        floatingButon = findViewById(R.id.fab);
        QuotaLeftNm = findViewById(R.id.QuotaLeftNm);
        GreenL = findViewById(R.id.BtnGreen);
        GreenL2 = findViewById(R.id.BtnGreen2);
        yellowL = findViewById(R.id.BtnYellow);
        yellowL2 = findViewById(R.id.BtnYellow2);
        OrangeL = findViewById(R.id.BtnOrange);
        OrangeL2 = findViewById(R.id.BtnOrange2);
        RedL = findViewById(R.id.BtnRed);
        RedL2 = findViewById(R.id.BtnRed2);
        TotalallOut = findViewById(R.id.TotalallOut);
        Guestmode = findViewById(R.id.SwitchGuest);
        hereisyourQuota = findViewById(R.id.hereisurqt);

        LeftOut = findViewById(R.id.QuotaLeftOut);
        SumOutBy = findViewById(R.id.TotalTodayOut);
        admain = findViewById(R.id.admain);
        SearchSpinerSub1 = findViewById(R.id.SumCategory);
        SearchSpinerSub2 = findViewById(R.id.Sumsearche);
        SearchSpinerSub3 = findViewById(R.id.SumsearchBydate);
        Molhanot = new ArrayList<>();
        allList = new ArrayList<String>();
        CategoryList = new ArrayList<>();
        Sub1List = new ArrayList<>();
        Sub2List = new ArrayList<>();
        MDBC = new MyDataBaseCreator(getApplicationContext());
        forQutaOC = new ForQuotas(getApplicationContext());


        Molhanot.add(getString(R.string.unknown));
        GetItemNameFromdatabase();

        GetmolhanotFromdatabase();


        SearchSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, CategoryList);
        SearchSpinnerSub2Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Sub1List);
        SearchSpinnerSub3Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Sub2List);

        DateV1.setText(GetDate());

        Guestmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ischecked = Guestmode.isChecked();
                if (ischecked) {
                    MsgBox("وضع (الضيوف )");
                    showQuota();
                    TraficLight(sumtoday);
                } else {
                    MsgBox("الوضع العائلي");
                    showQuota();
                    TraficLight(sumtoday);
                }
            }
        });
        floatingButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupDialogue();
            }
        });
        SearchSpinerSub1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner1Fill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SearchSpinerSub2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = SearchSpinerSub1.getSelectedItem().toString().trim();
                String sellection2 = SearchSpinerSub2.getSelectedItem().toString().trim();

                switch (sellection1) {

                    case "حسب المحل":
                        FillWithDaysforShop(sellection2);
                        break;
                    case "حسب اليوم":
                        GetSumByDate(sellection2);
                        //FillWithDaysforShopEmpty();
                        break;
                    case "حسب السلعة":
                    FillWithItemsDates(sellection2);
                      //  GetSumByItem(sellection2);
                        //  FillWithDaysforShopEmpty();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SearchSpinerSub3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = SearchSpinerSub1.getSelectedItem().toString().trim();
                String sellection2 = SearchSpinerSub2.getSelectedItem().toString().trim();
                String sellection3 = SearchSpinerSub3.getSelectedItem().toString().trim();

                //this is the mai serach spinner to seach by category
                switch (sellection1) {
                    case "حسب المحل":
                        //this is for getting sum of all items if * is selected in spinner 3
                        //otherwise it will get the sum by shopName and Date
                        if (sellection3.equals("*")) {
                            GetSumByShop(sellection2);
                        } else
                            GetSumByShopDate(sellection2, sellection3);
                        break;
                    case "حسب اليوم":
                        // this fills it with with empty so that it shows the sum of all days in
                        FillWithDaysforShopEmpty();
                        break;
                    case "حسب السلعة":
                        if (sellection3.equals("*")) {
                         GetSumByItem(sellection2);
                        } else
                       GetSumByItemsByDate(sellection2,sellection3);
                        //  FillWithDaysforShopEmpty();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fillcategory();
        TotalallOut.setText(String.valueOf(getTotalAll()));
        showQuota();
        TraficLight(sumtoday);
        ADSmainActivity();
    }

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
            case R.id.stats:
                OpenStats();
                return true;
            case R.id.Settings:
                OpentSettings();
                isStoragePermissionGranted();
                return true;
            case R.id.exit_M:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("per", "Permission is granted");
                return true;
            } else {
                Log.v("per", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("per", "Permission is granted");
            return true;
        }
    }



    public void ADSmainActivity() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        admain.loadAd(adRequest);
    }

    public void fillcategory() {
        CategoryList.add("حسب المحل");
        CategoryList.add("حسب اليوم");
        CategoryList.add("حسب السلعة");
        SearchSpinerSub1.setAdapter(SearchSpinnerAdapter);
    }

    public void Spinner1Fill() {
        String sellection1 = SearchSpinerSub1.getSelectedItem().toString().trim();

        switch (sellection1) {
            case "حسب المحل":
                fillwithShopNm();

                break;
            case "حسب اليوم":
                FillWithDays();
                //  FillWithDaysforShopEmpty();
                break;
            case "حسب السلعة":
                FillWithItems();
                //  FillWithDaysforShopEmpty();
                break;
        }
    }

    // this opens list activity
    public void OpentAvtivity2() {
        final Intent intent1;
        intent1 = new Intent(this, ListActivity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent1);
    }

    /**
     * this method fills the autocomplete Edittextview
     */

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent(this, Settings.class);
        startActivity(intent2);
    }

    public void OpenStats() {
        final Intent intent2;
        intent2 = new Intent(this, stats.class);
        startActivity(intent2);
    }

    public static String GetDate() {
        Date currenttime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return dateFormat.format(currenttime);
    }

    // this method calculates the limit (Quota ) according to the switch and according to the user settings


    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean LoadDatabase(double qntiti, String quqntifier, String itemNm, double itemPrix, String shopNm) {
//
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectData(qntiti, quqntifier, itemNm, itemPrix, shopNm, GetDate());
        if (newRowAdded) {
            MsgBox("تم");
            return true;
        } else MsgBox("لم يتم");
        return false;
    }


    // this method controlls Traffic Light and the text with it
    public void TraficLight(Double sum2day) {
        leftOfQuota = Quota - sum2day;
        LeftOut.setText(String.valueOf(leftOfQuota));

        if (sum2day <= Quota / 3) {
            Greenlight();
        } else if ((sum2day < Quota / 2 && sum2day > Quota / 3)) {
            Yellowlight();
        } else if (sum2day < Quota && sum2day > Quota / 2) {
            OrangeLight();
        } else if (sum2day >= Quota) {
            Redlight();
        }
    }

    public void Greenlight() {
        RedL.setVisibility(View.INVISIBLE);
        RedL2.setVisibility(View.INVISIBLE);
        OrangeL.setVisibility(View.INVISIBLE);
        OrangeL2.setVisibility(View.INVISIBLE);
        yellowL.setVisibility(View.INVISIBLE);
        yellowL2.setVisibility(View.INVISIBLE);
        GreenL.setVisibility(View.VISIBLE);
        GreenL2.setVisibility(View.VISIBLE);
        QuotaLeftNm.setTextColor(Color.parseColor("#64DD17"));
    }

    public void Yellowlight() {
        RedL.setVisibility(View.INVISIBLE);
        RedL2.setVisibility(View.INVISIBLE);
        OrangeL.setVisibility(View.INVISIBLE);
        OrangeL2.setVisibility(View.INVISIBLE);
        GreenL.setVisibility(View.VISIBLE);
        GreenL2.setVisibility(View.VISIBLE);
        yellowL.setVisibility(View.VISIBLE);
        yellowL2.setVisibility(View.VISIBLE);
        QuotaLeftNm.setTextColor(Color.parseColor("#FFC107"));
    }

    public void OrangeLight() {
        GreenL.setVisibility(View.VISIBLE);
        GreenL2.setVisibility(View.VISIBLE);
        yellowL.setVisibility(View.VISIBLE);
        yellowL2.setVisibility(View.VISIBLE);
        RedL.setVisibility(View.INVISIBLE);
        RedL2.setVisibility(View.INVISIBLE);
        OrangeL.setVisibility(View.VISIBLE);
        OrangeL2.setVisibility(View.VISIBLE);
        QuotaLeftNm.setTextColor(Color.parseColor("#FF6D00"));
    }

    public void Redlight() {
        GreenL.setVisibility(View.VISIBLE);
        GreenL2.setVisibility(View.VISIBLE);
        yellowL.setVisibility(View.VISIBLE);
        yellowL2.setVisibility(View.VISIBLE);
        OrangeL.setVisibility(View.VISIBLE);
        OrangeL2.setVisibility(View.VISIBLE);
        RedL.setVisibility(View.VISIBLE);
        RedL2.setVisibility(View.VISIBLE);
        QuotaLeftNm.setTextColor(Color.parseColor("#D50000"));
    }

    public double GetQuota() {
        forQutaOC = new ForQuotas(getApplicationContext());
        Cursor qfinder = forQutaOC.JibData();
        if (qfinder.getCount() == 0) {
            MsgBox(getString(R.string.noquotafound));
            Quota = 0;
            hereisyourQuota.setText(String.valueOf(Quota));
            return Quota;
        } else {
            qfinder.moveToFirst();
            Quotafrom_database = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col22));
            GestQuotafrom_databse = qfinder.getDouble(qfinder.getColumnIndex(ForQuotas.col11));
            qfinder.close();
            if (Guestmode.isChecked()) {
                Quota = Quotafrom_database;
                hereisyourQuota.setText(String.valueOf(Quota));
                return Quota;
            } else {
                Quota = GestQuotafrom_databse;
                hereisyourQuota.setText(String.valueOf(Quota));
                return Quota;
            }
        }

    }

    public void showQuota() {
        hereisyourQuota.setText(String.valueOf(GetQuota()));
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
    public double getTotalAll() {
        /*NumberFormat mfr = new DecimalFormat("0.00");*/
        Cursor c = MDBC.GetSumall();
        if (c.getCount() == 0) {
            return SumAll = 0;
        } else {
            c.moveToFirst();
            SumAll = c.getDouble(0);
            //closing cursor so as not to bring anything else or ruin sth
            c.close();
        }
        return SumAll;
    }

    public void FillWithDays() {
        Sub1List.clear();
        Cursor datecurs = MDBC.getDates();
        if (datecurs.getCount() == 0) {
            return;
        } else {
            datecurs.moveToFirst();
            Sub1List.add("*");
            while (!datecurs.isAfterLast()) {
                String dts = datecurs.getString(datecurs.getColumnIndex(MyDataBaseCreator.da));
                Sub1List.add(dts);
                datecurs.moveToNext();
            }

            datecurs.close();
            SearchSpinerSub2.setAdapter(SearchSpinnerSub2Adapter);
        }
    }

    public void FillWithDaysforShop(String shop) {
        Sub2List.clear();
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor datecurs = db.rawQuery(" Select distinct " + MyDataBaseCreator.da + " from " + MyDataBaseCreator.TABLE_NAME + " where " + MyDataBaseCreator.person + " like '%" + shop + "%' order by " + MyDataBaseCreator.da + "", null);
        if (datecurs.getCount() == 0) {
            return;
        } else {
            datecurs.moveToFirst();
            Sub2List.add("*");
            while (!datecurs.isAfterLast()) {
                String dts = datecurs.getString(datecurs.getColumnIndex(MyDataBaseCreator.da));
                Sub2List.add(dts);
                datecurs.moveToNext();
            }
            datecurs.close();

            SearchSpinerSub3.setAdapter(SearchSpinnerSub3Adapter);
        }
    }

    public void FillWithDaysforShopEmpty() {
        Sub2List.clear();
        SearchSpinerSub3.setAdapter(SearchSpinnerSub3Adapter);
    }

    public void fillwithShopNm() {
        Sub1List.clear();

        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor molhanotCursor = db.rawQuery("select distinct " + MyDataBaseCreator.person + " from " + MyDataBaseCreator.TABLE_NAME + "  order by " + MyDataBaseCreator.da + " asc", null);
        if (molhanotCursor.getCount() == 0) {
            return;
        } else
            molhanotCursor.moveToFirst();
        Sub1List.add("*");
        while (!molhanotCursor.isAfterLast()) {
            String Mlhanot = molhanotCursor.getString(molhanotCursor.getColumnIndex(MyDataBaseCreator.person));
            Sub1List.add(Mlhanot);
            molhanotCursor.moveToNext();
        }
        molhanotCursor.close();
        SearchSpinerSub2.setAdapter(SearchSpinnerSub2Adapter);

    }

    public void FillWithItems() {
        Sub1List.clear();

        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor ItemCursor = db.rawQuery("select distinct " + MyDataBaseCreator.col1 + " from " + MyDataBaseCreator.TABLE_NAME + "  order by " + MyDataBaseCreator.da + " asc", null);
        if (ItemCursor.getCount() == 0) {
            return;
        } else
            ItemCursor.moveToFirst();
        while (!ItemCursor.isAfterLast()) {
            String Items = ItemCursor.getString(ItemCursor.getColumnIndex(MyDataBaseCreator.col1));
            Sub1List.add(Items);
            ItemCursor.moveToNext();
        }
        ItemCursor.close();
        SearchSpinerSub2.setAdapter(SearchSpinnerSub2Adapter);

    }
    public void FillWithItemsDates(String goods) {
        Sub2List.clear();

        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor ItemCursor = db.rawQuery("select  " + MyDataBaseCreator.da + " from " + MyDataBaseCreator.TABLE_NAME + " where " + MyDataBaseCreator.col1 + " like '%"+goods+"%'group by " + MyDataBaseCreator.col1 + "  order by " + MyDataBaseCreator.da + " desc ", null);
        if (ItemCursor.getCount() == 0) {
            return;
        } else
            ItemCursor.moveToFirst();
        Sub2List.add("*");
        while (!ItemCursor.isAfterLast()) {
            String Items = ItemCursor.getString(ItemCursor.getColumnIndex(MyDataBaseCreator.da));
            Sub2List.add(Items);
            ItemCursor.moveToNext();
        }
        ItemCursor.close();
        SearchSpinerSub3.setAdapter(SearchSpinnerSub3Adapter);

    }

    public double GetSumByShop(String mohamed) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + MyDataBaseCreator.col2 + ")as Solo from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.person + " like '%" + mohamed + "%' group by MoolHanout ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("Solo"));
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByDate(String ladat) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + MyDataBaseCreator.col2 + ")as So from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.da + " like '%" + ladat + "%' group by history ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("So"));
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByShopDate(String mohamed, String dat) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + MyDataBaseCreator.col2 + ")as Soa from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.person + " like '%" + mohamed + "%' and " + MyDataBaseCreator.da + " like '%" + dat + "%' group by history ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("Soa"));
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByItem(String ItemName) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + MyDataBaseCreator.col2 + ")as SumItems from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.col1 + " like '%"+ItemName+"%' group by " + MyDataBaseCreator.col1 + " order by " + MyDataBaseCreator.da + " desc ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("SumItems"));
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByItemsByDate(String item, String dat) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + MyDataBaseCreator.col2 + ")as Soa from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.col1 + " like '%"+item+"%' and " + MyDataBaseCreator.da + " like '%"+dat+"%' group by " + MyDataBaseCreator.da + "  ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("Soa"));
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();
        return sumtoday;
    }
    public boolean Backup() throws IOException {

        final String inFileName =Environment.getExternalStoragePublicDirectory( DIRECTORY_DOWNLOADS ) + "/"+"School.db";
        File dbFile = new File( inFileName );
        FileInputStream fis = new FileInputStream( dbFile );

        String outFileName = "/data/data/com.dev_bourheem.hadi/databases/School.db";
        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream( outFileName );

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read( buffer )) > 0) {
            output.write( buffer, 0, length );
        }
        // Close the streams
        output.flush();
        output.close();
        fis.close();
        return true;
    }
    public  boolean Restore() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory( DIRECTORY_DOWNLOADS );
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//com.dev_bourheem.hadi//databases//School.db";
                String backupDBPath = "School.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void PopupDialogue() {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(this);
        dialogue.setTitle("أضف السلعة");
        View view = getLayoutInflater().inflate(R.layout.additempopup, null);
        final AutoCompleteTextView molhanotNmIn = view.findViewById(R.id.molhanoutNameIn);
        final AutoCompleteTextView ItemNameIn = view.findViewById(R.id.ItemNameIn);
        final TextView quantityV = view.findViewById(R.id.quantityView);
        final Button addBut = view.findViewById(R.id.AddBtn);
        final Button Cancel = view.findViewById(R.id.Cancel);
        final EditText PriceIn = view.findViewById(R.id.ItemPriceIn);
        final Spinner quntifierSpinner = view.findViewById(R.id.QuanType);
        final Button plus = view.findViewById(R.id.plus);
        final Button minus = view.findViewById(R.id.minus);
        QuanSpinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, QTypes);
        QuanSpinAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        AutoCompleteAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, allList);
        MolhntautoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Molhanot);
        molhanotNmIn.setAdapter(MolhntautoCompleteAdapter);
        ItemNameIn.setAdapter(AutoCompleteAdapter);
        quntifierSpinner.setAdapter(QuanSpinAdapter);
        dialogue.setView(view);
        final AlertDialog alertDialog = dialogue.create();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Qnt1 = Qnt1 + 0.5;
                quantityV.setText(String.valueOf(Qnt1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Qnt1 > 0) Qnt1 = Qnt1 - 0.5;
                quantityV.setText(String.valueOf(Qnt1));
            }
        });

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PriceIn.getText().toString().trim().length() != 0 && ItemNameIn.getText().toString().trim().length() != 0 && molhanotNmIn.getText().toString().trim().length() != 0) {
                    String Quantifiier = quntifierSpinner.getSelectedItem().toString().trim();
                    double Qnt = Double.valueOf(quantityV.getText().toString().trim());
                    double ItemPriceDbl = Double.valueOf(PriceIn.getText().toString().trim());
                    ItemPriceDbl = Qnt * ItemPriceDbl;
                    String ItemNameStr = ItemNameIn.getText().toString().trim();
                    String Sir = molhanotNmIn.getText().toString().trim();
                    if (LoadDatabase(Qnt, Quantifiier, ItemNameStr, ItemPriceDbl, Sir)) {
                        GetItemNameFromdatabase();
                        GetmolhanotFromdatabase();
                        PriceIn.getText().clear();
                        ItemNameIn.getText().clear();
                        TraficLight(sumtoday);
                        fillwithShopNm();
                        alertDialog.cancel();
                    }
                } else MsgBox("المرجو ادخال المعلومات");
            }
        });


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

}
