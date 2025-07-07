import java.util.*;
public class algo {
    public static void sortByDeadline(List<Task>list){
        if(list.size()<=1) return;
        int mid=list.size()/2;
        List<Task>left=new ArrayList<>(list.subList(0, mid));
        List<Task>right=new ArrayList<>(list.subList(mid,list.size()));
        sortByDeadline(left);
        sortByDeadline(right);
        merge(left,right,list);
    }
    private static void merge(List<Task>left,List<Task>right,List<Task>result){
        result.clear();
        int i=0,j=0,k=0;
        while(i<left.size() && j<right.size()){
            if(left.get(i).deadline.isBefore(right.get(j).deadline)){
                result.add(k++,left.get(i++));
            }
            else{
                result.add(k++,right.get(j++));
            }
        }
        while(i<left.size()) result.add(left.get(i++));
        while(j<right.size()) result.add(right.get(j++));
    }
    public static Task searchByTitle(List<Task>list,String title){
        for(Task t:list){
            if(t.title.equalsIgnoreCase(title)){
                return t;
            }
        }
        return  null;
    }

}
