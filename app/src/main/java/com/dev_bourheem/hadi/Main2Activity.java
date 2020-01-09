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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    MyDataBaseCreator MdbCrtr;
    TextView DateviewActvt2, back;

    Button ShowListBtn;
    String date2, itemId;
    ArrayList<String> mainList;
    ArrayAdapter mainListAdapter;

    private ArrayList<exampleitem> mExampleList;
    RecyclerView myrecycler;
    RecyclerView.LayoutManager RecyLayManger;
    ExampleAdapter RecyclerAdap;
    ArrayList<String> mContacts;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.myexmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Add_M:
                //func here
                OpentAvtivity3();
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
        setContentView(R.layout.activity_main2);
        // Method prints date to dateview
        DateviewActvt2 = findViewById(R.id.DateviewActvt2);
        myrecycler=findViewById(R.id.myrecycler);
        myrecycler.setHasFixedSize(true);
        ShowListBtn = findViewById(R.id.ShowList);
        mainList = new ArrayList<>();
        MdbCrtr = new MyDataBaseCreator(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ShowListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GetDbData();
               /** if (mainList.isEmpty()) {
                    PrintMessage(getString(R.string.sorry), getString(R.string.thereisnodata));
                }*/
                //doIt();

            }
        });
        GetDate();
        MdbCrtr = new MyDataBaseCreator(this);
    }

    public void OpentAvtivity3() {
        final Intent intent2;
        intent2 = new Intent(this, MainActivity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    public void OpentSettings() {
        final Intent intent2;
        intent2 = new Intent(this, Settings.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    public void GetDate() {
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(" dd / MM / yy");
        date2 = dateFormat.format(currentTime);
        DateviewActvt2.setText("" + date2);
    }

    /*public void FillmainList() {
        /*mainListAdapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, MainActivity.dataBaselist);
        list_VActivity2Var.setAdapter(mainListAdapter);
    }*/
    private void PrintMessage(String title, String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder(this);
        newAlert.setCancelable(true);
        newAlert.setTitle(title);
        newAlert.setMessage(message);
        newAlert.show();
    }

    public void GetDbData() {
        mExampleList = new ArrayList<>();
        Cursor data = MdbCrtr.GetDBdataAll();

        if (data.getCount() == 0)
            PrintMessage(getString(R.string.alert), getString(R.string.nodataintable));
        else if (data.moveToNext()) {
            while (!data.isAfterLast())
                do {
                    itemId = data.getString(data.getColumnIndex(MdbCrtr.col1));
                    String itemName = data.getString(data.getColumnIndex(MdbCrtr.col2));
                    String shopNm = data.getString(data.getColumnIndex(MdbCrtr.person));
                    String DateBout = data.getString(data.getColumnIndex(MdbCrtr.da));
                    mExampleList.add(new exampleitem(R.drawable.ic_spa, itemId , itemName,shopNm,DateBout));
                   // mainList.add(itemId);
                } while ((data.moveToNext()));

        }
        data.close();
       // mainListAdapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, mainList);


       // mExampleList.add(new exampleitem(R.drawable.ic_spa, "Line 1", "Line 2","momo","20/20/20"));
        RecyLayManger =new LinearLayoutManager(this);
        RecyclerAdap= new ExampleAdapter(mExampleList);
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);
        RecyclerAdap.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                DeletItem(position);
            }
        });
    }

    /**public void onDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.doyourealywantdeletall));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                MdbCrtr.deleteall();
                PrintMessage(getString(R.string.alert), getString(R.string.oopsdeletedall));
                //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }
    /**public void doIt(){
        mExampleList = new ArrayList<>();

        RecyLayManger =new LinearLayoutManager(this);
        RecyclerAdap= new ExampleAdapter(mExampleList);
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);
    }*/

    public void DeletItem(int position){
        mExampleList.remove(position);
        RecyclerAdap.notifyItemRemoved(position);
    }
}