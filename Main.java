import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            System.out.println("\n---TASK PRIORITIZATION ---");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. View All Tasks");
            System.out.println("5. View Top Priority Tasks");
            System.out.println("6. Sort Tasks by Deadline");
            System.out.println("7. Edit task");
            System.out.println("8. View Completed vs Pending Tasks");
            System.out.println("9. Search Task by Title");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> {
                    System.out.print("Title: ");
                    String title = sc.nextLine().trim();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    int p;
                    while(true){
                        System.out.print("Priority (1-10): ");
                        p=sc.nextInt();
                        if(p>=1 && p<=10) break;
                        System.out.println("Invalid priority. Enter between 1 and 10");
                    }
                    sc.nextLine();
                    LocalDate dl = null;
                    boolean validDate = false;

                    while (!validDate) {
                        try {
                            System.out.print("Deadline (YYYY-MM-DD) : ");
                            String dateInput = sc.nextLine();
                            dl = LocalDate.parse(dateInput);

                            if (dl.isBefore(LocalDate.now())) {
                                System.out.println("Deadline can't be in the past. Please try again.");
                            } else {
                                validDate = true;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Use YYYY-MM-DD (e.g., 2025-04-04)");
                        }
                    }
                    manager.addTask(title, desc, p, dl);
                }
                case 2 -> {
                    System.out.print("Title to remove: ");
                    String title = sc.nextLine();
                    System.out.println(manager.removeTask(title) ? "Removed." : "Not found.");
                }
                case 3 -> {
                    System.out.print("Title to complete: ");
                    String title = sc.nextLine();
                    manager.markAsCompleted(title);
                }
                case 4 -> manager.showAllTasks();
                case 5 -> manager.showTopPriorities();
                case 6 -> {
                    manager.sortAccToDeadline();
                    manager.showAllTasks();
                }
                case 7 -> {
                    System.out.println("Enter the title of task to edit: ");
                    String oldtitle=sc.nextLine();
                    System.out.println("Enter new title : ");
                    String newtitle=sc.nextLine();
                    System.out.println("Enter new Description : ");
                    String newDesc=sc.nextLine();
                    int p;
                    while(true){
                        System.out.print("Priority (1-10): ");
                        p=sc.nextInt();
                        if(p>=1 && p<=10) break;
                        System.out.println("Invalid priority. Enter between 1 and 10");
                    }
                    sc.nextLine();
                    System.out.println("Enter new deadline (YYYY-MM-DD) : ");
                    LocalDate newDeadline=null;
                    boolean validDate=false;
                    while(!validDate){
                        try{
                            String dateInput=sc.nextLine();
                            newDeadline=LocalDate.parse(dateInput);
                            if(newDeadline.isBefore(LocalDate.now())){
                                System.out.println("Deadline can't be past. Please enetr a valid date.");
                            }
                            else{
                                validDate=true;
                            }
                        }
                        catch(DateTimeParseException e){
                            System.out.println("Invalid date format. Use YYYY-MM-DD");
                        }
                    }
                    manager.editTask(oldtitle, newtitle, newDesc, p, newDeadline);
                }
                case 8 -> manager.showStatus();
                case 9 -> {
                    System.out.print("Title to search: ");
                    manager.search(sc.nextLine());
                }
                case 10 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
