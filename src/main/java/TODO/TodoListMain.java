package TODO;

import java.io.IOException;
import java.util.Scanner;

public class TodoListMain {
    public static void main(String[] args) {
        TaskController task = new TaskController();
        task.welcomeMessage();
        int input = 0;
        while(input < 5){
            displayMenu();
            input = getChoice();
            executeUserChoice(input, task);
        }
    }

    public static void displayMenu(){
        System.out.println("Travel TODO Menu List");
        System.out.println("1:ADD TASK");
        System.out.println("2:EDIT TASK");
        System.out.println("3:SHOW LIST");
        System.out.println("4:SAVE");
        System.out.println("5:SAVE & QUIT\n");
    }

    public static int getChoice() {
        System.out.println("Enter the value:");
        Scanner getChoiceScanner = new Scanner(System.in);
        int userInput = Integer.parseInt(getChoiceScanner.nextLine());
        return userInput;
    }

    public static void executeUserChoice(int choice, TaskController task){
        switch (choice){
            case 1:
                task.addTask();
                break;
            case 2:
                task.editTask();
                break;
            case 3:
                System.out.println("1.Show All bucket list");
                System.out.println("2.Sort by Tripname");
                System.out.println("3.Sort by Date");
                int newChoice = getChoice();
                switch(newChoice){
                    case 1:
                        task.showBucketList();
                        break;
                    case 2 :
                        task.showTripFilteredBucketList();
                        break;
                    case 3:
                        task.showFilteredDateList();
                        break;
                }
                break;
            case 4:
                task.save();
                break;
            case 5:
                task.saveAndQuit();
                break;
            default:
                System.out.println("Choose only 1 to 5 options");
                break;
        }
    }

}
