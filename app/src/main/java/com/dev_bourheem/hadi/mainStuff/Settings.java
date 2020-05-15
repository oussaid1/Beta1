package com.dev_bourheem.hadi.mainStuff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.Login.LoginClass;
import com.dev_bourheem.hadi.R;
import com.dev_bourheem.hadi.sharedmethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class Settings extends AppCompatActivity {
    private Spinner ShopToDeleteSpinner, ShopToPaySpinner;

    private TextView archiveIt;
    private EditText setQuota, setGuestQta, setusername, setpassword, confirmpass, paidAmountIn;
    private Button delete, saveBtnforuserQuotas, SaveBtnforuserdata, payButton;
    private double user_defined_Quota, user_defined_guestQuota;
    private String Userusername, Userpassword, Confirmedpass;
    private LoginClass Lgin;
    private MyDataBaseCreator MDBC;
    private ArrayAdapter<String> ShopNamesSpinnerAdapter;
    private ArrayList<String> ShopNamesList;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Additems:
                OpenActiviti();
                //func here
                return true;
            case R.id.ShowList_M:
                OpenActivitilist();
                return true;
            case R.id.backup:
               backUp();
                return true;
            case R.id.restore:
            restore();
                return true;
            case R.id.reset:
                onDialogue2();
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
        setContentView(R.layout.activity_settings);
        setQuota = findViewById(R.id.limitQuotaDef);
        setGuestQta = findViewById(R.id.limitGuestDef);
        setusername = findViewById(R.id.usernameDef);
        paidAmountIn = findViewById(R.id.paidamountV);
        payButton = findViewById(R.id.saveBtnforPayment);
        ShopToPaySpinner = findViewById(R.id.payfor);
        setpassword = findViewById(R.id.passwordDef);
        confirmpass = findViewById(R.id.ConfirmPassView);
        SaveBtnforuserdata = findViewById(R.id.saveBtnforuserdata);
        saveBtnforuserQuotas = findViewById(R.id.saveBtnforquotas);
        ShopToDeleteSpinner = findViewById(R.id.shopTodelete);
        delete = findViewById(R.id.deleteby);
        MDBC = new MyDataBaseCreator(getApplicationContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ShopToDeleteSpinner != null && ShopToDeleteSpinner.getSelectedItem() !=null ) {
                    OnDialodgDelete();
                }
            }
        });
        SaveBtnforuserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadusertoDatabase();

            }
        });
        saveBtnforuserQuotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadQuotatoDatabase(); // injects user defined quota to database ;


            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paidAmountIn.getText().toString().trim().length() != 0 && ShopToPaySpinner != null && ShopToPaySpinner.getSelectedItem() !=null  ) {
                    ConfirmPay();
                } else {
                    MsgBox(getString(R.string.plzinsertamount));
                }
            }
        });
        FillWithShopNames();
    }

    // opens main activity where add and total
    public void OpenActiviti() {
        Intent myintdsent = new Intent(this, MainActivity.class);
        startActivity(myintdsent);
    }

    public void OpenActivitilist() {
        Intent myintenct = new Intent(this, ListActivity.class);
        startActivity(myintenct);
    }

    public  void backUp() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            File data = Settings.this.getFilesDir();

            if (sd.canWrite() ) {
                String currentDBPath ="Merchandise.db";
                String backupDBPath = "BackedUpMerchandise.db";
                File currentDB = this.getDatabasePath("Merchandise.db");
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(Settings.this, "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(Settings.this, "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }
    public  void restore() {
        try {
            File sd = Settings.this.getFilesDir();
            File data =  Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            if (sd.canWrite()) {

                String DBName =MyDataBaseCreator.database_name;
                String backupDBPath = "importedDatabase.db"; // From SD directory.
                File backupDB = new File(data, DBName);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(Settings.this, "Import Successful!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Toast.makeText(Settings.this, "Import Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }
    public void LoadQuotatoDatabase() {

        if (setQuota.getText().toString().trim().length() == 0 || setGuestQta.getText().toString().trim().length() == 0) {
            return;
        } else
            user_defined_Quota = Double.parseDouble(setQuota.getText().toString().trim());
        user_defined_guestQuota = Double.parseDouble(setGuestQta.getText().toString().trim());
        boolean newRowAdded = MDBC.InjectQuotaData(user_defined_Quota, user_defined_guestQuota);
        if (newRowAdded) {
            MsgBox(getString(R.string.saved));
            OpenActiviti();
        } else {
            MsgBox(getString(R.string.notsaved));
            setQuota.setText("");
            setGuestQta.setText("");
        }

    }

    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public Calendar GetCalendar() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return cal;
    }

    public static String GetDate() {
        Date currenttime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return dateFormat.format(currenttime);
    }

    public void LoadusertoDatabase() {
        Lgin = new LoginClass(getApplicationContext());
        if ((setusername.getText().toString().trim().length() == 0 || setpassword.getText().toString().trim().length() == 0)) {
            MsgBox(getString(R.string.insertinfofirst));
        } else {
            Userusername = setusername.getText().toString().trim();
            Userpassword = setpassword.getText().toString().trim();
            Confirmedpass = confirmpass.getText().toString().trim();
            if (Userpassword.equals(Confirmedpass)) {
                boolean newRowAdded = Lgin.InjectData(Userusername, Userpassword);

                if (newRowAdded) {
                    MsgBox(getString(R.string.saved));
                    setusername.getText().clear();
                    setpassword.getText().clear();
                    confirmpass.getText().clear();
                } else MsgBox(getString(R.string.notsaved));
            } else MsgBox(getString(R.string.wrongpassoruser));
        }
    }

    public void ResetAll() {
        Lgin = new LoginClass(getApplicationContext());
        Lgin.deleteall();
        MDBC.deletAllFromTable(MyDataBaseCreator.MainTable);
        MDBC.deletAllFromTable(MyDataBaseCreator.InfoTable);
        MDBC.deletAllFromTable(MyDataBaseCreator.ArchiveTable);
        MDBC.deletAllFromTable(MyDataBaseCreator.PaymentTable);
        MDBC.deletAllFromTable(MyDataBaseCreator.ArchivePaymentTable);

    }

    private void OnDialodgDelete() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        mbuilder.setTitle(R.string.warning);
        mbuilder.setMessage(getString(R.string.surewannadelall));
        mbuilder.setIcon(android.R.drawable.ic_dialog_alert);
        mbuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MDBC.DeleteAllBy(MyDataBaseCreator.MainTable, MyDataBaseCreator.MShopName, ShopToDeleteSpinner.getSelectedItem().toString());
            }
        });
        mbuilder.setNegativeButton(R.string.no, null).create().show();
    }

    public void onDialogue2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.doyourealywantdeletall));
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                ResetAll();


            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    public void RestoreConferm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.doyourealywantorestore));
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    private void FillWithShopNames() {
        ShopNamesList = new ArrayList<>();
        ShopNamesList = MDBC.GetDistinctFromTable(MyDataBaseCreator.MainTable, MyDataBaseCreator.MShopName, MyDataBaseCreator.MShopName);
        ShopNamesSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShopNamesList);
        ShopToPaySpinner.setAdapter(ShopNamesSpinnerAdapter);
        ShopToDeleteSpinner.setAdapter(ShopNamesSpinnerAdapter);
    }

    public void ConfirmPay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.warning));
        builder.setMessage(R.string.surewanapay);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                if (MDBC.PayShop(ShopToPaySpinner.getSelectedItem().toString(), Double.parseDouble(paidAmountIn.getText().toString().trim()), GetDate())) {
                    MsgBox(getString(R.string.paysuccess));
                    ArchiveIt();
                } else {
                    MsgBox(getString(R.string.payunsuccessful));
                }

            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }



    public void ArchiveIt() {
        if (MDBC.TablegetCountIsFull(MyDataBaseCreator.PaymentTable, MyDataBaseCreator.PaidShopName, ShopToPaySpinner.getSelectedItem().toString())
                && MDBC.TablegetCountIsFull(MyDataBaseCreator.MainTable, MyDataBaseCreator.MShopName, ShopToPaySpinner.getSelectedItem().toString())) {
            if (MDBC.OweHimNothing(ShopToPaySpinner.getSelectedItem().toString()) == 0 /*|| isFirstDayOfMonth(GetCalendar())*/) {
                //String mohamed=  ShopToPaySpinner.getSelectedItem().toString();
                MDBC.ArchiveIt(ShopToPaySpinner.getSelectedItem().toString());
            }
        }
    }
}

