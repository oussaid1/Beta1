package com.dev_bourheem.hadi;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class stats extends AppCompatActivity {
    PieChart pieChartv1, pieChartv2;
    ArrayList<PieEntry> data;
    MyDataBaseCreator MBC = new MyDataBaseCreator( this );
    TextView tester, tester2;
    ArrayList<PieEntry> data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats );
        pieChartv1 = findViewById( R.id.pichartView );
        pieChartv2 = findViewById( R.id.pichartView2 );
        data2 = new ArrayList<>();
        data = new ArrayList<>();
        tester2 = findViewById( R.id.testerV );
       /* tester2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieThatThing();
            }
        } );
        tester.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );*/
        PieThatThing();
        // Adds();

        PieThat();
    }

    public void Adds() {
        MobileAds.initialize( this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        } );
        AdView mAdView = findViewById( R.id.adViewstats );
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd( adRequest );
    }

    public void PiesIt() {


    }


    public void PieThatThing() {
        data.clear();
        pieChartv2.setUsePercentValues( true );
        pieChartv2.getDescription().setEnabled( true );
        pieChartv2.setExtraOffsets( 5, 10, 5, 5 );
        pieChartv2.setDragDecelerationFrictionCoef( 0.95f );
        pieChartv2.setDrawHoleEnabled( true );
        pieChartv2.setCenterText( "السلع" );
        pieChartv2.setDrawEntryLabels( true );
        pieChartv2.setCenterTextSizePixels( 16 );
        pieChartv2.setHoleColor( Color.WHITE );
        pieChartv2.setTransparentCircleRadius( 61f );
        Cursor cu = MBC.GetDBdata();
        if (cu.getCount() == 0) {
            return;
        } else {
            while (cu.moveToNext()) {
                String item = cu.getString( cu.getColumnIndex( MyDataBaseCreator.col1 ) );
                int price = cu.getInt( cu.getColumnIndex( "total" ) );
                data.add( new PieEntry( price, item ) );
            }
            cu.close();
        }
        Description des = pieChartv2.getDescription();
        des.setEnabled( false );
        Legend leg = pieChartv2.getLegend();
        leg.setEnabled( true );
        PieDataSet pieDataSet = new PieDataSet( data, "" );

        pieDataSet.setSliceSpace( 3f );
        pieDataSet.setSelectionShift( 8f );
        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );

        PieData pieData = new PieData( pieDataSet );
        pieData.setValueTextColor( Color.YELLOW );
        pieData.setValueTextSize( 10f );
        pieChartv2.setData( pieData );

    }

    public void PieThat() {
        data2.clear();
        pieChartv1.setUsePercentValues( true );
        pieChartv1.getDescription().setEnabled( true );
        pieChartv1.setExtraOffsets( 5, 6, 5, 5 );
        pieChartv1.setDragDecelerationFrictionCoef( 0.95f );
        pieChartv1.setDrawHoleEnabled( true );
        pieChartv1.setCenterText( "السلع" );
        pieChartv1.setDrawEntryLabels( true );
        pieChartv1.setCenterTextSizePixels( 16 );
        pieChartv1.setHoleColor( Color.WHITE );
        pieChartv1.setTransparentCircleRadius( 41f );
        Cursor cu = MBC.GetdataByDate();
        if (cu.getCount() == 0) {
            return;
        } else {
            while (cu.moveToNext()) {
                String item = cu.getString( cu.getColumnIndex( MyDataBaseCreator.da ) );
                int price = cu.getInt( cu.getColumnIndex( "total" ) );
                data2.add( new PieEntry( price, item ) );
            }
            cu.close();
        }
        Description des = pieChartv1.getDescription();
        des.setEnabled( false );
        Legend leg = pieChartv1.getLegend();
        leg.setEnabled( true );
        PieDataSet pieDataSet = new PieDataSet( data2, "" );

        pieDataSet.setSliceSpace( 3f );
        pieDataSet.setSelectionShift( 8f );
        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );

        PieData pieData = new PieData( pieDataSet );
        pieData.setValueTextColor( Color.YELLOW );
        pieData.setValueTextSize( 10f );
        pieChartv1.setData( pieData );

    }


}
