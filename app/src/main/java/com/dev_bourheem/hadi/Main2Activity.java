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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.dev_bourheem.hadi.MyDataBaseCreator.MsgBox;

public class Main2Activity extends AppCompatActivity {
    MyDataBaseCreator MdbCrtr;
    MainActivity Mnactvt;
    TextView DateviewActvt2,refresh, back;
    ListView list_VActivity2Var;
    Button ShowListBtn;
    String date2,itemId;
    ArrayList<String> mainList;
    ArrayAdapter mainListAdapter;
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
                PrintMessage("Alert","working");
                return true;
            case R.id.SortBy_M:
                PrintMessage("Alert","yes working");
                return true;
            case R.id.del_M:
                //func
                MdbCrtr=new MyDataBaseCreator(this);
                MdbCrtr.deleteall();
                PrintMessage("Alert","Oops deleted all");
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
        list_VActivity2Var = findViewById(R.id.list_VActivity2);
        refresh=findViewById(R.id.refreshV);
        ShowListBtn = findViewById(R.id.ShowList);
        mainList=new ArrayList<>();
        MdbCrtr=new MyDataBaseCreator(this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mainListAdapter.notifyDataSetChanged();
               mainList.clear();
            }
        });
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
               if (mainList.isEmpty()) {
                    PrintMessage("Sorry", "There is No Data");
                }

                    //FillmainList();
                //MainActivity.dataBaselist.clear();
            }
        });
        GetDate();
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
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(" dd / MMM / yy");
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
        mainList.clear();
        Cursor data = MdbCrtr.GetDBdata();

        if (data.getCount() == 0) {
            PrintMessage("Alert","no data in table");
        } else if (data.moveToNext()) {
            while (!data.isAfterLast())
                do {
                    itemId = data.getString(data.getColumnIndex("FullItem"));
                    mainList.add(itemId);
                } while ((data.moveToNext()));

        }
        data.close();
        mainListAdapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1,mainList);
        list_VActivity2Var.setAdapter(mainListAdapter);
    }
    public void onDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Do you really want to delete all data ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}