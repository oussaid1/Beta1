package com.dev_bourheem.hadi.mainStuff;

import android.R.layout;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    MyDataBaseCreator MdbCrtr;

    Spinner categoryspinner, searchspinner;
    ArrayList<String> mainList;
    ArrayList<String> categoryspinnerList, searchspinerList;
    ArrayAdapter<String> categoryAdapter, searchAdapter;


    private ArrayList<exampleitem> mExampleList;
    RecyclerView myrecycler;
    RecyclerView.LayoutManager RecyLayManger;
    public ExampleAdapter RecyclerAdap;

    AdView listAd;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.myexmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Add_M:
                //func here
                OpentAvtivity3();
                return true;
            case R.id.stats_M:
                //func here
                OpentStats();
                return true;
            case R.id.Settingsactvt2:
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
        setContentView(R.layout.listactivity);
        myrecycler = findViewById(R.id.myrecycler);
        listAd = findViewById(R.id.listAd);
        myrecycler.setHasFixedSize(true);
        categoryspinner = findViewById(R.id.CategorySpin);
        searchspinner = findViewById(R.id.searchSpin);
        mExampleList = new ArrayList<>();
        mainList = new ArrayList<>();
        MdbCrtr = new MyDataBaseCreator(this);
        categoryspinnerList = new ArrayList<>();
        addtoCategoryList();
        searchspinerList = new ArrayList<>();
        categoryAdapter = new ArrayAdapter<>(this, layout.simple_spinner_dropdown_item, categoryspinnerList);
        categoryspinner.setAdapter(categoryAdapter);
        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });

        searchAdapter = new ArrayAdapter<>(this, layout.simple_spinner_dropdown_item, searchspinerList);
        searchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sellection1 = categoryspinner.getSelectedItem().toString().trim();
                String selection2 = searchspinner.getSelectedItem().toString().trim();
                switch (sellection1) {
                    case "الكل":
                        ShowlistAll();
                        break;
                    case "حسب المحل":
                        ShowlistByMolhanot(selection2);
                        break;
                    case "حسب التاريخ":
                        ShowlistByDate(selection2);
                        break;
                    case "حسب السلعة":
                        ShowlistByItem(selection2);
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RecyLayManger = new LinearLayoutManager(this);
        RecyclerAdap = new ExampleAdapter(mExampleList);
        RecyclerAdap.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                Intent intent = new Intent(ListActivity.this, EdditActivity.class);
                intent.putExtra("exampleItem", mExampleList.get(position));
                startActivity(intent);

            }

            @Override
            public void OnItemDelete(int position) {
                //DeletItem(position);
            }
        });
        Adslist();
    }

    public void Adslist() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        listAd.loadAd(adRequest);
    }

    public void OpentAvtivity3() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent(this, Settings.class);
        startActivity(intent2);
    }

    private void PrintMessage(String title, String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder(this);
        newAlert.setCancelable(true);
        newAlert.setTitle(title);
        newAlert.setMessage(message);
        newAlert.show();
    }

    public void ShowlistByMolhanot(String mohamed) {
        mExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + DbContractor.TableColumns.MainTable +
                " where  " + DbContractor.TableColumns.MShopName +
                " like '%" + mohamed + "%' order by history asc", null);

        if (data.getCount() == 0) {
            PrintMessage(getString(R.string.alert), getString(R.string.nodataintable));
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String quantity = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantity));
                String qunatifier = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantifier));
                String itemNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Name));
                String itemName = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Price));
                String shopNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MShopName));
                String DateBout = data.getString(data.getColumnIndex(DbContractor.TableColumns.MDate));
                mExampleList.add(new exampleitem(idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout));

            } while ((data.moveToNext()));

        data.close();
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);

    }

    public void ShowlistByDate(String datatata) {
        mExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + DbContractor.TableColumns.MainTable +
                " where  " + DbContractor.TableColumns.MDate +
                " like '%" + datatata + "%' order by history asc", null);

        if (data.getCount() == 0) {
            PrintMessage(getString(R.string.alert), getString(R.string.nodataintable));
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String quantity = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantity));
                String qunatifier = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantifier));
                String itemNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Name));
                String itemName = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Price));
                String shopNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MShopName));
                String DateBout = data.getString(data.getColumnIndex(DbContractor.TableColumns.MDate));
                mExampleList.add(new exampleitem(idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout));

            } while ((data.moveToNext()));

        data.close();
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);

    }

    public void ShowlistByItem(String goods) {
        mExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + DbContractor.TableColumns.MainTable +
                " where  " + DbContractor.TableColumns.MItem_Name + " like '%" + goods + "%' order by history asc", null);
        if (data.getCount() == 0) {
            PrintMessage(getString(R.string.alert), getString(R.string.nodataintable));
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String quantity = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantity));
                String qunatifier = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantifier));
                String itemNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Name));
                String itemName = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Price));
                String shopNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MShopName));
                String DateBout = data.getString(data.getColumnIndex(DbContractor.TableColumns.MDate));
                mExampleList.add(new exampleitem(idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout));

            } while ((data.moveToNext()));

        data.close();
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);

    }

    public void ShowlistAll() {
        mExampleList.clear();
        SQLiteDatabase db = MdbCrtr.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + DbContractor.TableColumns.MainTable +
                " order by history asc", null);
        if (data.getCount() == 0) {
            PrintMessage(getString(R.string.alert), getString(R.string.nodataintable));
        } else

            data.moveToNext();

        while (!data.isAfterLast())
            do {
                String idnonit = data.getString(data.getColumnIndex(DbContractor.TableColumns._ID));
                String quantity = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantity));
                String qunatifier = data.getString(data.getColumnIndex(DbContractor.TableColumns.MQuantifier));
                String itemNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Name));
                String itemName = data.getString(data.getColumnIndex(DbContractor.TableColumns.MItem_Price));
                String shopNm = data.getString(data.getColumnIndex(DbContractor.TableColumns.MShopName));
                String DateBout = data.getString(data.getColumnIndex(DbContractor.TableColumns.MDate));
                mExampleList.add(new exampleitem(idnonit, quantity, qunatifier, itemNm, itemName, shopNm, DateBout));

            } while ((data.moveToNext()));

        data.close();
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);

    }

    public void OpentStats() {
        Intent intent = new Intent(this, stats.class);
        startActivity(intent);
    }

    public void FillWithDates() {
        searchspinerList.clear();
        searchspinerList = MdbCrtr.GetDistinctFromTable(DbContractor.TableColumns.MainTable,DbContractor.TableColumns.MDate,DbContractor.TableColumns.MDate);
    }

    public void FillWithMolhanot() {
        searchspinerList.clear();
        searchspinerList = MdbCrtr.GetDistinctFromTable(DbContractor.TableColumns.MainTable,DbContractor.TableColumns.MShopName,DbContractor.TableColumns.MShopName);
    }

    public void FillWithItems() {
        searchspinerList.clear();
        searchspinerList = MdbCrtr.GetDistinctFromTable(DbContractor.TableColumns.MainTable,DbContractor.TableColumns.MItem_Name,DbContractor.TableColumns.MItem_Name);
    }


    public void fillwithStar() {
        searchspinerList.clear();
        searchspinerList.add("*");
        searchspinner.setAdapter(searchAdapter);
    }

    public void addtoCategoryList() {
        categoryspinnerList.add("الكل");
        categoryspinnerList.add("حسب المحل");
        categoryspinnerList.add("حسب التاريخ");
        categoryspinnerList.add("حسب السلعة");


    }

}