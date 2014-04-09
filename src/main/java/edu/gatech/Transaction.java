package main.java.edu.gatech;

import java.util.Date;

/**
 * the transaction abstract class.
 * @author Carter Smith
 *
 */
public abstract class Transaction {
    /**
     * the time of transaction.
     */
    private Date timeStamp;
    /**
     * amount of transaction.
     */
    private double amount;
    /**
     * transaction account.
     */
    private Account account;
    /**
     * enum of transaction types.
     * @author Seok Jung
     *
     */
    public static enum TRANSACTION_TYPE { WITHDRAW, DEPOSIT };
	
    /**
     * Checks the transaction.
     * @param account of the transaction
     * @param amount of the transaction
     * @param type of the transaction
     */
    public Transaction(Account account, double amount, TRANSACTION_TYPE type) {
        if (account != null) {
            if (!(type == TRANSACTION_TYPE.WITHDRAW && amount > account.getBalance())) {
                this.account = account;
                this.amount = amount;
                timeStamp = new Date();
            } else {
				
            }
        } else {
			
        }
    }
	/**
	 * gets the amount of transaciton.
	 * @return returns amount.
	 */
    public double getAmount() {
        return amount;
    }
	/**
	 * the date of transaction.
	 * @return returns the date.
	 */
    public Date getTimestamp() {
        return new Date(timeStamp.getTime());
    }
	/**
	 * the account of transaction.
	 * @return the account.
	 */
    public Account getAssociatedAccount() {
        return account;
    }
	
}
