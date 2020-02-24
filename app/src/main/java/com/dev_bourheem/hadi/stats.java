package com.dev_bourheem.hadi;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class stats extends AppCompatActivity {

    MyDataBaseCreator MBC = new MyDataBaseCreator(this);
    TextView  tester2;
    List<DataEntry> datax;
    AnyChartView anyChartView;
    List<DataEntry> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        anyChartView = findViewById(R.id.chart2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        data = new ArrayList<>();
        datax = new ArrayList<>();
        tester2 = findViewById(R.id.testerV);
        Cartesian cartesian = AnyChart.column();

        PieThat();
       /* datax.add(new ValueDataEntry("Rouge", 80540));
        datax.add(new ValueDataEntry("Foundation", 94190));*/


        Column column = cartesian.column(datax);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("حجم الإستهلاك حسب الأيام");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("الأيام");
        cartesian.yAxis(0).title("الثمن");

        anyChartView.setChart(cartesian);
        AnyChartView anyChartView2= findViewById(R.id.chartPie);
        APIlib.getInstance().setActiveAnyChartView(anyChartView2);

        Pie pie = AnyChart.pie();

        PieThatThing();
        /*data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));*/


        pie.data(data);

        pie.title("استهلاك المواد");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text(" حسب السلعة")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView2.setChart(pie);


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

        Cartesian cartesian = AnyChart.column();


        datax.add(new ValueDataEntry("Rouge", 80540));
        datax.add(new ValueDataEntry("Foundation", 94190));
        datax.add(new ValueDataEntry("Mascara", 102610));
        datax.add(new ValueDataEntry("Lip gloss", 110430));
        datax.add(new ValueDataEntry("Lipstick", 128000));
        datax.add(new ValueDataEntry("Nail polish", 143760));
        datax.add(new ValueDataEntry("Eyebrow pencil", 170670));
        datax.add(new ValueDataEntry("Eyeliner", 213210));
        datax.add(new ValueDataEntry("Eyeshadows", 249980));

        Column column = cartesian.column(datax);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);
    }


    public void PieThatThing() {
        data.clear();

        Cursor cu = MBC.GetDBdata();
        if (cu.getCount() == 0) {
            return;
        } else {
            while (cu.moveToNext()) {
                String item = cu.getString(cu.getColumnIndex(MyDataBaseCreator.col1));
                int price = cu.getInt(cu.getColumnIndex("total"));
                data.add(new ValueDataEntry(item, price));
            }
            cu.close();
        }
    }

    public void PieThat() {
        datax.clear();

        Cursor cu = MBC.GetdataByDate();
        if (cu.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cu.moveToNext()) {
                String date = cu.getString(cu.getColumnIndex(MyDataBaseCreator.da));
                int price = cu.getInt(cu.getColumnIndex("totalbydate"));
                datax.add(new ValueDataEntry(date, price));
            }
            cu.close();
        }
    }
}
