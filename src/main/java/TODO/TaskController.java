package TODO;

import java.io.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class TaskController {
    Scanner scanner = new Scanner(System.in);
    Travel travel = new Travel();
    private ArrayList<Travel> travelItems= new ArrayList<Travel>();
    public static String projectFileName = "projectfile.rtf";
    DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

    //Welcome message
    public void welcomeMessage(){
        int doneCount = 0, pendingCount=0;
        String done = "Done";
        ArrayList<Travel> readListFromFile = getExistingTravelItems();

        for(Travel myTravel: readListFromFile){
            String status = myTravel.getStatus();
            if(status.toUpperCase().contains(done.toUpperCase())){
                doneCount++;
            }else{
                pendingCount++;
            }
        }
        System.out.println("Hey, Welcome to ToDoList, You have "+pendingCount+" Pending and "+doneCount+" done!");
    }
    //To add the task
    public void addTask() {
        getUserInputToAddTask();
    }

    public void getUserInputToAddTask(){
        try{
            System.out.println("Add Task");
            System.out.println("Enter the trip name: " );
            travel.setTripName(scanner.nextLine());
            System.out.println("Enter the bucket list name: ");
            travel.setBucketListName(scanner.nextLine());
            System.out.println("Enter the due-date(dd-MM-yyyy): ");
            String strDate = scanner.nextLine();
            Date myDate = targetFormat.parse(strDate);
            travel.setDueDate(myDate);
            System.out.println("Enter the status: ");
            travel.setStatus(scanner.nextLine());
            if(travelItems.size() != 0){
                travelItems.clear();
            }
            travelItems.add(travel);
            System.out.println("task successfully added. Continue to save.");
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //To edit the task. This has two operations like delete and edit the status
    public void editTask(){
        System.out.println("You have 2 options");
        System.out.println("d.Delete:");
        System.out.println("e.Edit:");
        System.out.println("Enter the value:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        switch (userInput){
            case "d":
                removeTask();
                break;
            case "e":
                modifyTask();
                break;
            default:
                System.out.println("Choose between d & e");
                break;
        }
    }

    //Delete the task: user should enter the trip name to delete
    public void removeTask() {
        ArrayList<Travel> listFromFile = new ArrayList<Travel>();
        Travel travelList = new Travel();
        System.out.println("Delete Task");
        System.out.println("Choose a task name to delete:");
        String userInput = scanner.nextLine();

        listFromFile = getExistingTravelItems();

        /* For Loop for iterating ArrayList */
        for (int counter = 0; counter < listFromFile.size(); counter++) {
            travelList = listFromFile.get(counter);
            if(userInput.toUpperCase().equals(travelList.getTripName().toUpperCase())) {
                System.out.println("Trip exists and proceeding to Delete");
                listFromFile.remove(counter);
            }
        }
        writeTaskToFile(listFromFile,false);
    }

    //Edit the task: user should enter the trip name to search and new status for update
    public void modifyTask() {
        String bucketList,date,status,tripName;
        Scanner modifyScanner = new Scanner(System.in);
        ArrayList<Travel> listFromFile = new ArrayList<Travel>();
        Travel travelList = new Travel();
        System.out.println("Edit Task");
        System.out.println("Choose a task name to Edit:");
        String userInput = modifyScanner.nextLine();

        listFromFile = getExistingTravelItems();

        /* For Loop for iterating ArrayList */
        for (int counter = 0; counter < listFromFile.size(); counter++) {

            travelList = listFromFile.get(counter);

            if(userInput.toUpperCase().equals(travelList.getTripName().toUpperCase())) {
                System.out.println("Your new Status:");
                status = modifyScanner.nextLine();
                travelList.setStatus(status);
            }
            listFromFile.set(counter,travelList);
        }
        writeTaskToFile(listFromFile,false);
    }

    //read the values from the file and stored it in array list of objects
    private ArrayList<Travel> getExistingTravelItems() {
        ArrayList<Travel> readListFromFile = new ArrayList<Travel>();
        Travel existingTravelList = new Travel();
        try {
            Scanner newScanner = new Scanner(new File(getFilePath(projectFileName)));
            while (newScanner.hasNextLine()) {
                existingTravelList = splitValuesLineByLine(newScanner.nextLine());
                readListFromFile.add(existingTravelList);
            }
            newScanner.close();
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return readListFromFile;
    }

    // take single line of the text, split it by comma separator and stored it in an object
    private Travel splitValuesLineByLine(String newScannerValue) throws ParseException {
        Travel existingTravel = new Travel();

        String[] splitedString =  newScannerValue.split(",");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = dateFormat.parse(splitedString[2]);

        existingTravel.setTripName(splitedString[0]);
        existingTravel.setBucketListName(splitedString[1]);
        existingTravel.setDueDate(myDate);
        existingTravel.setStatus(splitedString[3]);
        return existingTravel;
    }

    // show the task that has to sort by date
    public void showFilteredDateList(){
        System.out.println("Show Filtered Date List");
        ArrayList<Travel> readListFromFile = getExistingTravelItems();
        Collections.sort(readListFromFile,Travel.dateComparator);

        for(Travel travelList: readListFromFile){
            showFilteredListWithFormat(travelList);
        }
    }

    // show the task sorted by tripname
    public void showTripFilteredBucketList(){
        System.out.println("Show Filtered Trip Name");
        ArrayList<Travel> readListFromFile = getExistingTravelItems();
        Collections.sort(readListFromFile,Travel.tripComparator);
        for(Travel myTravel: readListFromFile){
            showFilteredListWithFormat(myTravel);
        }
    }

    //Show all task
    public void showBucketList(){
        System.out.println("Show All Bucket List");
        ArrayList<Travel> readListFromFile = getExistingTravelItems();
        if (readListFromFile.size() == 0){
            System.out.println("task is empty");
        }
        else{
            for(Travel travelList: readListFromFile){
                showFilteredListWithFormat(travelList);
            }
        }
    }

    //display format for show list
    private void showFilteredListWithFormat(Travel travelList){
        String combinedStr,tripName,bucketList,dateStr,progress;
        Date myDate;
        tripName = travelList.getTripName();
        bucketList = travelList.getBucketListName();
        myDate = travelList.getDueDate();
        //Convert format for Display purpose only
        dateStr = targetFormat.format(myDate);
        progress = travelList.getStatus();

        combinedStr = tripName+','+bucketList+','+dateStr+','+progress;
        System.out.println(combinedStr);
    }

    // save the object which is currently added task
    public void save(){
        System.out.println("Save");
        if(travelItems.size() != 0) {
            writeTaskToFile(travelItems, true);
            travelItems.clear();
            }else{
            System.out.println("Cache is empty! Nothing to save at the moment. Add Task to save.");
        }
    }

    // save all task when quit the program
    public void saveAndQuit(){
        System.out.println("Save & Quit");
        if(travelItems.size() != 0) {
            writeTaskToFile(travelItems, true);
            travelItems.clear();
        }
    }

    public static void writeTaskToFile(ArrayList<Travel> travelItems,boolean appendFlag){
        try {
            String filePath = getFilePath(projectFileName);
            FileWriter fileWriter = new FileWriter(filePath,appendFlag);
            Writer writerToFile = new BufferedWriter(fileWriter);
            int listSize = travelItems.size();
            for (int i = 0; i < listSize; i++)
            {
                String task = travelItems.get(i).getTripName();
                String bucketList = travelItems.get(i).getBucketListName();
                Date date = travelItems.get(i).getDueDate();

                DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
                String myDate = targetFormat.format(date);

                String status = travelItems.get(i).getStatus();

                writerToFile.write(task+','+bucketList+','+myDate+','+status+ "\n");
            }
            System.out.println("Written to File");
            writerToFile.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getFilePath(String projectFileName)
    {
        File fileName = new File(projectFileName);
        String filePath = fileName.getAbsolutePath();
        return filePath;

    }

}
