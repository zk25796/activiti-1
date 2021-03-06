/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl;

import java.util.List;

import org.activiti.engine.Page;
import org.activiti.engine.ProcessInstance;
import org.activiti.engine.ProcessInstanceQuery;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;


/**
 * @author Tom Baeyens
 */
public class ProcessInstanceQueryImpl extends AbstractQuery<ProcessInstance> implements ProcessInstanceQuery {

  protected String processDefinitionId;
  protected String processDefinitionKey;
  protected String processInstanceId;
  protected String activityId;
  
  protected CommandExecutor commandExecutor;
  
  public ProcessInstanceQueryImpl() {
  }
  
  public ProcessInstanceQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  public boolean isProcessInstancesOnly() {
    return true;
  }

  public ProcessInstanceQueryImpl processDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
    return this;
  }

  public ProcessInstanceQueryImpl processDefinitionKey(String processDefinitionKey) {
    this.processDefinitionKey = processDefinitionKey;
    return this;
  }
  
  public ProcessInstanceQueryImpl processInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
    return this;
  }
  
  public ProcessInstanceQueryImpl activityId(String activityId) {
    this.activityId = activityId;
    return this;
  }
  
  public long executeCount(CommandContext commandContext) {
    return commandContext
      .getRuntimeSession()
      .findExecutionCountByDynamicCriteria(this);
  }

  @SuppressWarnings("unchecked")
  public List<ProcessInstance> executeList(CommandContext commandContext, Page page) {
    return (List) commandContext
      .getRuntimeSession()
      .findExecutionsByDynamicCriteria(this);
  }
  
  public String getProcessDefinitionKey() {
    return processDefinitionKey;
  }
  
  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

}
