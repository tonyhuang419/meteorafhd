package junitdemo.mock;

import junit.framework.TestCase;
import junitdemo.mock.Account;
import junitdemo.mock.AccountService;
import junitdemo.mock.MockAccountManager;

public class AccountServiceTest extends TestCase {

	public void testTransferOk(){
		//ʹ��MockAccountManager
		MockAccountManager mockAccountManager = new MockAccountManager();
		Account senderAccount = new Account("1", 200);
		Account beneficiaryAccount = new Account("2", 100);
		mockAccountManager.addAccount("1", senderAccount);
		mockAccountManager.addAccount("2", beneficiaryAccount);
		AccountService accountService = new AccountService();
		accountService.setAccountManager(mockAccountManager);
		
		//ת�ʲ���
		accountService.transfer("1", "2", 50); 
		
		//��֤
		assertEquals(150, senderAccount.getBalance());
		assertEquals(150, beneficiaryAccount.getBalance());
	}
	
}
