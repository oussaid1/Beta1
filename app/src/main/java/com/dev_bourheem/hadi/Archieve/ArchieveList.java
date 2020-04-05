package com.dev_bourheem.hadi.Archieve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dev_bourheem.hadi.ArchivePayment.ArchPaymentList;
import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.mainStuff.EdditActivity;
import com.dev_bourheem.hadi.mainStuff.MainActivity;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.R;
import com.dev_bourheem.hadi.mainStuff.Settings;
import com.dev_bourheem.hadi.mainStuff.stats;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class ArchieveList extends AppCompatActivity {

    MyDataBaseCreator MdbCrtr;

    private Spinner categoryspinner, searchspinner;
    private ArrayList<String> mainList;
    private ArrayList<String> categoryspinnerList, searchspinerList;
    private ArrayAdapter<String> categoryAdapter, searchAdapter;


    private ArrayList<ArchexampleItem> ArchmExampleList;
    private RecyclerView Archmyrecycler;
    private RecyclerView.LayoutManager ArchRecyLayManger;
    private ArchRecyclerViewAdapter ArchRecyclerAdap;

    AdView listAd;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.archmenu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mainArch:
                OpentMainActivity();
                return true;
            case R.id.statsArch:
                OpentStats();
                return true;
            case R.id.ArchPayment:
                OpentArchPayment();
                return true;
            case R.id.SettingsArch:
                OpentSettings();
                return true;
            case R.id.exitArch:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_archieve_list );
        Archmyrecycler = findViewById( R.id.myrecyclerArch );
        listAd = findViewById( R.id.listAdArch );
        Archmyrecycler.setHasFixedSize( true );
        categoryspinner = findViewById( R.id.CategorySpinArch );
        searchspinner = findViewById( R.id.searchSpinArch );
        ArchmExampleList = new ArrayList<>();
        mainList = new ArrayList<>();
        MdbCrtr = new MyDataBaseCreator( this );
        categoryspinnerList = new ArrayList<>();
        addtoCategoryList();
        searchspinerList = new ArrayList<>();
        categoryAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, categoryspinnerList );
        categoryspinner.setAdapter( categoryAdapter );
        categoryspinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = categoryspinner.getSelectedItem().toString().trim();

                switch (sellection1) {
                    case "الكل":
                        fillwithStar();
                        break;
                    case "حسب المحل":
                        FillWithMolhanot();
                        break;
                    case "حسب التاريخ":
                        FillWithDates();
                        break;
                    case "حسب السلعة":
                        FillWithItems();
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        searchAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, searchspinerList );
        searchspinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = categoryspinner.getSelectedItem().toString().trim();
                String selection2 = searchspinner.getSelectedItem().toString().trim();
                switch (sellection1) {
                    case "الكل":
                        ShowlistAll();
                        break;
                    case "حسب المحل":
                        ShowlistByMolhanot( selection2 );
                        break;
                    case "حسب التاريخ":
                        ShowlistByDate( selection2 );
                        break;
                    case "حسب السلعة":
                        ShowlistByItem( selection2 );
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        ArchRecyLayManger = new LinearLayoutManager( this );
        ArchRecyclerAdap = new ArchRecyclerViewAdapter( ArchmExampleList );
        ArchRecyclerAdap.setOnItemClickListener( new ArchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                Intent intent = new Intent( ArchieveList.this, ArchEddit.class );
                intent.putExtra( "ArchExampleItem", ArchmExampleList.get( position ) );
                startActivity( intent );

            }

            @Override
            public void OnItemDelete(int position) {

            }
        } );
        Adslist();
    }

    private void Adslist() {
        MobileAds.initialize( this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );

        AdRequest adRequest = new AdRequest.Builder().build();
        listAd.loadAd( adRequest );
    }

    private void OpentMainActivity() {
        final Intent intent2;
        intent2 = new Intent( this, MainActivity.class );
        startActivity( intent2 );
    }

    private void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent( this, Settings.class );
        startActivity( intent2 );
    }

    private void PrintMessage(String title, String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder( this );
        newAlert.setCancelable( true );
        newAlert.setTitle( title );
        newAlert.setMessage( message );
        newAlert.show();
    }

    private void ShowlistByMolhanot(String mohamed) {
        ArchmExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.ArchiveTable + " where " +
                " " + DbContractor.TableColumns.ArShopName + " like '%" + mohamed + "%' order by history asc", null );

        if (data.getCount() == 0) {
            PrintMessage( getString( R.string.alert ), getString( R.string.nodataintable ) );
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString( data.getColumnIndex( DbContractor.TableColumns._ID ) );
                String quantity = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantity ) );
                String qunatifier = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantifier ) );
                String itemNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Name ) );
                String itemName = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Price ) );
                String shopNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArShopName ) );
                String DateBout = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArDate ) );
                ArchmExampleList.add( new ArchexampleItem( idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout ) );

            } while ((data.moveToNext()));

        data.close();
        Archmyrecycler.setLayoutManager( ArchRecyLayManger );
        Archmyrecycler.setAdapter( ArchRecyclerAdap );

    }

    private void ShowlistByDate(String datatata) {
        ArchmExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.ArchiveTable +
                " where  " + DbContractor.TableColumns.ArDate + " like '%" + datatata + "%' order by history asc", null );

        if (data.getCount() == 0) {
            PrintMessage( getString( R.string.alert ), getString( R.string.nodataintable ) );
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString( data.getColumnIndex( DbContractor.TableColumns._ID ) );
                String quantity = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantity ) );
                String qunatifier = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantifier ) );
                String itemNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Name ) );
                String itemName = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Price ) );
                String shopNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArShopName ) );
                String DateBout = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArDate ) );
                ArchmExampleList.add( new ArchexampleItem( idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout ) );

            } while ((data.moveToNext()));

        data.close();
        Archmyrecycler.setLayoutManager( ArchRecyLayManger );
        Archmyrecycler.setAdapter( ArchRecyclerAdap );

    }

    private void ShowlistByItem(String goods) {
        ArchmExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.ArchiveTable +
                " where  " + DbContractor.TableColumns.ArItem_Name + " like '%" + goods + "%' order by history asc", null );
        if (data.getCount() == 0) {
            PrintMessage( getString( R.string.alert ), getString( R.string.nodataintable ) );
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString( data.getColumnIndex( DbContractor.TableColumns._ID ) );
                String quantity = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantity ) );
                String qunatifier = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantifier ) );
                String itemNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Name ) );
                String itemName = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Price ) );
                String shopNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArShopName ) );
                String DateBout = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArDate ) );
                ArchmExampleList.add( new ArchexampleItem( idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout ) );

            } while ((data.moveToNext()));

        data.close();
        Archmyrecycler.setLayoutManager( ArchRecyLayManger );
        Archmyrecycler.setAdapter( ArchRecyclerAdap );

    }

    private void ShowlistAll() {
        ArchmExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.ArchiveTable +
                " order by history asc", null );
        if (data.getCount() == 0) {
            PrintMessage( getString( R.string.alert ), getString( R.string.nodataintable ) );
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString( data.getColumnIndex( DbContractor.TableColumns._ID ) );
                String quantity = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantity ) );
                String qunatifier = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArQuantifier ) );
                String itemNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Name ) );
                String itemName = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArItem_Price ) );
                String shopNm = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArShopName ) );
                String DateBout = data.getString( data.getColumnIndex( DbContractor.TableColumns.ArDate ) );
                ArchmExampleList.add( new ArchexampleItem( idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout ) );

            } while ((data.moveToNext()));

        data.close();
        Archmyrecycler.setLayoutManager( ArchRecyLayManger );
        Archmyrecycler.setAdapter( ArchRecyclerAdap );

    }

    private void OpentStats() {
        Intent intent = new Intent( this, ArchievStats.class );
        startActivity( intent );
    }
    private void OpentArchPayment(){
        Intent intent = new Intent( this, ArchPaymentList.class );
        startActivity( intent );
    }

    private void FillWithDates() {
        searchspinerList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor datescursor = db.rawQuery( " Select distinct " + DbContractor.TableColumns.ArDate + " from "
                + DbContractor.TableColumns.ArchiveTable + " order by " +
                "" + DbContractor.TableColumns.ArDate + " asc ", null );

        if (datescursor.getCount() == 0) {
            return;
        } else {
            datescursor.moveToFirst();
            while (!datescursor.isAfterLast()) {
                String dts = datescursor.getString( datescursor.getColumnIndex( DbContractor.TableColumns.ArDate ) );
                searchspinerList.add( dts );
                datescursor.moveToNext();
            }
        }
        datescursor.close();
        searchspinner.setAdapter( searchAdapter );
    }

    private void FillWithMolhanot() {
        searchspinerList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor molhanotCursor = db.rawQuery( "select distinct " + DbContractor.TableColumns.ArShopName +
                " from " + DbContractor.TableColumns.ArchiveTable + " order by " +
                "" + DbContractor.TableColumns.ArDate + " asc", null );
        if (molhanotCursor.getCount() == 0) {
            return;
        } else
            molhanotCursor.moveToFirst();
        while (!molhanotCursor.isAfterLast()) {
            String Mlhanot = molhanotCursor.getString( molhanotCursor.getColumnIndex( DbContractor.TableColumns.ArShopName ) );
            searchspinerList.add( Mlhanot );
            molhanotCursor.moveToNext();
        }
        molhanotCursor.close();
        searchspinner.setAdapter( searchAdapter );
        //db.close();
    }

    public void FillWithItems() {
        searchspinerList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor itemcursor = db.rawQuery( "select distinct " + DbContractor.TableColumns.ArItem_Name +
                " from " + DbContractor.TableColumns.ArchiveTable + " order by " +
                "" + DbContractor.TableColumns.ArDate + " asc", null );
        if (itemcursor.getCount() == 0) {
            return;
        } else
            itemcursor.moveToFirst();
        while (!itemcursor.isAfterLast()) {
            String dts = itemcursor.getString( itemcursor.getColumnIndex( DbContractor.TableColumns.ArItem_Name ) );
            searchspinerList.add( dts );
            itemcursor.moveToNext();
        }
        itemcursor.close();
        searchspinner.setAdapter( searchAdapter );
    }


    private void fillwithStar() {
        searchspinerList.clear();
        searchspinerList.add( "*" );
        searchspinner.setAdapter( searchAdapter );
    }

    private void addtoCategoryList() {
        categoryspinnerList.add( "الكل" );
        categoryspinnerList.add( "حسب المحل" );
        categoryspinnerList.add( "حسب التاريخ" );
        categoryspinnerList.add( "حسب السلعة" );


    }

}