package suite;

import mock.CreateMockDate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import com.exam.TestFramework;




@RunWith(Suite.class)
@Suite.SuiteClasses({
	CreateMockDate.class,
	TestFramework.class
})
public class SuiteTest {
	//i need nothing
}
