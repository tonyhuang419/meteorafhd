package junit;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
  BaseTest.class,
  SubTest.class,
})

public class SuiteTest {

}