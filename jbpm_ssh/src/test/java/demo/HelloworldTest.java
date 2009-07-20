package demo;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.RepositoryService;
import org.jbpm.test.JbpmTestCase;



public class HelloworldTest extends JbpmTestCase {

//	long deploymentDbid;

	protected void setUp() throws Exception {
		super.setUp();
//		deploymentDbid = repositoryService.createDeployment().addResourceFromClasspath("demo/helloworld.jpdl.xml").deploy();
	}

	protected void tearDown() throws Exception {
//		repositoryService.deleteDeploymentCascade(deploymentDbid);
		super.tearDown();
	}

	public void testHelloworld() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addResourceFromClasspath("helloworld.jpdl.xml").deploy();
		ExecutionService executionService = processEngine.getExecutionService();
		executionService.startProcessInstanceByKey("HelloWorld");


		//		ProcessInstance processInstance = executionService.startProcessInstanceByKey("HelloWorld");
		//		String processInstanceId = processInstance.getId();
		//		//		assertEquals(Execution.STATE_ASYNC, processInstance.getState());
		//		List<Job> jobs = managementService.createJobQuery().processInstanceId(processInstanceId).list();
		//		Job job = jobs.get(0);
		//		managementService.executeJob(job.getDbid());
		//
		//		Date endTime = historyService.createHistoryProcessInstanceQuery().processInstanceId(processInstance.getId()).uniqueResult().getEndTime();
		//		System.out.println(endTime);
	}
}
