package main.java.edu.gatech;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Handles the database.
 * @author Seok Jung
 *
 */
public class DBHandler {
	/**
	 * The method that helps with creating the tables.
	 */
    private DBHelper helper;
    /**
     * The actual database.
     */
    private SQLiteDatabase database;
    /**
     * the balance.
     */
    protected static double curBalance;
    /**
     * the selected account.
     */
    protected static String selectedAccount = "";
    /**
     * Table name Users.
     */
    private static final String TABLE_USERS = "Users";
    /**
     * Table name Accounts.
     */
    private static final String TABLE_ACCOUNTS = "Accounts";
    /**
     * table name transactions.
     */
    private static final String TABLE_TRANSACTIONS = "Transactions";
    /**
     * the logged in email.
     */
    private static String loggedInEmail = User.getLoggedInEmail();
	
    /**
     * the constructor of DBHandler, takes in the android context.
     * @param context the android activity context
     */
    
    public DBHandler(Context context) {
        helper = new DBHelper(context);
    }
	/**
	 * creates the User.
	 * @param email User's email.
	 * @param password of the User.
	 * @return temp returns what user has been created.
	 */
    public long createUser(String email, String password) {
        database = helper.getWritableDatabase();
        Log.d("createUser", "Entering createUser()");
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("Password", password);
        Log.d("createUser", "Creating: " + email + " and " + password);
        long temp = database.insert(TABLE_USERS, null, values);
        return temp;
    }
	/**
	 * selects the user of certain email and pasword.
	 * @param email of the User.
	 * @param password of the User.
	 * @return myCursor the cursor of the User.
	 */
    public Cursor selectUser(String email, String password) {
        database = helper.getWritableDatabase();
        Log.d("selectUser", "Entering selectUser()");
        Cursor myCursor = database.rawQuery("select * from Users where Email=? and Password=?" , new String [] {email, password});
        if (myCursor != null) {
            Log.d("selectUser", "myCursor != null");
        }
        return myCursor;
    }
	/**
	 * creates the account of the user.
	 * @param email of the user.
	 * @param accountName of the account.
	 * @param balance of the account.
	 * @return temp a long of account's information.
	 */
    public long createAccount(String email, String accountName, String balance) {
        database = helper.getWritableDatabase();
        Log.d("createAccount", "Entering createAccount()");
        ContentValues values = new ContentValues();
        values.put("Email", email);
        values.put("AccountName", accountName);
        values.put("Balance", balance);
        Log.d("createAccount", "Creating account: " + email + " and " + accountName + " and $" + balance);
        long temp = database.insert(TABLE_ACCOUNTS, null, values);
        return temp;
    }
	/**
	 * selects the account.
	 * @param email of the account.
	 * @param accountName of the account.
	 * @return myCursor the cursor of the account.
	 */
    public Cursor selectAccount(String email, String accountName) {
        database = helper.getWritableDatabase();
        Log.d("selectAccount", "Entering selectAccount");
        Cursor myCursor = database.rawQuery("select * from " +  TABLE_ACCOUNTS + " where Email=? and AccountName=?", new String[]{email, accountName});
        if (myCursor != null) {
            Log.d("selectAccount", "myCursor != null");
        }
        return myCursor;
    }
    /**
     * gets all users.
     * @return end list of all users.
     */
    public String getAllUsers() {
        database = helper.getWritableDatabase();
        Log.d("getAllUsers", "Entering getAllUsers()");
        Cursor myCursor = database.rawQuery("select * from Users", null);
        String end = "";
        if (myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                String name = myCursor.getString(myCursor.getColumnIndex("Email"));
                end += name + "\n";
                myCursor.moveToNext();
            }
        }
        Log.d("Users: ", end);
        return end;
    }
	/**
	 * get all accounts.
	 * @return end the String list of all accounts.
	 */
    public String getAllAccounts() {
        database = helper.getWritableDatabase();
        Log.d("getAllAccounts", "Entering getAllAccounts()");
        Cursor myCursor = database.rawQuery("select * from Accounts where Email = '" + User.getLoggedInEmail() + "'", null);
        String end = "";
        if (myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                String name = myCursor.getString(myCursor.getColumnIndex("AccountName"));
                end += name + "\n";
                myCursor.moveToNext();
            }
        }
        Log.d("Accounts: ", end);
        return end;
    }
	/**
	 * Gets account names by array list.
	 * @return accountNames the array List of strings of account names.
	 */
    public List<String> getAccountNames() {
        database = helper.getWritableDatabase();
        List<String> accountNames = new ArrayList<String>();
        Cursor myCursor = database.rawQuery("select * from Accounts", null);
        if (myCursor.moveToFirst()) {
            do {
                accountNames.add(myCursor.getString(myCursor.getColumnIndex("AccountName")));
                Log.d("Account added:", myCursor.getString(myCursor.getColumnIndex("AccountName")));
            } while(myCursor.moveToNext());
        }
        myCursor.close();
        return accountNames;
    }
	/**
	 * gets the balance.
	 * @param accountName of the account.
	 * @return the balance of accountName
	 */
    public double getBalance(String accountName) {
        database = helper.getWritableDatabase();
        Cursor myCursor = database.rawQuery("select balance from Accounts where Email = '" + User.getLoggedInEmail() + "' and AccountName = '" + accountName + "'", null);
        double balance = 0;
        if (myCursor.moveToFirst()) {
            Log.d("Getting Balance: ", myCursor.getString(myCursor.getColumnIndex("Balance")));
            balance = Double.parseDouble(myCursor.getString(myCursor.getColumnIndex("Balance")));
        }
        myCursor.close();
        return balance;
    }
    /**
     * withdraws balance.
     * @param amount of the withdrawal.
     * @param accountName of the account.
     * @param destination the destination of the withdrawal
     * @param date of transaction.
     */
    public void withdrawBalance(double amount, String accountName, String destination, String date) {
        database = helper.getWritableDatabase();
        curBalance = getBalance(accountName);
        curBalance -= amount;
        database.execSQL("UPDATE Accounts SET balance = " + curBalance + " WHERE AccountName = '" + accountName + "'");
        Log.d("New balance", Double.toString(curBalance));
        ContentValues values = new ContentValues();
        values.put("Email", User.getLoggedInEmail());
        values.put("AccountName", accountName);
        values.put("Date", date);
        values.put("Amount", amount);
        values.put("TransactionType",  "W");
        values.put("SourceDestination", destination);
        Log.d("DepositBalance", "Inserting: " + values);
        database.insert(TABLE_TRANSACTIONS, null, values);
    }
    /**
     * deposits balance.
     * @param amount of deposit
     * @param accountName of the account
     * @param source of the deposit
     * @param date of the transaction
     */
    public void depositBalance(double amount, String accountName, String source, String date) {
        database = helper.getWritableDatabase();
        curBalance = getBalance(accountName);
        curBalance += amount;
        database.execSQL("UPDATE Accounts SET balance = " + curBalance + " WHERE AccountName = '" + accountName + "'");
        Log.d("New balance", Double.toString(curBalance));
        ContentValues values = new ContentValues();
        values.put("Email", User.getLoggedInEmail());
        values.put("AccountName", accountName);
        values.put("Date", date);
        values.put("Amount", amount);
        values.put("TransactionType",  "D");
        values.put("SourceDestination", source);
        Log.d("DepositBalance", "Inserting: " + values);
        database.insert(TABLE_TRANSACTIONS, null, values);
    }
    /**
     * gets transaction.
     * @param accountName of the account.
     * @return myCursor the account.
     */
    public Cursor getTransactions(String accountName) {
        database = helper.getWritableDatabase();
        String email = User.getLoggedInEmail();
        Cursor myCursor = database.rawQuery("select * from " +  TABLE_TRANSACTIONS + " where Email=? and AccountName=?", new String[]{email, accountName});
        return myCursor;
    }
    /**
     * creates spending report.
     * @param startDate of the transaction
     * @param endDate of the transaction
     * @return report of the transactions.
     */
    public String generateSpendingCategoriesReport(String startDate, String endDate) {
        database = helper.getWritableDatabase();
        String email = User.getLoggedInEmail();
        String report = "Spending Categories Report for " + email + " from " + startDate + " to " + endDate + "\n-------------------------------------------------\n";
        String sql = "Select SourceDestination, AccountName, sum(amount) from Transactions where email = ? and TransactionType = ? and date between '" + startDate + "' and '" + endDate 
			+ "' group by SourceDestination order by AccountName";
        Cursor myCursor = database.rawQuery(sql, new String[]{email, "W"});
        if (myCursor.moveToFirst()) {
            Log.d("ReportPage", "Report query not null!");
            while (!myCursor.isAfterLast()) {
                String accountName = myCursor.getString(myCursor.getColumnIndex("AccountName"));
                report += accountName + ":\t";
                String category = myCursor.getString(myCursor.getColumnIndex("SourceDestination"));
                report += category + " - $";
                int amount = myCursor.getInt(myCursor.getColumnIndex("sum(amount)"));
                report += amount + "\n";
                Log.d("ReportPage", "Movin to next row");
                myCursor.moveToNext();
            }
            myCursor.close();
        }
        return report;

    }
	
	
	
	
	
	
	
	
	
	
	
}

