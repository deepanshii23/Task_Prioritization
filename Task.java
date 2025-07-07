import java.io.Serializable;
import java.time.LocalDate;
public class Task implements Serializable{
    // private static final long serialVersionUID = 1L;
    public String title;
    public String description;
    public int priority;
    public LocalDate deadline;
    public boolean isCompleted;
    public Task(String title,String description,int priority,LocalDate deadline){
        this.title=title;
        this.description=description;
        this.priority=priority;
        this.deadline=deadline;
        this.isCompleted=false;
    }
    @Override
    public String toString() {
        return String.format("Title: %s | Desc: %s | Priority: %d | Deadline: %s | Done: %s",
                title, description, priority, deadline, isCompleted ? "Yes" : "No");
    }
    
}