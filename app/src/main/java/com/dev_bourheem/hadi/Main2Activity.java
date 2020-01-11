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
import android.widget.Toast;

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
    String date2;
    ArrayList<String> mainList;
    ArrayAdapter mainListAdapter;

    private ArrayList<exampleitem> mExampleList;
    RecyclerView myrecycler;
    RecyclerView.LayoutManager RecyLayManger;
    ExampleAdapter RecyclerAdap;


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

            }
        });
        ShowListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GetDbData();


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
                    String idnonit= data.getString(data.getColumnIndex(MdbCrtr.ID));
                    String quantity= data.getString(data.getColumnIndex(MdbCrtr.Quantity));
                    String qunatifier= data.getString(data.getColumnIndex(MdbCrtr.Quantifier));
                    String itemNm = data.getString(data.getColumnIndex(MdbCrtr.col1));
                    String itemName = data.getString(data.getColumnIndex(MdbCrtr.col2));
                    String shopNm = data.getString(data.getColumnIndex(MdbCrtr.person));
                    String DateBout = data.getString(data.getColumnIndex(MdbCrtr.da));
                    mExampleList.add(new exampleitem( idnonit,quantity,qunatifier ,itemNm, itemName,shopNm,DateBout));
                   // mainList.add(itemId);
                } while ((data.moveToNext()));

        }
        data.close();

        RecyLayManger =new LinearLayoutManager(this);
        RecyclerAdap= new ExampleAdapter(mExampleList);
        myrecycler.setLayoutManager(RecyLayManger);
        myrecycler.setAdapter(RecyclerAdap);
        RecyclerAdap.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                Intent intent =new Intent(Main2Activity.this,EdditActivity.class);
                intent.putExtra("exampleItem",mExampleList.get(position));
                startActivity(intent);

                Toast.makeText(Main2Activity.this,"am clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnItemDelete(int position) {
                //DeletItem(position);
            }
        });
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