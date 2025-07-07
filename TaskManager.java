import java.util.*;
import java.io.*;
import java.time.LocalDate;
public class TaskManager {
    private LinkedList<Task>tasks =new LinkedList<>();
    public TaskManager(){
        loadTasks();
    }
    public void addTask(String title, String description, int priority, LocalDate deadline) {
        String newTitle = title.trim().toLowerCase();
        for (Task t : tasks) {
            if (t.title.trim().toLowerCase().equals(newTitle)) {
                System.out.println("Task with this title already exists.");
                return;
            }
        }
        tasks.add(new Task(title.trim(), description.trim(), priority, deadline));
        saveTasks();
        System.out.println("Task added successfully.");
    }
    
    public boolean removeTask(String title) {
        String searchTitle = title.trim().toLowerCase();
        boolean removed = tasks.removeIf(t -> t.title.trim().toLowerCase().equals(searchTitle));
        if (removed) saveTasks();
        return removed;
    }
    
    public void markAsCompleted(String title) {
        String searchTitle = title.trim().toLowerCase();
        Task t = tasks.stream()
                      .filter(task -> task.title.trim().toLowerCase().equals(searchTitle))
                      .findFirst()
                      .orElse(null);
        if (t != null) {
            t.isCompleted = true;
            saveTasks();
            System.out.println("Task marked completed.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void showAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        System.out.println("\n---------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-45s | %-8s | %-12s | %-10s |\n", "Title", "Description", "Priority", "Deadline", "Status");
        System.out.println("\n---------------------------------------------------------------------------------------------------------------");    
        for (Task t : tasks) {
            String status = t.isCompleted ? "Completed" : "Pending";
            System.out.printf("| %-20s | %-45s | %-8d | %-12s | %-10s |\n",
                    t.title.length() > 20 ? t.title.substring(0, 17) + "..." : t.title,
                    t.description.length() > 45 ? t.description.substring(0, 42) + "..." : t.description,
                    t.priority, t.deadline.toString(), status);
        }
    
        
        System.out.println("\n---------------------------------------------------------------------------------------------------------------");
    }

    public void showTopPriorities() {
        PriorityQueue<Task> pq = new PriorityQueue<>((a, b) -> a.priority - b.priority);
        for (Task task : tasks) {
            if (!task.isCompleted) {
                pq.add(task);
            }
        }
        if (pq.isEmpty()) {
            System.out.println("No pending tasks with priority.");
            return;
        }
        System.out.println("Top priority tasks (not completed):");
        String format = "| %-20s | %-45s | %-8s | %-12s |\n";
        String line = "-".repeat(98);
        System.out.println(line);
        System.out.printf(format, "Title", "Description", "Priority", "Deadline");
        System.out.println(line);
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            Task t = pq.poll();
            System.out.printf(format,
                    t.title.length() > 20 ? t.title.substring(0, 17) + "..." : t.title,
                    t.description.length() > 45 ? t.description.substring(0, 42) + "..." : t.description,
                    t.priority,
                    t.deadline.toString());
        }
        System.out.println(line);
    }
    
    public void sortAccToDeadline(){
        algo.sortByDeadline(tasks);
        System.out.println("Sorted According to Deadline.");
    }
    public void search(String title){
        Task t=algo.searchByTitle(tasks, title);
        System.out.println(t!=null ? t : "Not found.");
    }
    public void editTask(String oldtitle,String newTitle,String newDesc,int newPriority,LocalDate newDeadline){
        Task t=algo.searchByTitle(tasks, oldtitle);
        if(t!=null){
            t.title=newTitle;
            t.description=newDesc;
            t.priority=newPriority;
            t.deadline=newDeadline;
            saveTasks();
            System.out.println("Task updates successfully");
        }
        else{
            System.out.println("No task Found");
        }
    }
    public void showStatus(){
       int completed=0;
       int pending =0;
       for(Task t:tasks){
        if(t.isCompleted) completed++;
        else pending++;
       }
       System.out.println("Task Summary : ");
       System.out.println("Completed : "+ completed);
       System.out.println("Pending : "+ pending);
    }
    private void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("tasks.txt"))) {
            for (Task t : tasks) {
                pw.println(t.title + ";;" + t.description + ";;" + t.priority + ";;" + t.deadline + ";;" + t.isCompleted);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   

    private void loadTasks() {
        File file = new File("tasks.txt");
        if (!file.exists()) {
            System.out.println("No tasks file found, starting fresh");
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";;");
                if (parts.length == 5) {
                    String title = parts[0];
                    String desc = parts[1];
                    int priority = Integer.parseInt(parts[2]);
                    LocalDate deadline = LocalDate.parse(parts[3]);
                    boolean isCompleted = Boolean.parseBoolean(parts[4]);
    
                    Task t = new Task(title, desc, priority, deadline);
                    t.isCompleted = isCompleted;
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
