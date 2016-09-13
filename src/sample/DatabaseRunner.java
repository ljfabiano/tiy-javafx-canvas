package sample;

/**
 * Created by jfabiano on 9/9/2016.
 */
public class DatabaseRunner {
    public static void main(String[] args) throws Exception {
        System.out.println("DB runner is running...");
        ToDoDatabase db = new ToDoDatabase();
        db.init();
    }
}
