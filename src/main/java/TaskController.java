import com.sun.javafx.tk.Toolkit;

/**
 * This class acts as a controller for objects of type Tasks.java.
 *
 * @author Dustin Stone (stone.dustin94@gmail.com)
 */
public class TaskController {

    // Variables
    public static Tasks tasks;

    /**
     * Default constructor.
     */
    public TaskController() {
        this("task.json");
    }

    /**
     * A constructor that accepts a Json file and builds a tasks object.
     */
    public TaskController(String file) {
        tasks = new Tasks();
        tasks.buildJson(file);
        tasks.buildTasks();
    }

    /**
     * A method to inject Tasks dependency for Mockito
     */
    public void setTaskController(Tasks tasks) {
        this.tasks = tasks;
    }

    /**
     * Answers the following question:
     * Given a particular instanceId, give me the name of the most recent task.
     */
    public String getMostRecentTask(int instanceId) {
        return tasks.getMostRecentTask(instanceId);
    }

    /**
     * Answers the following question:
     * Given a particular instanceId, give me the count of tasks.
     */
    public int getNumberOfTasks(int instanceId) {
        return tasks.getNumberOfTasks(instanceId);
    }

    /**
     * Answers the following question:
     * Given a specific date give me the current number of open and
     * closed tasks. The date is inclusive so if we ask for midnight Oct 12,
     * a task opened or closed on midnight would count
     *
     * This method makes the following assumptions:
     * If a task is opened and closed on the same date, it counts twice.
     */
    public int getTasksOnDate(String date) {
        return tasks.getTasksOpened(date).size() + tasks.getTasksClosed(date).size();
    }

    /**
     * Answers the following question:
     * Given a specific start and end date, how many tasks were opened
     * and how many were closed in that range. The start date is inclusive,
     * the end date is exclusive.
     *
     * This method makes the following assumptions:
     * If a task is opened and closed between the startDate and endDate,
     * it counts twice.
     */
    public int getTasksBetweenDates(String startDate, String endDate) {
        return tasks.getTasksOpened(startDate, endDate).size() +
                tasks.getTasksClosed(startDate, endDate).size();
    }

}
