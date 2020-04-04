package com.dev_bourheem.hadi.mainStuff;

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

import com.dev_bourheem.hadi.Archieve.ArchieveList;
import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.R;
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
    private Spinner CategorySpinner, SearchSpinerSub1, SearchSpinerSub2;
    private TextView DateV1, RedL, RedL2, yellowL, yellowL2, OrangeL, OrangeL2, GreenL, GreenL2, QuotaLeftNm, LeftOut, SumOutBy, TotalallOut, hereisyourQuota;
    private Switch Guestmode;
    private boolean ischecked;
    private double Quota = 0, sumtoday = 0, leftOfQuota = 0, Qnt1 = 1;
    private ArrayList<String> allList;
    private ArrayList<String> CategoryList;
    private ArrayList<String> SpinnerSub1List;
    private ArrayList<String> SpinnerSub2List;

    private ArrayList<String> Molhanot;
    private ArrayAdapter<String> MolhntautoCompleteAdapter, SearchSpinnerAdapter, SearchSpinnerSub2Adapter, SearchSpinnerSub3Adapter;
    private ArrayAdapter<String> AutoCompleteAdapter;
    private ArrayAdapter<String> QuanSpinAdapter;
    private MyDataBaseCreator MDBC;
    private AdView admain;
    private FloatingActionButton floatingButon;
    private String[] QTypes = {"واحدة", " كيلو", "لتر", "متر", "صندوق", "علبة"};
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
        CategorySpinner = findViewById(R.id.SumCategory);
        SearchSpinerSub1 = findViewById(R.id.Sumsearche);
        SearchSpinerSub2 = findViewById(R.id.SumsearchBydate);
        Molhanot = new ArrayList<>();
        allList = new ArrayList<String>();
        CategoryList = new ArrayList<>();
        SpinnerSub1List = new ArrayList<>();
        SpinnerSub2List = new ArrayList<>();
        MDBC = new MyDataBaseCreator(getApplicationContext());
        getTotalAllForAllShops();
        Molhanot.add(getString(R.string.unknown));
        GetItemNameFromdatabase();
        GetShopNamesFromdatabase();

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
        CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner1Fill();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SearchSpinerSub1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = CategorySpinner.getSelectedItem().toString().trim();
                String sellection2 = SearchSpinerSub1.getSelectedItem().toString().trim();
                TotalallOut.setText(String.valueOf(getTotalAllForAllShops()));
                switch (sellection1) {

                    case "حسب المحل":
                        FillWithDaysforShop(sellection2);
                        break;
                    case "حسب اليوم":
                        GetSumByDate(sellection2);
                        break;
                    case "حسب السلعة":
                        FillWithItemsDates(sellection2);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SearchSpinerSub2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = CategorySpinner.getSelectedItem().toString();
                String sellection2 = SearchSpinerSub1.getSelectedItem().toString();
                String sellection3 = SearchSpinerSub2.getSelectedItem().toString();

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
                            GetSumByItemsByDate(sellection2, sellection3);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fillcategory();
        TotalallOut.setText(String.valueOf(getTotalAllForAllShops()));
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

            case R.id.openArch:
                OpenArch();
                return true;
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
        CategoryList.clear();
        CategoryList.add("حسب المحل");
        CategoryList.add("حسب اليوم");
        CategoryList.add("حسب السلعة");
        SearchSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, CategoryList);
        CategorySpinner.setAdapter(SearchSpinnerAdapter);
    }

    public void Spinner1Fill() {
        String sellection1 = CategorySpinner.getSelectedItem().toString().trim();

        switch (sellection1) {
            case "حسب المحل":
                FillwithShopNm();

                break;
            case "حسب اليوم":
                FillWithDays();

                break;
            case "حسب السلعة":
                FillWithItems();

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

    public void OpenArch() {
        final Intent intent1;
        intent1 = new Intent(this, ArchieveList.class);
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

    public boolean LoadDatabase(double qntiti, String quqntifier, String itemNm, double itemPrix, String shopNm, String date) {
//
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectDataToMainTable(qntiti, quqntifier, itemNm, itemPrix, shopNm, date);
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
        double Quotafrom_database, GestQuotafrom_database;
        Cursor qfinder = MDBC.JibData();
        if (qfinder.getCount() == 0) {
            MsgBox(getString(R.string.noquotafound));
            Quota = 0;
            hereisyourQuota.setText(String.valueOf(Quota));
            return Quota;
        } else {
            qfinder.moveToFirst();
            Quotafrom_database = qfinder.getDouble(qfinder.getColumnIndex(DbContractor.TableColumns.userQuota));
            GestQuotafrom_database = qfinder.getDouble(qfinder.getColumnIndex(DbContractor.TableColumns.userGQuota));
            qfinder.close();
            if (Guestmode.isChecked()) {
                Quota = GestQuotafrom_database;
                hereisyourQuota.setText(String.valueOf(Quota));
                return Quota;
            } else {
                Quota = Quotafrom_database;
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
        allList = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MItem_Name, DbContractor.TableColumns.MItem_Name);
    }

    // this method gets shop names from database
    public void GetShopNamesFromdatabase() {
        Molhanot.clear();
        Molhanot = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MShopName, DbContractor.TableColumns.MShopName);
    }

    //this method gets the total of all product items from data base
    public double getTotalAllForAllShops() {
        /*NumberFormat mfr = new DecimalFormat("0.00");*/
       double allPaid= MDBC.GetPaidAmountForAllShop(DbContractor.TableColumns.PaymentTable, DbContractor.TableColumns.PaidAmount);
        Cursor c = MDBC.GetSumall();
        if (c.getCount() == 0) {
            return SumAll = 0;
        } else {
            c.moveToFirst();
            SumAll = c.getDouble(0);
            //closing cursor so as not to bring anything else or ruin sth
            c.close();
        }
        TotalallOut.setText(String.valueOf(SumAll-allPaid));
        return SumAll - allPaid;
    }

    /** these methods fill the second Spinner according to the firstSpinner */
    public void FillWithDays() {
        SpinnerSub1List.clear();
        SpinnerSub1List = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MDate, DbContractor.TableColumns.MDate);
        SpinnerSub1List.add("*");
        SearchSpinnerSub2Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub1List);
        SearchSpinerSub1.setAdapter(SearchSpinnerSub2Adapter);
    }
    public void FillwithShopNm() {
        SpinnerSub1List.clear();
        SpinnerSub1List = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MShopName, DbContractor.TableColumns.MShopName);
        SpinnerSub1List.add("*");
        SearchSpinnerSub2Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub1List);
        SearchSpinerSub1.setAdapter(SearchSpinnerSub2Adapter);
    }
    public void FillWithItems() {
        SpinnerSub1List.clear();
        SpinnerSub1List = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MItem_Name, DbContractor.TableColumns.MDate);
        SearchSpinnerSub2Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub1List);
        SearchSpinerSub1.setAdapter(SearchSpinnerSub2Adapter);
    }


    public void FillWithDaysforShop(String shop) {
        SpinnerSub2List.clear();

        SpinnerSub2List = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MDate, DbContractor.TableColumns.MDate);
        SearchSpinnerSub3Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub2List);
        SearchSpinerSub2.setAdapter(SearchSpinnerSub3Adapter);
    }
    public void FillWithDaysforShopEmpty() {
        SpinnerSub2List.clear();
        SearchSpinnerSub3Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub2List);
        SearchSpinerSub2.setAdapter(SearchSpinnerSub3Adapter);

    }
    public void FillWithItemsDates(String goods) {
        SpinnerSub2List.clear();

        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor ItemCursor = db.rawQuery("select  " + DbContractor.TableColumns.MDate + " from" +
                " " + DbContractor.TableColumns.MainTable + " where " + DbContractor.TableColumns.MItem_Name +
                " like '%" + goods + "%'group by " + DbContractor.TableColumns.MItem_Name +
                "  order by " + DbContractor.TableColumns.MDate + " desc ", null);
        if (ItemCursor.getCount() == 0) {
            return;
        } else
            ItemCursor.moveToFirst();
        SpinnerSub2List.add("*");
        while (!ItemCursor.isAfterLast()) {
            String Items = ItemCursor.getString(ItemCursor.getColumnIndex(DbContractor.TableColumns.MDate));
            SpinnerSub2List.add(Items);
            ItemCursor.moveToNext();
        }
        ItemCursor.close();
        SearchSpinnerSub3Adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, SpinnerSub2List);
        SearchSpinerSub2.setAdapter(SearchSpinnerSub3Adapter);

    }

    /** these are the methods for sums */
    public double GetSumByShop(String mohamed) {
      double paid=  MDBC.GetPaidAmountForShop( DbContractor.TableColumns.PaidAmount,DbContractor.TableColumns.PaymentTable,DbContractor.TableColumns.PaidShopName ,mohamed);
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + DbContractor.TableColumns.MItem_Price + ")as Soa from "
                + DbContractor.TableColumns.MainTable + " where  " + DbContractor.TableColumns.MShopName +
                " like '%" + mohamed + "%' order by " + DbContractor.TableColumns.MDate + " ", null);
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble(data.getColumnIndex("Soa"));
            sumtoday=sumtoday-paid;
            SumOutBy.setText((mfr.format(sumtoday)));
            TraficLight(sumtoday);
        }
        data.close();

        return sumtoday ;
    }
    public double GetSumByDate(String ladat) {
        NumberFormat mfr = new DecimalFormat("0.00");
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery("select Sum(" + DbContractor.TableColumns.MItem_Price + ")as So from "
                + DbContractor.TableColumns.MainTable + " where  " + DbContractor.TableColumns.MDate +
                " like '%" + ladat + "%' group by " + DbContractor.TableColumns.MDate + " ", null);
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
        Cursor data = db.rawQuery("select Sum(" + DbContractor.TableColumns.MItem_Price + ")as Soa from "
                + DbContractor.TableColumns.MainTable + " where  " + DbContractor.TableColumns.MShopName +
                " like '%" + mohamed + "%' and " + DbContractor.TableColumns.MDate + " like '%" + dat + "%' group by " + DbContractor.TableColumns.MDate + " ", null);
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
        Cursor data = db.rawQuery("select Sum(" + DbContractor.TableColumns.MItem_Price + ")as SumItems from "
                + DbContractor.TableColumns.MainTable + " where  " + DbContractor.TableColumns.MItem_Name +
                " like '%" + ItemName + "%' group by " + DbContractor.TableColumns.MItem_Name + " order by " + DbContractor.TableColumns.MDate + " desc ", null);
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
        Cursor data = db.rawQuery("select Sum(" + DbContractor.TableColumns.MItem_Price + ")as Soa from "
                + DbContractor.TableColumns.MainTable + " where  " + DbContractor.TableColumns.MItem_Name +
                " like '%" + item + "%' and " + DbContractor.TableColumns.MDate + " like '%" + dat + "%' group by "
                + DbContractor.TableColumns.MDate + "  ", null);
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
/************************************************************/
    public boolean Backup() throws IOException {

        final String inFileName = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + "School.db";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = "/data/data/com.dev_bourheem.hadi/databases/School.db";
        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        // Close the streams
        output.flush();
        output.close();
        fis.close();
        return true;
    }
    public boolean Restore() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
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
        View view = getLayoutInflater().inflate(R.layout.additempopup, null);
        final AutoCompleteTextView molhanotNmIn = view.findViewById(R.id.molhanoutNameIn);
        final AutoCompleteTextView ItemNameIn = view.findViewById(R.id.ItemNameIn);
        final TextView quantityV = view.findViewById(R.id.quantityView);
        final Button addBut = view.findViewById(R.id.AddBtn);
        final Button Cancel = view.findViewById(R.id.Cancel);
        final EditText PriceIn = view.findViewById(R.id.ItemPriceIn);
        final EditText DateIN = view.findViewById(R.id.DateIn);
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
        DateIN.setText(GetDate());
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
                    double Qnt = Double.parseDouble(quantityV.getText().toString().trim());
                    double ItemPriceDbl = Double.parseDouble(PriceIn.getText().toString().trim());
                    String dateIn= DateIN.getText().toString().trim();
                    ItemPriceDbl = Qnt * ItemPriceDbl;
                    String ItemNameStr = ItemNameIn.getText().toString().trim();
                    String Sir = molhanotNmIn.getText().toString().trim();
                    if (LoadDatabase(Qnt, Quantifiier, ItemNameStr, ItemPriceDbl, Sir,dateIn)) {
                        GetItemNameFromdatabase();
                        GetShopNamesFromdatabase();
                        PriceIn.getText().clear();
                        ItemNameIn.getText().clear();
                        TraficLight(sumtoday);
                        FillwithShopNm();
                        //alertDialog.cancel();
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

    public boolean isFirstDayOfMonth(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("Calendar cannot be null.");
        }
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth == 1;
    }

    public void ArchiveIt() {
        if (isFirstDayOfMonth(Calendar.getInstance())) {
            //     ArchiveIt();
            SQLiteDatabase db = MDBC.getWritableDatabase();
            db.execSQL("insert into " + DbContractor.TableColumns.ArchiveTable + " select * from " + DbContractor.TableColumns.MainTable +
                    "  order by " + DbContractor.TableColumns.MDate);
        }

    }
}
