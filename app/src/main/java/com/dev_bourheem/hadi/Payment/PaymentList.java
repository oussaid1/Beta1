package com.dev_bourheem.hadi.Payment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class PaymentList extends AppCompatActivity {

    MyDataBaseCreator MdbCrtr;
    private Spinner categoryspinner, searchspinner;
    private ArrayList<String> mainList;
    private ArrayList<String> categoryspinnerList, searchspinerList;
    private ArrayAdapter<String> categoryAdapter, searchAdapter;
    private ArrayList<PaymentItemsClass> PaymentItemsClassList;
    private RecyclerView Archmyrecycler;
    private RecyclerView.LayoutManager ArchPRecyLayManger;
    private PaymentRecyclerAdapter ArchPRecyclerAdap;
    private AdView listAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payment_list );
        Archmyrecycler = findViewById( R.id.PayPmyrecycler );
        listAd = findViewById( R.id.PayPlistAd );
        Archmyrecycler.setHasFixedSize( true );
        categoryspinner = findViewById( R.id.PayPCategorySpin );
        searchspinner = findViewById( R.id.PayPsearchSpin );
        PaymentItemsClassList = new ArrayList<>();
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
                        GetAllPaymentHistoryForAll();
                        break;
                    case "حسب المحل":
                        FillWithMolhanot();
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

                    case "حسب المحل":
                        GetAllPaymentHistoryForShopNm(selection2);
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        ArchPRecyLayManger = new LinearLayoutManager( this );
        ArchPRecyclerAdap = new PaymentRecyclerAdapter( PaymentItemsClassList );
        ArchPRecyclerAdap.setOnItemClickListener( new PaymentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent( PaymentList.this, PaymentEddit.class );
                intent.putExtra( "PaymentEdit", PaymentItemsClassList.get( position ) );
                startActivity( intent );
            }

            @Override
            public void OnItemDelete(int position) {

            }
        } );
        AdsPaymentlist();
    }
    private void AdsPaymentlist() {
        MobileAds.initialize( this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );

        AdRequest adRequest = new AdRequest.Builder().build();
        listAd.loadAd( adRequest );
    }
    private void PrintMessage(String title, String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder( this );
        newAlert.setCancelable( true );
        newAlert.setTitle( title );
        newAlert.setMessage( message );
        newAlert.show();
    }
    private void GetAllPaymentHistoryForAll() {
        PaymentItemsClassList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.PaymentTable +
                " order by "+DbContractor.TableColumns.PaymentDate+" asc", null );
        if (data.getCount() != 0) {
            data.moveToFirst();
            while (!data.isAfterLast()) {

                String id = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String shopName = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaidShopName));
                String paidAmount = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaidAmount));
                String paidDate = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaymentDate));
                PaymentItemsClassList.add(new PaymentItemsClass(id, shopName, paidAmount, paidDate));

                data.moveToNext();
            }
            data.close();
            Archmyrecycler.setLayoutManager(ArchPRecyLayManger);
            Archmyrecycler.setAdapter(ArchPRecyclerAdap);
        }
    }
    private void GetAllPaymentHistoryForShopNm(String ShopName) {
        PaymentItemsClassList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery( "select * from " + DbContractor.TableColumns.PaymentTable + "" +
                " where " + DbContractor.TableColumns.PaidShopName + " like '%" + ShopName + "%'" +
                "  order by "+DbContractor.TableColumns.PaymentDate+" asc", null );
        if (data.getCount() != 0) {
            data.moveToFirst();
            while (!data.isAfterLast()) {

                String id = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String shopName = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaidShopName));
                String paidAmount = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaidAmount));
                String paidDate = data.getString(data.getColumnIndex(DbContractor.TableColumns.PaymentDate));
                PaymentItemsClassList.add(new PaymentItemsClass(id, shopName, paidAmount, paidDate));

                data.moveToNext();
            }
            data.close();
            Archmyrecycler.setLayoutManager(ArchPRecyLayManger);
            Archmyrecycler.setAdapter(ArchPRecyclerAdap);
        }
    }
    private void FillWithMolhanot() {
        searchspinerList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor molhanotCursor = db.rawQuery( "select distinct " + DbContractor.TableColumns.PaidShopName +
                " from " + DbContractor.TableColumns.PaymentTable + " order by " +
                "" + DbContractor.TableColumns.PaymentDate + " asc", null );
        if (molhanotCursor.getCount() == 0) {
            return;
        } else
            molhanotCursor.moveToFirst();
        while (!molhanotCursor.isAfterLast()) {
            String Mlhanot = molhanotCursor.getString( molhanotCursor.getColumnIndex( DbContractor.TableColumns.PaidShopName ) );
            searchspinerList.add( Mlhanot );
            molhanotCursor.moveToNext();
        }
        molhanotCursor.close();
        searchspinner.setAdapter( searchAdapter );
        //db.close();
    }
    /*private void FillWithMolhanotDates(String ladat) {
        searchspinerList.clear();
        searchspinerList.add( "*" );
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor molhanotCursor = db.rawQuery( "select distinct " + DbContractor.TableColumns.ArPaymentDate +" from " +
                "" + DbContractor.TableColumns.ArchivePaymentTable + "" +
                " where " + DbContractor.TableColumns.ArPaymentDate + " like '%" + ladat + "%' " +
                "order by " + DbContractor.TableColumns.ArPaymentDate + " asc ", null );
        if (molhanotCursor.getCount() == 0) {
            return;
        } else
            molhanotCursor.moveToFirst();
        while (!molhanotCursor.isAfterLast()) {
            String Mlhanot = molhanotCursor.getString( molhanotCursor.getColumnIndex( DbContractor.TableColumns.ArPaymentDate ) );
            searchspinerList.add( Mlhanot );
            molhanotCursor.moveToNext();
        }
        molhanotCursor.close();
        searchspinner.setAdapter( searchAdapter );

    }*/
    private void fillwithStar() {
        searchspinerList.clear();
        searchspinerList.add( "" );
        searchspinner.setAdapter( searchAdapter );
    }
    private void addtoCategoryList() {
        categoryspinnerList.add( "الكل" );
        categoryspinnerList.add( "حسب المحل" );
    }
}

