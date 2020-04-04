package com.dev_bourheem.hadi.mainStuff;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.dev_bourheem.hadi.DatabaseClass.DbContractor;
import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;
import com.dev_bourheem.hadi.Login.LoginClass;
import com.dev_bourheem.hadi.R;
import com.dev_bourheem.hadi.sharedmethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
                sharedmethods.backitUp(this);
                return true;
            case R.id.restore:
                sharedmethods.restoreItUp(this);
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
        setusername = findViewById(R.id.UserNameDef);
        paidAmountIn = findViewById(R.id.paidamountV);
        payButton = findViewById(R.id.pay);
        ShopToPaySpinner = findViewById(R.id.payfor);
        setpassword = findViewById(R.id.PasswordDef);
        archiveIt = findViewById(R.id.archiveIt);
        confirmpass = findViewById(R.id.ConfirmPassDef);
        SaveBtnforuserdata = findViewById(R.id.saveBtnforuserdata);
        saveBtnforuserQuotas = findViewById(R.id.saveBtnforquotas);
        ShopToDeleteSpinner = findViewById(R.id.shopTodelete);
        delete = findViewById(R.id.deleteby);
        MDBC = new MyDataBaseCreator(getApplicationContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialodgDelete();
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
        archiveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paidAmountIn.getText().toString().trim().length() != 0 && !ShopToPaySpinner.getSelectedItem().toString().trim().isEmpty()) {
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

    public String GetDate() {
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
                    OpenActiviti();
                } else MsgBox(getString(R.string.notsaved));
            } else MsgBox(getString(R.string.wrongpassoruser));
        }
    }

    public void ResetAll() {
        Lgin = new LoginClass(getApplicationContext());

        Lgin.deleteall();
        MDBC.deletAllFromTable(DbContractor.TableColumns.MainTable);
        MDBC.deletAllFromTable(DbContractor.TableColumns.InfoTable);
        MDBC.deletAllFromTable(DbContractor.TableColumns.ArchiveTable);
        MDBC.deletAllFromTable(DbContractor.TableColumns.PaymentTable);
        MDBC.deletAllFromTable(DbContractor.TableColumns.ArchivePaymentTable);

    }

    private void OnDialodgDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(getString(R.string.surewannadelall));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MDBC.DeleteAllBy(DbContractor.TableColumns.MainTable,DbContractor.TableColumns.MShopName, ShopToDeleteSpinner.getSelectedItem().toString());
                    }
                });
        builder.setNegativeButton(R.string.no, null).show();
    }

    public void onDialogue2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert));
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
        builder.setTitle(getString(R.string.alert));
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
        ShopNamesList = MDBC.GetDistinctFromTable(DbContractor.TableColumns.MainTable, DbContractor.TableColumns.MShopName, DbContractor.TableColumns.MShopName);
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
                } else {
                    MsgBox(getString(R.string.payunsuccessful));
                }
                ArchiveIt(ShopToPaySpinner.getSelectedItem().toString());
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }
    /*public boolean isFirstDayOfMonth(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("Calendar cannot be null.");
        }
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth == 1;
    }*/

    public void ArchiveIt(String ShopName) {

            SQLiteDatabase db = MDBC.getWritableDatabase();
             db.execSQL(" insert into " + DbContractor.TableColumns.ArchiveTable + "" +
                     " (" + DbContractor.TableColumns.ArItem_Name + "," +
                     "" + DbContractor.TableColumns.ArQuantifier + "," +
                     "" + DbContractor.TableColumns.ArQuantity + "," +
                     "" + DbContractor.TableColumns.ArItem_Price + " ," +
                     "" + DbContractor.TableColumns.ArShopName + " ," +
                     " "+ DbContractor.TableColumns.ArDate + ") select " + DbContractor.TableColumns.MItem_Name  + "," +
                     " "+ DbContractor.TableColumns.MQuantifier + "," +
                     "" + DbContractor.TableColumns.MQuantity + "," +
                     "" + DbContractor.TableColumns.MItem_Price + " ," +
                     "" + DbContractor.TableColumns.MShopName + " ," +
                     " "+ DbContractor.TableColumns.MDate + " from " +DbContractor.TableColumns.MainTable +"" +
                     " where " + DbContractor.TableColumns.MShopName + " like  '%"+ShopName+"%' ");

        db.execSQL(" insert into " + DbContractor.TableColumns.ArchivePaymentTable + "" +
                " (" + DbContractor.TableColumns.ArPaidShopName + "," +
                "" + DbContractor.TableColumns.ArPaidAmount + "," +
                "" + DbContractor.TableColumns.ArPaymentDate +") select " + DbContractor.TableColumns.PaidShopName  + "," +
                " "+ DbContractor.TableColumns.PaidAmount + "," +
                "" + DbContractor.TableColumns.PaymentDate + " from " +DbContractor.TableColumns.PaymentTable +"" +
                " where " + DbContractor.TableColumns.PaidShopName + " like '%"+ShopName+"%' ");

          MDBC.DeleteAllBy(DbContractor.TableColumns.MainTable,DbContractor.TableColumns.MShopName,ShopName);
          MDBC.DeleteAllBy(DbContractor.TableColumns.PaymentTable,DbContractor.TableColumns.PaidShopName,ShopName);
        }

    }


