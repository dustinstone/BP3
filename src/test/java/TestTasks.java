import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskController.java
 *
 * @author Dustin Stone (stone.dustin94@gmail.com)
 */
public class TestTasks {

    // Json file for testing. This will change for each test.
    private static String file;

    // Mutable TaskController for testing.
    private static TaskController taskController;

    private static Tasks tasks;

    @Before
    public void setUp() {
        tasks = mock(Tasks.class);
    }

    @Test
    public void testGetMostRecentTaskSmallFile() {
        file = "test.json";
        taskController = new TaskController(file);
        String reply = taskController.getMostRecentTask(477);
        assertEquals("Review Client On-Boarding Request", reply);
    }

    @Test
    public void testGetNumberOfTasksSmallFile() {
        file = "test.json";
        taskController = new TaskController(file);
        int reply = taskController.getNumberOfTasks(477);
        assertEquals(2, reply);
    }

    @Test
    public void testGetTasksOnDateSmallFileNoTasks() {
        file = "test.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksOnDate("2015-01-01T00:32:38Z");
        assertEquals(0, reply);
    }

    @Test
    public void testGetTasksOnDateSmallFileOneTask() {
        file = "test.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksOnDate("2015-02-22T22:24:56Z");
        assertEquals(1, reply);
    }

    @Test
    public void testGetTasksBetweenDatesSmallFileOneTask() {
        file = "test.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksBetweenDates("2014-10-06T23:32:27Z", "2014-10-06T23:32:27Z");
        assertEquals(1, reply);
    }

    @Test
    public void testGetTasksBetweenDatesSmallFileAllTasks() {
        file = "test.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksBetweenDates("2014-10-06T23:32:27Z", "2015-02-24T00:32:27Z");
        assertEquals(3, reply);
    }

    @Test
    public void testGetMostRecentTask() {
        file = "task.json";
        taskController = new TaskController(file);
        String reply = taskController.getMostRecentTask(474);
        assertEquals("Background Check for New Coverage Branch", reply);
    }

    @Test
    public void testGetNumberOfTasks() {
        file = "task.json";
        taskController = new TaskController(file);
        int reply = taskController.getNumberOfTasks(474);
        assertEquals(23, reply);
    }

    @Test
    public void testGetTasksOnDate() {
        file = "task.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksOnDate("2015-01-01T00:32:38Z");
        assertEquals(4, reply);
    }

    @Test
    public void testGetTasksBetweenDates() {
        file = "task.json";
        taskController = new TaskController(file);
        int reply = taskController.getTasksBetweenDates("2015-01-01T00:32:38Z", "2015-02-24T00:32:27Z");
        assertEquals(225, reply);
    }

}
