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

import static android.os.Environment.*;
import static com.dev_bourheem.hadi.mainStuff.MainActivity.GetDate;

public class sharedmethods {

    public static boolean Backup() throws IOException {

        File dbFile = new File( "/data/data/com.dev_bourheem.hadi/databases/School.db" );
        FileInputStream fis = new FileInputStream( dbFile );

        String outFileName = Environment.getExternalStoragePublicDirectory( DIRECTORY_DOWNLOADS ) + "/" + GetDate() + ".db";
        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream( outFileName );

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read( buffer )) > 0) {
            output.write( buffer, 0, length );
        }
        // Close the streams
        output.flush();
        output.close();
        fis.close();
        return true;
    }


    public static boolean Restore() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory( DIRECTORY_DOWNLOADS );
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/com.dev_bourheem.hadi/databases/School.db";
                String backupDBPath = "hool.db";
                File currentDB = new File( data, currentDBPath );
                File backupDB = new File( sd, backupDBPath );

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream( backupDB ).getChannel();
                    FileChannel dst = new FileOutputStream( currentDB ).getChannel();
                    dst.transferFrom( src, 0, src.size() );
                    src.close();
                    dst.close();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void importDB(Context context) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                File backupDB = context.getDatabasePath( MyDataBaseCreator.database_name );
                String backupDBPath = MyDataBaseCreator.database_name;
                File currentDB = new File( sd, backupDBPath );

                FileChannel src = new FileInputStream( currentDB ).getChannel();
                FileChannel dst = new FileOutputStream( backupDB ).getChannel();
                dst.transferFrom( src, 0, src.size() );
                src.close();
                dst.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDBPath = MyDataBaseCreator.database_name;
                File currentDB = context.getDatabasePath( MyDataBaseCreator.database_name );
                File backupDB = new File( sd, backupDBPath );

                FileChannel src = new FileInputStream( currentDB ).getChannel();
                FileChannel dst = new FileOutputStream( backupDB ).getChannel();
                dst.transferFrom( src, 0, src.size() );
                src.close();
                dst.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean RestoreIt() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.dev_bourheem.hadi//databases//School.db";
                String backupDBPath = "School.db";
                File currentDB = new File( data, currentDBPath );
                File backupDB = new File( sd, backupDBPath );

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream( backupDB ).getChannel();
                    FileChannel dst = new FileOutputStream( currentDB ).getChannel();
                    dst.transferFrom( src, 0, src.size() );
                    src.close();
                    dst.close();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void restoreItUp(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.dev_bourheem.hadi"
                        + "//databases//" + "School.db";
                String backupDBPath = "School.db"; // From SD directory.
                File backupDB = new File( data, currentDBPath );
                File currentDB = new File( sd, backupDBPath );

                FileChannel src = new FileInputStream( backupDB ).getChannel();
                FileChannel dst = new FileOutputStream( currentDB ).getChannel();
                dst.transferFrom( src, 0, src.size() );
                src.close();
                dst.close();
                Toast.makeText( context, "Import Successful!",
                        Toast.LENGTH_SHORT ).show();

            }
        } catch (Exception e) {

            Toast.makeText( context, "Import Failed!", Toast.LENGTH_SHORT )
                    .show();

        }
    }

   public static void backitUp(Context contexta) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.dev_bourheem.hadi"
                        + "//databases//" + "School.db";
                String backupDBPath = "School.db";
                File currentDB = new File( data, currentDBPath );
                File backupDB = new File( sd, backupDBPath );

                FileChannel src = new FileInputStream( currentDB ).getChannel();
                FileChannel dst = new FileOutputStream( backupDB ).getChannel();
                dst.transferFrom( src, 0, src.size() );
                src.close();
                dst.close();
                Toast.makeText( contexta, "Backup Successful!",
                        Toast.LENGTH_SHORT ).show();

            }
        } catch (Exception e) {

            Toast.makeText( contexta, "Backup Failed!", Toast.LENGTH_SHORT )
                    .show();

        }
    }
}
