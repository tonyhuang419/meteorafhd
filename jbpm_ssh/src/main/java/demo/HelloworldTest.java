/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
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
		repositoryService.createDeployment().addResourceFromClasspath("demo/helloworld.jpdl.xml").deploy();
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
