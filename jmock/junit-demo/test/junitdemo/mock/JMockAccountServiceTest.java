package junitdemo.mock;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

public class JMockAccountServiceTest extends TestCase {
	Mockery context = new Mockery();

	public void testTransferOk(){
		final Account senderAccount = new Account("1", 200);
		final Account beneficiaryAccount = new Account("2", 100);
		
		//使用MockAccountManager
		final AccountManager mockAccountManager = context.mock(AccountManager.class);
		context.checking((new Expectations() {{
				one(mockAccountManager).findAccountForUser("1");
				will(returnValue(senderAccount));
				
				one(mockAccountManager).findAccountForUser("2");
				will(returnValue(beneficiaryAccount));
				
				one(mockAccountManager).updateAccount(senderAccount);
				one(mockAccountManager).updateAccount(beneficiaryAccount);
				
			}
		}));

		AccountService accountService = new AccountService();
		accountService.setAccountManager(mockAccountManager);
		
		//转帐操作
		accountService.transfer("1", "2", 50); 
		
		//验证
		assertEquals(150, senderAccount.getBalance());
		assertEquals(150, beneficiaryAccount.getBalance());
	}
}
