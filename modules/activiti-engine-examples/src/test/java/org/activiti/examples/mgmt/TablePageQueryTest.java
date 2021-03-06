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
package org.activiti.examples.mgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TablePage;
import org.activiti.engine.Task;
import org.activiti.engine.test.ProcessEngineTestCase;


/**
 * @author Joram Barrez
 */
public class TablePageQueryTest extends ProcessEngineTestCase {
  
  public void testGetTablePage() {
    List<String> taskIds = generateDummyTasks(20);
    
    TablePage tablePage = managementService.createTablePageQuery()
      .tableName("ACT_TASK")
      .start(0)
      .size(5)
      .singleResult();
    
    assertEquals(0, tablePage.getStart());
    assertEquals(5, tablePage.getSize());
    assertEquals(5, tablePage.getRows().size());
    assertEquals(20, tablePage.getTotal());
    
    tablePage = managementService.createTablePageQuery()
      .tableName("ACT_TASK")
      .start(14)
      .size(10)
      .singleResult();
    
    assertEquals(14, tablePage.getStart());
    assertEquals(6, tablePage.getSize());
    assertEquals(6, tablePage.getRows().size());
    assertEquals(20, tablePage.getTotal());

    taskService.deleteTasks(taskIds);
  }
  
  public void testGetSortedTablePage() {
    List<String> taskIds = generateDummyTasks(15);
    
    // With an ascending sort
    TablePage tablePage = managementService.createTablePageQuery()
      .tableName("ACT_TASK")
      .start(1)
      .size(7)
      .orderAsc("NAME_")
      .singleResult();
    String[] expectedTaskNames = new String[] {"B", "C", "D", "E", "F", "G", "H"};
    verifyTaskNames(expectedTaskNames, tablePage.getRows());
    
    // With a descending sort
    tablePage = managementService.createTablePageQuery()
      .tableName("ACT_TASK")
      .start(6)
      .size(8)
      .orderDesc("NAME_")
      .singleResult();
    expectedTaskNames = new String[] {"I", "H", "G", "F", "E", "D", "C", "B"} ;
    verifyTaskNames(expectedTaskNames, tablePage.getRows());
    
    taskService.deleteTasks(taskIds);
  }
  
  private void verifyTaskNames(String[] expectedTaskNames, List<Map<String, Object>> rowData) {
    assertEquals(expectedTaskNames.length, rowData.size());
    for (int i=0; i < expectedTaskNames.length; i++) {
      assertEquals(expectedTaskNames[i], rowData.get(i).get("NAME_"));
    }
  }
  
  private List<String> generateDummyTasks(int nrOfTasks) {
    ArrayList<String> taskIds = new ArrayList<String>();
    for (int i = 0; i < nrOfTasks; i++) {
      Task task = taskService.newTask();
      task.setName(((char)('A' + i)) + "");
      taskService.saveTask(task);
      taskIds.add(task.getId());
    }
    return taskIds;
  } 

}
