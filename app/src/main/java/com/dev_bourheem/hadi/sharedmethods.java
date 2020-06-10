package com.dev_bourheem.hadi;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_MUSIC;
import static com.dev_bourheem.hadi.mainStuff.MainActivity.GetDate;

public class sharedmethods {

    public static void restoreItUp(Context context) {
        try {
            File sd = context.getFilesDir();
            File data = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            if (sd.canWrite()) {

                String currentDBPath = "Merchandise.db";
                String backupDBPath = "importedMerchandise.db"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Import Successful!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Toast.makeText(context, "Import Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    public static void importDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                File backupDB = context.getDatabasePath("DBHandler.getDBName()");
                String backupDBPath = String.format("%s.bak", "DBHandler.getDBName()");
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void exportDB(Context context) throws IOException {
            String sd = context.getExternalFilesDir(DIRECTORY_DOWNLOADS).getPath();
            String data = context.getDataDir().getPath();
                File currentDB = new File(data,"Merchandise.db");
                File backupDB = new File(sd, "BackUpMerchandise.db");
                InputStream src = new FileInputStream(currentDB);
                OutputStream dst = new FileOutputStream(backupDB);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = src.read(buffer)) > 0) {
            dst.write(buffer, 0, length);
        }
        dst.flush();
        dst.close();
        src.close();
                Toast.makeText(context, "Backup Successful!",
                        Toast.LENGTH_SHORT).show();
        }


    public static void BackUp(Context context) throws IOException {

        InputStream input = new FileInputStream(context.getDatabasePath("Merchandise.db"));

        // create directory for backup

        File sd = Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC);
        // Path to the external backup
        OutputStream output = new FileOutputStream(sd);

        // transfer bytes from the Input File to the Output File
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
        Toast.makeText(context, "Backup Successful!",
                Toast.LENGTH_SHORT).show();

    }

}
