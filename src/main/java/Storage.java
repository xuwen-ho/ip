import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
//    String FILE_PATH = "data/hal.txt";
    boolean isDoneDefault = false;
    File file;
    public Storage(String filePath) {
        this.file = new File(filePath);
        this.file.getParentFile().mkdirs();

        try {
            // Ensure the file is created before attempting to read from it
            if (this.file.createNewFile()) {
                System.out.println("File created: " + filePath);
            } else {
                System.out.println("File already exists: " + filePath);
            }

            readFromFile();
        } catch (IOException e) {
            System.out.println("Error creating or reading from file");
            e.printStackTrace();
        }
    }
    public void writeToFile(String filePath, ArrayList<Task> newestTaskList) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter(filePath);
            // get tasklist
            for (Task task : newestTaskList) {
                fw.write(task.getFileString() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Task> readFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        ArrayList<Task> prevTaskList = new ArrayList<>();

        try {

            while (sc.hasNextLine()) {
//                System.out.println(sc.nextLine());
                String[] parts = sc.nextLine().split(" \\| ");
//                System.out.println(parts);

                String taskType = parts[0];
                boolean isDone = (Integer.parseInt(parts[1]) == 1);
                String description = parts[2];

                switch (taskType) {
                    case "T":
                        prevTaskList.add(new Todo(isDone, description));
                        break;
                    case "D":
                        prevTaskList.add(new Deadline(isDoneDefault, description, parts[3]));
                        break;
                    case "E":
                        prevTaskList.add(new Event(isDoneDefault, description, parts[3], parts[4]));
                        break;
                    default:
                        System.out.println("Unknown task type");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return prevTaskList;
    }
}
