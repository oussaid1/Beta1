package com.dev_bourheem.hadi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dev_bourheem.hadi.Login.ForQuotas;
import com.dev_bourheem.hadi.Login.LoginClass;

import java.io.IOException;

import static com.dev_bourheem.hadi.MainActivity.Backup;
import static com.dev_bourheem.hadi.MainActivity.Restore;

public class Settings extends AppCompatActivity {

    EditText setQuota, setGuestQta, setusername, setpassword, confirmpass;
    Button saveBtnforuserQuotas, SaveBtnforuserdata;
    double user_defined_Quota, user_defined_guestQuota;
    String Userusername, Userpassword, Confirmedpass;
    LoginClass Lgin;
    ForQuotas forQutaOC;
    MyDataBaseCreator MDBC;
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
                try {
                    if (Backup()) {
                        PrintMessage( "تم" );
                    } else {
                        PrintMessage( "لم يتم" );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.restore:
                RestoreِConferm();
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
        setpassword = findViewById(R.id.PasswordDef);
        confirmpass = findViewById(R.id.ConfirmPassDef);
        SaveBtnforuserdata = findViewById(R.id.saveBtnforuserdata);
        saveBtnforuserQuotas = findViewById(R.id.saveBtnforquotas);
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
        forQutaOC = new ForQuotas(getApplicationContext());

          if (setQuota.getText().toString().trim().length() == 0 || setGuestQta.getText().toString().trim().length() == 0) {
             return;
          }else
            user_defined_Quota = Double.parseDouble(setQuota.getText().toString().trim());
            user_defined_guestQuota = Double.parseDouble(setGuestQta.getText().toString().trim());
            boolean newRowAdded = forQutaOC.InjectData(user_defined_Quota, user_defined_guestQuota);
            if (newRowAdded) {
                MsgBox("تم الحفظ");
                OpenActiviti();
            } else {
                MsgBox("لم يتم الحفظ");
                 setQuota.setText("0");
                setGuestQta.setText("0");
            }

    }

    public void MsgBox(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void LoadusertoDatabase() {
        Lgin = new LoginClass(getApplicationContext());
        if ((setusername.getText().toString().trim().length() == 0 || setpassword.getText().toString().trim().length() == 0)) {
            MsgBox("المرجو ادخال المعلومات اولا");
        } else {
            Userusername = setusername.getText().toString().trim();
            Userpassword = setpassword.getText().toString().trim();
            Confirmedpass = confirmpass.getText().toString().trim();
            if (Userpassword.equals(Confirmedpass)) {
                boolean newRowAdded = Lgin.InjectData(Userusername, Userpassword);

                if (newRowAdded) {
                    MsgBox("تم الحفظ");
                    OpenActiviti();
                }else MsgBox("لم يتم الحفظ");
            } else MsgBox("الإسم والقن غير متطابقان");
        }
    }

    public void ResetAll() {
        Lgin = new LoginClass(getApplicationContext());
        forQutaOC = new ForQuotas(getApplicationContext());
        MDBC = new MyDataBaseCreator(getApplicationContext());
        Lgin.deleteall();
        forQutaOC.deleteall();
        MDBC.deleteall();
    }

    private void PrintMessage(String message) {
        AlertDialog.Builder newAlert = new AlertDialog.Builder(this);
        newAlert.setCancelable(true);
        newAlert.setMessage(message);
        newAlert.show();
    }
    public void onDialogue2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert));
        builder.setMessage(getString(R.string.doyourealywantdeletall));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                ResetAll();


            }
        } );
        builder.setNegativeButton( android.R.string.no, null );
        builder.show();
    }

    public void RestoreِConferm() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( getString( R.string.alert ) );
        builder.setMessage( getString( R.string.doyourealywantdeletall ) );
        builder.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                if (Restore()) {
                    MsgBox( "تم" );
                } else {
                    MsgBox( "لم يتم" );
                }

            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }
}

