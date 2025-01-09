package ServletsPages;

import java.util.Date;

public class Task {
    private int id;
    private String taskName;
    private int hoursNeeded;
    private Date dueDate;
    private String description;
    private String categoryName;

    // Constructor
    public Task(int id, String taskName, int hoursNeeded, Date dueDate, String description, String categoryName) {
        this.id = id;
        this.taskName = taskName;
        this.hoursNeeded = hoursNeeded;
        this.dueDate = dueDate;
        this.description = description;
        this.categoryName = categoryName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getHoursNeeded() {
        return hoursNeeded;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

