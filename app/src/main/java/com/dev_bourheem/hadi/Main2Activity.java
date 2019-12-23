package com.dev_bourheem.hadi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    TextView DateviewActvt2, back;
    ListView list_VActivity2Var;
    Button ShowListBtn;
    String date2;
    ArrayList<String> mainList;
    ArrayAdapter<String> mainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Method prints date to dateview
        DateviewActvt2 = findViewById(R.id.DateviewActvt2);
        list_VActivity2Var = findViewById(R.id.list_VActivity2);
        ShowListBtn = findViewById(R.id.ShowList);
        mainList = new ArrayList<>();

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
                FillmainList();

            }
        });
        GetDate();
    }

    public void GetDate() {
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(" dd / MMM / yy");
        date2 = dateFormat.format(currentTime);
        DateviewActvt2.setText("" + date2);
    }

    public void FillmainList() {
        mainListAdapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, MainActivity.dataBaselist);
        list_VActivity2Var.setAdapter(mainListAdapter);
    }
}