package aop;

public class AccountWithSecurityCheck extends AccountImpl{
	private  IAccount account;
	public AccountWithSecurityCheck (IAccount account) {
		this.account = account;
	}
	public void operation() {
		SecurityChecker.checkSecurity();
		account.operation();
	}

}
