package junitdemo.mock;

public interface AccountManager{
	Account findAccountForUser(String userId);
	void updateAccount(Account account);
}
