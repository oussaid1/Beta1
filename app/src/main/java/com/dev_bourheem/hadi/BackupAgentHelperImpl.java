package com.dev_bourheem.hadi;

import android.app.backup.FileBackupHelper;
import android.content.Context;

class DbBackupHelper extends FileBackupHelper {
    public static final String DB_NAME = "School";

    /**
     * Construct a helper to manage backup/restore of entire files within the
     * application's data directory hierarchy.
     */
    public DbBackupHelper(Context ctx, String dbName) {
        super( ctx, ctx.getDatabasePath( dbName ).getAbsolutePath() );
    }

}
