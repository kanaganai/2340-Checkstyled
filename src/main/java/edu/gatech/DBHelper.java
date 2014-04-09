package main.java.edu.gatech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * DBHelper involving the databases.
 * @author Carter Smith
 *
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * the database version.
     */
    private static final int DATABASE_VERSION = 2;
    /**
     * the name of the database.
     */
    private static final String DATABASE_NAME = "Minimint";
    /**
     * the table users.
     */
    private static final String TABLE_USERS = "Users";
    /**
     * the table accounts.
     */
    private static final String TABLE_ACCOUNTS = "Accounts";
    /**
     * the table transactions.
     */
    private static final String TABLE_TRANSACTION = "Transactions";
    /**
     * creates the database of Users.
     */
    private static final String DATABASE_CREATE = "Create table Users(Email text primary key, Password text not null)";
    /** 
     * creates the database of Accounts.
     */
    private static final String DATABASE_ACCOUNTS_CREATE = "Create table " + TABLE_ACCOUNTS + "(Email text not null, AccountName text not null, Balance integer not null)";
    /**
     * creates the database of transactions.
     */
    private static final String DATABASE_TRANSACTION_CREATE = "Create table " + TABLE_TRANSACTION + "(Email text, AccountName text, Date text, Amount integer, TransactionType text, SourceDestination text)";
	
    /**
     * constructor.
     * @param context android activity context.
     */
    public DBHelper(Context context) {
		 super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.beginTransaction();
        try {
            database.execSQL(DATABASE_CREATE);
            Log.d("Create database", "Creating database user");
            database.execSQL(DATABASE_ACCOUNTS_CREATE);
            Log.d("Create database", "Creating Accounts database");
            database.execSQL(DATABASE_TRANSACTION_CREATE);
            Log.d("Create database", "Creating Transaction database");
            database.setTransactionSuccessful();
        }
        finally {
            database.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(arg0);
    }
	/**
	 * if the version is downgraded.
	 * @param arg0 the database
	 * @param arg1 the database
	 * @param arg2 the database
	 */
    public void onDowngrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(arg0);
    }

}
