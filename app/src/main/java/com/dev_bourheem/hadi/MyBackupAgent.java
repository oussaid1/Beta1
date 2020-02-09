package com.dev_bourheem.hadi;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;

import java.io.File;

class MyBackupAgent extends BackupAgentHelper {
    private static final String DB_NAME = "School";

    @Override
    public void onCreate() {
        addHelper( "School", new DbBackupHelper( this, DB_NAME ) );
        FileBackupHelper dbs = new FileBackupHelper( this, DB_NAME );
        addHelper( "dbs", dbs );
    }
    @Override
    public File getFilesDir() {
        File path = getDatabasePath( DB_NAME );
        return path.getParentFile();
    }
}