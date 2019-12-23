package com.dev_bourheem.hadi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MyDataBaseCreator MDBC;
    public Button addBut;
    public EditText NameIn, PriceIn, moolhanotNm;
    public TextView DateV1, RedL, OrangeL, GreenL, LeftOut, TotalOut, RedText, OrangeText, GreenText;
    public Spinner ItMSpinner, molhanotSpinner;
    public ListView ListaOut;
    public Switch Guestmode;
    public boolean ischecked;
    public double LeftOfQuota, ItemPriceDbl;
    public String date, ItemNameStr, itemId, Sir;
    public int Quota;
    public ArrayList<String> allList;
    public ArrayList<String> Molhanot;
    public ArrayAdapter<String> MolhntSpinnerAdapter;
    public static ArrayList<String> dataBaselist;
    public ArrayAdapter<String> SpinnerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteTB_M:
                DeleteTablecontent();
                return true;
            case R.id.about_M:
                //funtion here
                return true;
            case R.id.ShowList_M:
                OpentAvtivity2();
                return true;
            case R.id.Add_M:
                //func
                return true;
            case R.id.RefreshTB_M:
                //function here
                return true;
            case R.id.Statis_M:
                OpentAvtivity3();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateV1 = findViewById(R.id.dateView);
        RedText = findViewById(R.id.Limit);
        OrangeText = findViewById(R.id.Carefull);
        GreenText = findViewById(R.id.Good);
        GreenL = findViewById(R.id.BtnGreen);
        Guestmode = findViewById(R.id.SwitchGuest);
        ListaOut = findViewById(R.id.list_VActivity2);
        OrangeL = findViewById(R.id.BtnOrange);
        RedL = findViewById(R.id.BtnRed);
        ItMSpinner = findViewById(R.id.ItemNameInSp);
        NameIn = findViewById(R.id.ItemNameIn);
        moolhanotNm = findViewById(R.id.molhanoutNameIn);
        molhanotSpinner = findViewById(R.id.molhanoutNameInSp);
        molhanotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String molhanotname = String.valueOf(parent.getItemAtPosition(position));
                moolhanotNm.setText(" " + molhanotname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PriceIn = findViewById(R.id.ItemPriceIn);
        LeftOut = findViewById(R.id.QuotaLeftOut);
        TotalOut = findViewById(R.id.TotalTodayOut);
        addBut = findViewById(R.id.AddBtn);
        ItMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SpineerText = String.valueOf(parent.getItemAtPosition(position));
                NameIn.setText(" " + SpineerText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DateV1.setText("" + GetDate());
        MDBC = new MyDataBaseCreator(getApplicationContext());
        dataBaselist = new ArrayList<>();
        FillArrList();
        FillMolhanot();
        getTotal();
        GetDbData();
        TraficLight();
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PriceIn.length() != 0 && NameIn.length() != 0) {
                    openDialogue();
                    LoadDatabase();
                    getTotal();
                    GetDbData();
                    TraficLight();
                }else MsgBox("Plz Insert Data");
            }
        });

        Guestmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked = Guestmode.isChecked();
                if (ischecked) {
                    MsgBox("Guest Mode On");
                } else {
                    MsgBox("Family Mode On");
                }
                getTotal();
                TraficLight();
            }
        });

    }

    public void OpentAvtivity2() {
        final Intent intent1;
        intent1 = new Intent(this, Main2Activity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent1);
    }

    public void OpentAvtivity3() {
        final Intent intent2;
        intent2 = new Intent(this, Main3Activity.class);
        //intent1.putExtra("tarikh" ,date);
        startActivity(intent2);
    }

    public String GetDate() {
        Date currentTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(" dd / MMM / yy");
        date = dateFormat.format(currentTime);
        ///Intent dateIntent= new Intent(date);
        //startActivity(dateIntent);
        return date;
    }

    public void FillMolhanot() {
        Molhanot = new ArrayList<>();
        Molhanot.add(" Hessina ");
        Molhanot.add(" Momo ");
        Molhanot.add(" Belaise ");
        MolhntSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Molhanot);
        molhanotSpinner.setAdapter(MolhntSpinnerAdapter);
    }

    public void FillArrList() {
        allList = new ArrayList<String>();
        allList.add(" Milk ");
        allList.add(" Danon ");
        allList.add(" Honey ");
        allList.add(" Sugar ");
        allList.add(" Bread ");
        allList.add(" Butter ");
        allList.add("Laban");
        allList.add("Tea");
        allList.add("Yeast");
        allList.add("Eggs");
        allList.add("Tuna");
        allList.add("Oil");
        allList.add("Jam");
        allList.add("Tide");
        allList.add("NoteBooks");
        allList.add("Spaghetti");
        allList.add("Pasta");
        allList.add("Biscuit");
        SpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, allList);
        ItMSpinner.setAdapter(SpinnerAdapter);
    }

    public int GetQuota() {
        if (Guestmode.isChecked()) {
            Quota = 600;
            // MsgBox(getString(R.string.guestmodeon));
        } else
            Quota = 300;
        // MsgBox(getString(R.string.guestmodeoff));
        return Quota;
    }

    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void LoadDatabase() {
// get !!!!!!!!!edditext input to vars.
        String hiTp = PriceIn.getText().toString();
        ItemPriceDbl = Double.parseDouble(PriceIn.getText().toString().trim());
        ItemNameStr = NameIn.getText().toString();
        Sir = moolhanotNm.getText().toString();
// insert data to database's Table.
        boolean newRowAdded = MDBC.InjectData(Sir, ItemNameStr, ItemPriceDbl);
        if (newRowAdded) {
            MsgBox("data inserted");
        } else MsgBox("data not inserted");
    }

    private void PrintMessage(String title, String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder(this);
        newAlert.setCancelable(true);
        newAlert.setTitle(title);
        newAlert.setMessage(message);
        newAlert.show();
    }

    public void DeleteTablecontent() {
        boolean del = MDBC.deleteall();
        if (del) MsgBox("deleted all");
        else MsgBox("not deleted");
    }

    //this method gets the Sum of all elements in ItemPrice Column
    public void getTotal() {

        Cursor c = MDBC.GetSum();
        if (c.getCount() == 0) MsgBox("No Sum");
        else
            while (c.moveToNext()) {
                double itemsSum = c.getInt(0);
                c.close();
                GetQuota();
                TotalOut.setText(" " + itemsSum);
                LeftOfQuota = Quota - itemsSum;
                LeftOut.setText(" " + LeftOfQuota);
                //localDatabase.close();
            }
    }

    //this method gets Cursor data from table products
