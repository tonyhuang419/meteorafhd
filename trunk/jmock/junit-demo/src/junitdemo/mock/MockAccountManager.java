package junitdemo.mock;

import java.util.Hashtable;

public class MockAccountManager implements AccountManager{
	private Hashtable accounts = new Hashtable();
	public void addAccount(String userId, Account account){
		this.accounts.put(userId, account);
	}
	public Account findAccountForUser(String userId){
		return (Account) this.accounts.get(userId);
	}
	public void updateAccount(Account account){
		// do nothing
	}
}
