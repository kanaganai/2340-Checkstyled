package main.java.edu.gatech;
/**
 * Object User.
 * @author Ruobin Ling
 *
 */
public class User {
    /**
     * the User instance.
     */
    private static User uInstance = null;
	/**
	 * the logged in email.
	 */
    protected static String loggedInEmail;
	/**
	 * the constructor.
	 * @param loggedInEmail the logged in email.
	 */
    protected User(String loggedInEmail) {
        setLoggedInEmail(loggedInEmail);
    }
	/**
	 * get the instance of the user.
	 * @param loggedInEmail of the user.
	 * @return the instance.
	 */
    public static synchronized User getInstance(String loggedInEmail) {
        if (uInstance == null) {
            uInstance = new User(loggedInEmail);
        }
        return uInstance;
    }
	/**
	 * get the logged in email.
	 * @return the string of logged in email.
	 */
    public static String getLoggedInEmail() {
        return loggedInEmail;
    }
    /**
     * Set logged in email.
     * @param loggedInEmail the logged in email to set to.
     */
    public static void setLoggedInEmail(String loggedInEmail) {
        User.loggedInEmail = loggedInEmail;
    }
	/**
	 * log out of the user.
	 */
    public static void logOut() {
        loggedInEmail = null;
        uInstance = null;
    }
}