//and fills an arraylist dataBaselist
    public void GetDbData() {
        dataBaselist.clear();
        Cursor data = MDBC.GetDBdata();

        if (data.getCount() == 0) {
            MsgBox("no data to show");
        } else if (data.moveToNext()) {
            while (!data.isAfterLast())
                do {
                    itemId = data.getString(data.getColumnIndex("FullItem"));

                    dataBaselist.add(itemId);
                } while ((data.moveToNext()));

        }
        data.close();
    }

    // this method controlls Traffic Light and the text with it
    public void TraficLight() {
        float halfquota = Quota / 2;
        if (LeftOfQuota < Quota && LeftOfQuota > 0) {
            RedL.setVisibility(View.INVISIBLE);
            RedText.setText("");
            OrangeL.setVisibility(View.INVISIBLE);
            OrangeText.setText("");
            GreenL.setVisibility(View.VISIBLE);
            GreenText.setVisibility(View.VISIBLE);
            GreenText.setText(R.string.good);
        }
        if (LeftOfQuota < halfquota && LeftOfQuota > 0) {
            GreenL.setVisibility(View.INVISIBLE);
            GreenText.setText("");
            OrangeL.setVisibility(View.VISIBLE);
            OrangeText.setText("");
            OrangeText.setVisibility(View.VISIBLE);
            OrangeText.setText(R.string.carefull);
        }
        if (LeftOfQuota < 0) {
            GreenL.setVisibility(View.INVISIBLE);
            GreenText.setText("");
            OrangeL.setVisibility(View.INVISIBLE);
            OrangeText.setText("");
            RedL.setVisibility(View.VISIBLE);
            RedText.setVisibility(View.VISIBLE);
            RedText.setText(R.string.limit_exceeded);

        }
    }

    public void openDialogue() {
        Alerdialogue AlrtDlg = new Alerdialogue();
        AlrtDlg.show(getSupportFragmentManager(), "Something");
    }


}
