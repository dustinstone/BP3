
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class implements all logic related to instances and tasks.
 * Task objects are grouped by their instanceId.
 *
 * @author Dustin Stone (stone.dustin94@gmail.com)
 */
public class Tasks {

    // instanceIds are recorded as Integers and tasks as Task objects
    private Map<Integer, TreeSet<Task>> instances;

    // task.json is an array of Map objects
    private ArrayList<Map<String, Object>> json;

    /**
     * Default constructor
     */
    public Tasks() {

    }

    /**
     * This method builds 'json' from a file.
     * buildJson() must be called before buildTasks().
     * Returns true if successful and false otherwise.
     */
    public boolean buildJson(String file) {
        boolean result = false;

        // Verify that file is not null
        if (file != null) {
            File jsonFile = new File(file);
            // Verify that jsonFile exists
            if (jsonFile.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    // Build 'json' - Codehaus Jackson docs: http://jackson.codehaus.org/
                    json = mapper.readValue(jsonFile, ArrayList.class);
//                    System.out.println(tasks);
                    result = true;

                } catch (IOException e) {
                    System.out.println("IOException: " + file + " not found");
                }
            }
        }

        return result;
    }

    /**
     * This method builds Tasks from objects in 'json' and groups them
     * by instanceId. buildJson() must be called before buildTasks().
     * Returns true if successful and false otherwise.
     */
    public boolean buildTasks() {
        boolean result = false;

        // Create instances
        instances = new TreeMap<Integer, TreeSet<Task>>();

        if (json != null) {
            // Iterate over 'json' (objects in task.json)
            Iterator iterator = json.iterator();
            while (iterator.hasNext()) {
                // Objects in 'json' are Maps
                HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
//                        System.out.println(map);
                // Create a new Task object and set its values
                Task task = new Task();
                task.name = (String) map.get("name");
                task.id = Integer.parseInt((String) map.get("id"));
                task.instanceId = (Integer) map.get("instanceId");
                task.status = (String) map.get("status");
                task.createDate = (String) map.get("createDate");
                task.closeDate = (String) map.get("closeDate");
                task.dueDate = (String) map.get("dueDate");
//                        System.out.println(task);

                // Add this Task object to instances
                TreeSet<Task> instanceTasks = instances.containsKey(task.instanceId) ?
                        instances.get(task.instanceId) : new TreeSet<Task>();
                instanceTasks.add(task);
                instances.put(task.instanceId, instanceTasks);
                result = true;
            }
        }
        else {
            System.out.println("Error: json is null!");
        }

        return result;
    }

//    public void printInstances() {
//        for (Map.Entry<Integer, TreeSet<Task>> e : instances.entrySet()) {
//            System.out.println(e.getKey() + " " + e.getValue());
//        }
//    }

    /**
     * This method returns the name of the most recent task with an
     * instanceId equal to 'instanceId'.
     */
    public String getMostRecentTask(int instanceId) {
        return instances.get(instanceId).first().name;
    }

    /**
     * This method returns the number of tasks with an instanceId
     * equal to 'instanceId'.
     */
    public int getNumberOfTasks(int instanceId) {
        return instances.get(instanceId).size();
    }

    /**
     * This method returns the number of tasks opened on the given date.
     */
    public TreeSet<Task> getTasksOpened(String date) {
        TreeSet<Task> result = new TreeSet<Task>();

        // Iterate over each instances tasks. If a task.createDate is
        // equal to 'date', add it to 'result'.
        for (TreeSet<Task> instanceTasks : instances.values()) {
            for (Task task : instanceTasks) {
                if (task.createDate.equals(date)) {
                    result.add(task);
                }
            }
        }

        return result;
    }

    /**
     * This method returns the number of tasks closed on the given date.
     */
    public TreeSet<Task> getTasksClosed(String date) {
        TreeSet<Task> result = new TreeSet<Task>();

        // Iterate over each instances tasks. If a task.closeDate is
        // equal to 'date', add it to 'result'.
        for (TreeSet<Task> instanceTasks : instances.values()) {
            for (Task task : instanceTasks) {
                if (task.closeDate != null && task.closeDate.equals(date)) {
                    result.add(task);
                }
            }
        }

        return result;
    }

    /**
     * This method returns the number of tasks opened (inclusive) in a range
     * of given dates.
     */
    public TreeSet<Task> getTasksOpened(String startDate, String endDate) {
        TreeSet<Task> result = new TreeSet<Task>();

        // Iterate over each instances tasks. If a task.createDate is
        // after or equal to 'startDate' and before or equal to 'endDate',
        // add it to 'result'.
        for (TreeSet<Task> instanceTasks : instances.values()) {
            for (Task task : instanceTasks) {
                if ((task.createDate.compareTo(startDate) == 1 ||
                        task.createDate.compareTo(startDate) == 0) &&
                        (task.createDate.compareTo(endDate) == -1 ||
                                task.createDate.compareTo(endDate) == 0)) {
                    result.add(task);
                }
            }
        }

        return result;
    }

    /**
     * This method returns the number of tasks closed (exclusive) in a range
     * of given dates.
     */
    public TreeSet<Task> getTasksClosed(String startDate, String endDate) {
        TreeSet<Task> result = new TreeSet<Task>();

        // Iterate over each instances tasks. If a task.closeDate is
        // after 'startDate' and before 'endDate', add it to 'result'.
        for (TreeSet<Task> instanceTasks : instances.values()) {
            for (Task task : instanceTasks) {
                if (task.closeDate != null &&
                        (task.closeDate.compareTo(startDate) == 1 &&
                                task.closeDate.compareTo(endDate) == -1)) {
                    result.add(task);
                }
            }
        }

        return result;
    }

    /**
     * A private inner class to represent a task in task.json
     */
    private class Task implements Comparable<Task> {
        // Instance variables
        private String name;
        private int id;
        private int instanceId;
        private String status;
        private String createDate;
        private String closeDate;
        private String dueDate;

        // Default constructor
        public Task() {

        }

        /**
         * Override toString()
         */
        public String toString() {
            return "Name: " + name +
                    " Id: " + id +
                    " InstanceId: " + instanceId +
                    " Status: " + status +
                    " CreateDate: " + createDate +
                    " CloseDate: " + closeDate +
                    " DueDate: " + dueDate;
        }

        /**
         * Implements compareTo() of type Task in descending order (Most recent first).
         */
        public int compareTo(Task other) {
            int result;

            // The date format in task.json YYYY-MM-DD lets us use String compareTo().
            // If this.createDate and other.createDate are equal, compare their due dates
            // If this.dueDate and other.dueDate are equal, just pick one...
            result = this.createDate.compareTo(other.createDate);
            if (result == 0) {
                result = this.dueDate.compareTo(other.dueDate);
                if (result == 0) {
                    result = 1;
                }
            }

            return -result;
        }
    }
}
