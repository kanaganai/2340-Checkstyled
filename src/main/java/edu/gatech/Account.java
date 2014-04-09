package main.java.edu.gatech;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Account class creates an Account object with a name, balance and history.
 * @author Ruobin Ling
 */
public class Account implements Serializable {
    /**
     * Account's name.
     */
    private String name;
    /**
     * Accont's balance.
     */
    private double balance;
    /**
     * Account's transaction history.
     */
    private Collection<Transaction> history;
	/**
	 * Sets a history of an Account with transactions.
	 */
    public Account() {
        history = new ArrayList<Transaction>();
    }
    /**
     * adds transaction to history.
     * @param transaction defines the transaction of the account.
     */
    public void applyTransaction(Transaction transaction) {
        history.add(transaction);
        balance += transaction.getAmount();
    }
	/**
	 * returns balance of the account.
	 * @return balance returns balance of the account.
	 */
    public double getBalance() {
        return balance;
    }
	
}
