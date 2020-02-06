package com.dev_bourheem.hadi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    public Spinner qtypSpinner, Sumcategoryspinner, SumsearchSpinner, SumsearchBydate;
    AutoCompleteTextView NameIn, moolhanotNm;
    public EditText PriceIn, quantity;
    public TextView DateV1, RedL, RedL2, yellowL, yellowL2, OrangeL, OrangeL2, GreenL, GreenL2, QuotaLeftNm, LeftOut, SumOutBy, TotalallOut, hereisyourQuota;
    public Switch Guestmode;
    public boolean ischecked;
    public double LeftOfQuota, ItemPriceDbl, Quotafrom_database, GestQuotafrom_databse;
    public String ItemNameStr, Sir;
    public double Quota = 0, Qnt, sumtoday = 0, leftOfQuota = 0, halfQuota;
    ArrayList<String> allList, sumcategoryList, BydateList, sumsearchList;
    ArrayList<String> Molhanot;
    ArrayAdapter<String> MolhntautoCompleteAdapter, sumcategoryAdapter, sumsearchAdapter, SumsearchBydateAdapter;
    ArrayAdapter<String> AutoCompleteAdapter, QuanSpinAdapter;
    MyDataBaseCreator MDBC;
    ForQuotas forQutaOC;
    AdView admain;
    String[] QTypes = {"واحدة", " كيلو", "لتر", "متر", "صندوق", "علبة"};
    private double SumAll = 0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.exmenu, menu );
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
                return true;
            case R.id.exit_M:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButonsDeclare();
        sumcategoryList = new ArrayList<>();
        sumsearchList = new ArrayList<>();
        BydateList = new ArrayList<>();
        MDBC = new MyDataBaseCreator( getApplicationContext() );
        forQutaOC = new ForQuotas( getApplicationContext() );
        fillsugest();
        sumcategoryAdapter = new ArrayAdapter<>( this, R.layout.support_simple_spinner_dropdown_item, sumcategoryList );
        sumsearchAdapter = new ArrayAdapter<>( this, R.layout.support_simple_spinner_dropdown_item, sumsearchList );
        SumsearchBydateAdapter = new ArrayAdapter<>( this, R.layout.support_simple_spinner_dropdown_item, BydateList );
        plus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qnt = Double.valueOf( quantity.getText().toString().trim() );
                Qnt = Qnt + 0.5;
                quantity.setText( String.valueOf( Qnt ) );
            }
        } );
        minus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Qnt = Double.valueOf( quantity.getText().toString().trim() );
                if (Qnt > 0) Qnt = Qnt - 0.5;
                quantity.setText( String.valueOf( Qnt ) );
            }
        } );

        DateV1.setText( GetDate() );
        DateV1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseCreator.backUp();
            }
        } );
        addBut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (PriceIn.getText().toString().trim().length() != 0 && NameIn.getText().toString().trim().length() != 0) {
                    LoadDatabase();
                    GetItemNameFromdatabase();
                    GetmolhanotFromdatabase();
                    PriceIn.getText().clear();
                    NameIn.getText().clear();
                    fillsugest();
                } else MsgBox( "المرجو ادخال المعلومات" );
            }
        } );

        Guestmode.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ischecked = Guestmode.isChecked();
                if (ischecked) {
                    MsgBox( "وضع (الضيوف )" );
                    showQuota();
                } else {
                    MsgBox( "الوضع العائلي" );
                    showQuota();
                }
            }
        } );
        Sumcategoryspinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DbisEmpty()) {
                    String sellection1 = Sumcategoryspinner.getSelectedItem().toString().trim();

                    switch (sellection1) {
                        case "حسب المحل":
                            fillwithShopNm();

                            break;
                        case "حسب اليوم":
                            FillWithDays();
                            FillWithDaysforShopEmpty();
                            break;

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        SumsearchSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DbisEmpty()) {
                    String sellection1 = Sumcategoryspinner.getSelectedItem().toString().trim();
                    String sellection2 = SumsearchSpinner.getSelectedItem().toString().trim();

                    switch (sellection1) {

                        case "حسب المحل":
                            FillWithDaysforShop( sellection2 );
                            break;
                        case "حسب اليوم":
                            GetSumByDate( sellection2 );
                            //FillWithDaysforShopEmpty();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        SumsearchBydate.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (DbisEmpty()) {
                    String sellection1 = Sumcategoryspinner.getSelectedItem().toString().trim();
                    String sellection2 = SumsearchSpinner.getSelectedItem().toString().trim();
                    String sellection3 = SumsearchBydate.getSelectedItem().toString().trim();


                    switch (sellection1) {
                        case "حسب المحل":
                            if (sellection3.equals( "*" )) {
                                GetSumByShop( sellection2 );
                            } else
                                GetSumByShopDate( sellection2, sellection3 );
                            break;
                        case "حسب اليوم":
                            FillWithDaysforShopEmpty();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        fillcategory();
        TotalallOut.setText( String.valueOf( getTotalAll() ) );


        //ADSmainActivity();
    }

    public void ADSmainActivity() {
        MobileAds.initialize( this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );

        AdRequest adRequest = new AdRequest.Builder().build();
        admain.loadAd( adRequest );
    }

    public void fillcategory() {
        sumcategoryList.add( "حسب المحل" );
        sumcategoryList.add( "حسب اليوم" );
        Sumcategoryspinner.setAdapter( sumcategoryAdapter );
    }

    public void ButonsDeclare() {
        DateV1 = findViewById( R.id.dateView );
        QuotaLeftNm = findViewById( R.id.QuotaLeftNm );
        GreenL = findViewById( R.id.BtnGreen );
        GreenL2 = findViewById( R.id.BtnGreen2 );
        yellowL = findViewById( R.id.BtnYellow );
        yellowL2 = findViewById( R.id.BtnYellow2 );
        OrangeL = findViewById( R.id.BtnOrange );
        OrangeL2 = findViewById( R.id.BtnOrange2 );
        RedL = findViewById( R.id.BtnRed );
        RedL2 = findViewById( R.id.BtnRed2 );
        TotalallOut = findViewById( R.id.TotalallOut );
        Guestmode = findViewById( R.id.SwitchGuest );
        hereisyourQuota = findViewById( R.id.hereisurqt );
        plus = findViewById( R.id.plus );
        addBut = findViewById( R.id.AddBtn );
        minus = findViewById( R.id.minus );
        qtypSpinner = findViewById( R.id.QuanType );
        NameIn = findViewById( R.id.ItemNameIn );
        moolhanotNm = findViewById( R.id.molhanoutNameIn );
        PriceIn = findViewById( R.id.ItemPriceIn );
        LeftOut = findViewById( R.id.QuotaLeftOut );
        SumOutBy = findViewById( R.id.TotalTodayOut );
        quantity = findViewById( R.id.quantity );
        admain = findViewById( R.id.admain );
        Sumcategoryspinner = findViewById( R.id.SumCategory );
        SumsearchSpinner = findViewById( R.id.Sumsearche );
        SumsearchBydate = findViewById( R.id.SumsearchBydate );

    }

    // this opens list activity
    public void OpentAvtivity2() {
        final Intent intent1;
        intent1 = new Intent( this, ListActivity.class );
        //intent1.putExtra("tarikh" ,date);
        startActivity( intent1 );
    }

    /**
     * this method fills the autocomplete Edittextview
     */
    public void fillsugest() {
        Molhanot = new ArrayList<>();
        allList = new ArrayList<String>();
        QuanSpinAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, QTypes );
        QuanSpinAdapter.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item );
        qtypSpinner.setAdapter( QuanSpinAdapter );
        Molhanot.add( getString( R.string.unknown ) );
        GetItemNameFromdatabase();
        AutoCompleteAdapter = new ArrayAdapter<>( this, R.layout.support_simple_spinner_dropdown_item, allList );
        NameIn.setAdapter( AutoCompleteAdapter );
        GetmolhanotFromdatabase();
        MolhntautoCompleteAdapter = new ArrayAdapter<>( this, android.R.layout.simple_dropdown_item_1line, Molhanot );
        moolhanotNm.setAdapter( MolhntautoCompleteAdapter );
    }

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent( this, Settings.class );
        startActivity( intent2 );
    }

    public void OpenStats() {
        final Intent intent2;
        intent2 = new Intent( this, stats.class );
        startActivity( intent2 );
    }

    public String GetDate() {
        Date currenttime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat( "YYYY-MM-dd" );
        return dateFormat.format( currenttime );
    }

    // this method calculates the limit (Quota ) according to the switch and according to the user settings


    public void MsgBox(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
    }

    public void LoadDatabase() {
//
        String Quantifiier = qtypSpinner.getSelectedItem().toString().trim();
        Qnt = Double.valueOf( quantity.getText().toString().trim() );
        String d = GetDate();
        ItemPriceDbl = Double.parseDouble( PriceIn.getText().toString().trim() );
        ItemPriceDbl = Qnt * ItemPriceDbl;
        ItemNameStr = NameIn.getText().toString().trim();
        Sir = moolhanotNm.getText().toString().trim();
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectData( Qnt, Quantifiier, ItemNameStr, ItemPriceDbl, Sir, d );
        if (newRowAdded) {
            MsgBox( "تم" );
        } else MsgBox( "لم يتم" );
    }


    // this method controlls Traffic Light and the text with it
    public void TraficLight(Double sum2day) {
        leftOfQuota = Quota - sum2day;
        LeftOut.setText( String.valueOf( leftOfQuota ) );
        double SemiQuota = GetQuota() / 2;
        switch (Quota) {
            case (sum2day <= SemiQuota):

                RedL.setVisibility( View.INVISIBLE );
                OrangeL.setVisibility( View.INVISIBLE );
                GreenL.setVisibility( View.VISIBLE );
                QuotaLeftNm.setTextColor( Color.parseColor( "#64DD17" ) );

                GreenL.setVisibility( View.INVISIBLE );
                OrangeL.setVisibility( View.VISIBLE );
                QuotaLeftNm.setTextColor( Color.parseColor( "#FF6D00" ) );

                GreenL.setVisibility( View.INVISIBLE );
                OrangeL.setVisibility( View.INVISIBLE );
                RedL.setVisibility( View.VISIBLE );
                QuotaLeftNm.setTextColor( Color.parseColor( "#D50000" ) );
        }
    }

    public double GetQuota() {
        forQutaOC = new ForQuotas( getApplicationContext() );
        Cursor qfinder = forQutaOC.JibData();
        if (qfinder.getCount() == 0) {
            MsgBox( getString( R.string.noquotafound ) );
            Quota = 0;
            hereisyourQuota.setText( String.valueOf( Quota ) );
            return Quota;
        } else {
            qfinder.moveToFirst();
            Quotafrom_database = qfinder.getDouble( qfinder.getColumnIndex( ForQuotas.col22 ) );
            GestQuotafrom_databse = qfinder.getDouble( qfinder.getColumnIndex( ForQuotas.col11 ) );
            qfinder.close();
            if (Guestmode.isChecked()) {
                Quota = Quotafrom_database;
                hereisyourQuota.setText( String.valueOf( Quota ) );
                return Quota;
            } else {
                Quota = GestQuotafrom_databse;
                hereisyourQuota.setText( String.valueOf( Quota ) );
                return Quota;
            }
        }

    }

    public void showQuota() {
        hereisyourQuota.setText( String.valueOf( GetQuota() ) );
    }

    // this method gets product names from database
    public void GetItemNameFromdatabase() {
        allList.clear();
        MDBC = new MyDataBaseCreator( this );
        Cursor itemNameCursor = MDBC.GetItemNames();

        if (itemNameCursor.getCount() == 0) MsgBox( getString( R.string.noitemnamefound ) );
        else {
            while (itemNameCursor.moveToNext()) {
                allList.add( itemNameCursor.getString( itemNameCursor.getColumnIndex( MyDataBaseCreator.col1 ) ) );

            }
        }
    }

    // this method gets shop name from database
    public void GetmolhanotFromdatabase() {
        Molhanot.clear();
        MDBC = new MyDataBaseCreator( this );
        Cursor itemNameCursor = MDBC.GetMolhanot();

        if (itemNameCursor.getCount() == 0) {
            MsgBox( getString( R.string.noshopname ) );

        } else {
            while (itemNameCursor.moveToNext()) {
                Molhanot.add( itemNameCursor.getString( itemNameCursor.getColumnIndex( MyDataBaseCreator.person ) ) );

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
            SumAll = c.getDouble( 0 );
            //closing cursor so as not to bring anything else or ruin sth
            c.close();
        }
        return SumAll;
    }

    public void FillWithDays() {
        sumsearchList.clear();
        Cursor datecurs = MDBC.getDates();
        if (datecurs.getCount() == 0) {
            return;
        } else {
            datecurs.moveToFirst();
            while (!datecurs.isAfterLast()) {
                String dts = datecurs.getString( datecurs.getColumnIndex( MyDataBaseCreator.da ) );
                sumsearchList.add( dts );
                datecurs.moveToNext();
            }
            sumsearchList.add( "*" );
            datecurs.close();
            SumsearchSpinner.setAdapter( sumsearchAdapter );
        }
    }

    public void FillWithDaysforShop(String shop) {
        BydateList.clear();
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor datecurs = db.rawQuery( " Select distinct " + MyDataBaseCreator.da + " from " + MyDataBaseCreator.TABLE_NAME + " where " + MyDataBaseCreator.person + " like '%" + shop + "%' order by " + MyDataBaseCreator.da + "", null );
        if (datecurs.getCount() == 0) {
            return;
        } else {
            datecurs.moveToFirst();
            while (!datecurs.isAfterLast()) {
                String dts = datecurs.getString( datecurs.getColumnIndex( MyDataBaseCreator.da ) );
                BydateList.add( dts );
                datecurs.moveToNext();
            }
            datecurs.close();
            BydateList.add( "*" );
            SumsearchBydate.setAdapter( SumsearchBydateAdapter );
        }
    }

    public void FillWithDaysforShopEmpty() {
        BydateList.clear();
        BydateList.add( "*****" );
        SumsearchBydate.setAdapter( SumsearchBydateAdapter );
    }

    public void fillwithShopNm() {
        sumsearchList.clear();
        sumsearchList.add( "*" );
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor molhanotCursor = db.rawQuery( "select distinct " + MyDataBaseCreator.person + " from " + MyDataBaseCreator.TABLE_NAME + "  order by " + MyDataBaseCreator.da + " asc", null );
        if (molhanotCursor.getCount() == 0) {
            return;
        } else
            molhanotCursor.moveToFirst();
        while (!molhanotCursor.isAfterLast()) {
            String Mlhanot = molhanotCursor.getString( molhanotCursor.getColumnIndex( MyDataBaseCreator.person ) );
            sumsearchList.add( Mlhanot );
            molhanotCursor.moveToNext();
        }
        molhanotCursor.close();
        SumsearchSpinner.setAdapter( sumsearchAdapter );

    }

    public double GetSumByShop(String mohamed) {
        NumberFormat mfr = new DecimalFormat( "0.00" );
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery( "select Sum(" + MyDataBaseCreator.col2 + ")as Solo from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.person + " like '%" + mohamed + "%' group by MoolHanout ", null );
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble( data.getColumnIndex( "Solo" ) );
            SumOutBy.setText( (mfr.format( sumtoday )) );
            TraficLight( sumtoday );
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByDate(String mohamed) {
        NumberFormat mfr = new DecimalFormat( "0.00" );
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery( "select Sum(" + MyDataBaseCreator.col2 + ")as So from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.da + " like '%" + mohamed + "%' group by history ", null );
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble( data.getColumnIndex( "So" ) );
            SumOutBy.setText( (mfr.format( sumtoday )) );
            TraficLight( sumtoday );
        }
        data.close();
        return sumtoday;
    }

    public double GetSumByShopDate(String mohamed, String dat) {
        NumberFormat mfr = new DecimalFormat( "0.00" );
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor data = db.rawQuery( "select Sum(" + MyDataBaseCreator.col2 + ")as Soa from " + MyDataBaseCreator.TABLE_NAME + " where  " + MyDataBaseCreator.person + " like '%" + mohamed + "%' and " + MyDataBaseCreator.da + " like '%" + dat + "%' group by history ", null );
        if (data.getCount() == 0) {

        } else if (!data.isAfterLast()) {

            data.moveToFirst();
            sumtoday = data.getDouble( data.getColumnIndex( "Soa" ) );
            SumOutBy.setText( (mfr.format( sumtoday )) );
            TraficLight( sumtoday );
        }
        data.close();
        return sumtoday;
    }

    public boolean DbisEmpty() {
        SQLiteDatabase db = MDBC.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from " + MyDataBaseCreator.TABLE_NAME + "", null );
        if (cursor.getCount() == 0) {
            return false;
        }
        cursor.close();
        return true;
    }


}

