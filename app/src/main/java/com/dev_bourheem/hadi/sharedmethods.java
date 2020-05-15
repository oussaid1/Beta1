package com.dev_bourheem.hadi;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.dev_bourheem.hadi.DatabaseClass.MyDataBaseCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.dev_bourheem.hadi.mainStuff.MainActivity.GetDate;

public class sharedmethods {

    public static void restoreItUp(Context context) {
        try {
            File sd = context.getFilesDir();
            File data =  Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            if (sd.canWrite()) {

                String currentDBPath ="Merchandise.db";
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

    public static void backitUp(Context contexta) {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
            File data = contexta.getFilesDir();

            if (sd.canWrite()) {
                String currentDBPath ="Merchandise.db";
                String backupDBPath = "BackedUpMerchandise.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(contexta, "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(contexta, "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

}
