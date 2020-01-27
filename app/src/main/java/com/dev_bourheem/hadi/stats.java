package com.dev_bourheem.hadi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
    // ArrayAdapter<items> listada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        tester = findViewById(R.id.testerV);
        things = new ArrayList<>();
        PiesIt();
        hh();
       Adds();
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
        //data.add(new ValueDataEntry("John", 1));
        hh();
        pie.data(data);
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }


    public void hh() {
        things.clear();
        Cursor cu = MBC.GetDBdata();
        if (cu.getCount() == 0) {
            Toast.makeText(this, "No Players , Please Add Them", Toast.LENGTH_SHORT).show();
        } else {
            while (cu.moveToNext()) {
                // int logoP1 = cu.getInt(cu.getColumnIndex(PlayersAndTeamsStock.teamP1));
                String P1 = cu.getString(cu.getColumnIndex(MBC.col1));
                int scoreP1 = cu.getInt(cu.getColumnIndex("total"));
               // int scoreP2 = cu.getInt(cu.getColumnIndex("goalsP2"));
               ///**/ String P2 = cu.getString(cu.getColumnIndex(PlayersAndTeamsStock.PlayerP2));

                data.add(new ValueDataEntry(P1, scoreP1));
              //  data.add(new ValueDataEntry(P2, scoreP2));
                //tester.setText(""+scoreP2);
            }
            cu.close();
        }
        //  listada = new ArrayAdapter<items>(this, android.R.layout.simple_list_item_1, things);


    }

   /* public void zins() {
        things.clear();
        Cursor cu = PATS.getPlayersScores();
        if (cu.getCount() == 0) {
            Toast.makeText(this, "No Players , Please Add Them", Toast.LENGTH_SHORT).show();
        } else {
            while (cu.moveToNext()) {

                String P1 = cu.getString(cu.getColumnIndex(PlayersAndTeamsStock.PlayerP1));
                int scoreP1 = cu.getInt(cu.getColumnIndex("zins1"));
                int scoreP2 = cu.getInt(cu.getColumnIndex("zins2"));
                String P2 = cu.getString(cu.getColumnIndex(PlayersAndTeamsStock.PlayerP2));

                data.add(new ValueDataEntry(P1, scoreP1));
               // things.add(new items(scoreP1,P1,scoreP2,P2));
                tester.setText(""+scoreP2);
            }
            cu.close();
        }
        listada = new ArrayAdapter<items>(this, android.R.layout.simple_list_item_1, things.indexOf(1));
        lista.setAdapter(listada);

    }*/
}
