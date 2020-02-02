package com.dev_bourheem.hadi;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class stats extends AppCompatActivity {

    List<DataEntry> data;
    MyDataBaseCreator MBC = new MyDataBaseCreator(this);
    TextView tester;
    ArrayList<String> things;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        tester = findViewById(R.id.testerV);
        things = new ArrayList<>();
        PiesIt();
        hh();
        // Adds();
    }

    public void Adds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adViewstats);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void PiesIt() {
        Pie pie = AnyChart.pie();
        data = new ArrayList<>();
        hh();
        pie.data(data);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }


    public void hh() {
        things.clear();
        Cursor cu = MBC.GetDBdata();
        if (cu.getCount() == 0) {
            return;
        } else {
            while (cu.moveToNext()) {
                String item = cu.getString(cu.getColumnIndex(MyDataBaseCreator.col1));
                int too = cu.getInt(cu.getColumnIndex("total"));
                data.add(new ValueDataEntry(item, too));

            }
            cu.close();
        }

    }


}
