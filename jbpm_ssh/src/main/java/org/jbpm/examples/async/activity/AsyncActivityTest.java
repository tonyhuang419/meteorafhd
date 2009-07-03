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
package org.jbpm.examples.async.activity;

import java.util.List;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.job.Job;
import org.jbpm.test.JbpmTestCase;


/**
 * @author Tom Baeyens
 */
public class AsyncActivityTest extends JbpmTestCase {

  long deploymentDbid;
  
  protected void setUp() throws Exception {
    super.setUp();
    
    deploymentDbid = repositoryService.createDeployment()
        .addResourceFromClasspath("org/jbpm/examples/async/activity/process.jpdl.xml")
        .deploy();
  }

  protected void tearDown() throws Exception {
    repositoryService.deleteDeploymentCascade(deploymentDbid);
    
    super.tearDown();
  }

  public void testAsyncActivity() {
    ProcessInstance processInstance = executionService.startProcessInstanceByKey("AsyncActivity");
    String processInstanceId = processInstance.getId();
    
    assertEquals(Execution.STATE_ASYNC, processInstance.getState());
    
	List<Job> jobs = managementService.createJobQuery()
	.processInstanceId(processInstanceId)
	.list();

	System.out.println(jobs.size());
	
	
    Job job = managementService.createJobQuery()
      .processInstanceId(processInstanceId)
      .uniqueResult();
    managementService.executeJob(job.getDbid());
    
    processInstance = executionService.findProcessInstanceById(processInstanceId);

    assertEquals(Execution.STATE_ASYNC, processInstance.getState());
    
    job = managementService.createJobQuery()
      .processInstanceId(processInstanceId)
      .uniqueResult();
    managementService.executeJob(job.getDbid());
    
    assertNull(executionService.findProcessInstanceById(processInstanceId));
  }
}
