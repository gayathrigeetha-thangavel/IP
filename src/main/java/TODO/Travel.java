package TODO;

import java.util.Comparator;
import java.util.Date;

public class Travel implements Comparator<Travel> {
    private String tripName;
    private String bucketListName;
    private Date dueDate;
    private String status;

    Travel(){}

    Travel(String tripName, String  bucketListName, Date dueDate, String status) {
        this.tripName = tripName;
        this.bucketListName = bucketListName;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Date getDueDate() { return this.dueDate; }

    public void setDueDate(Date dueDate){ this.dueDate = dueDate; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBucketListName() {
        return bucketListName;
    }

    public void setBucketListName(String bucketListName) {
        this.bucketListName = bucketListName;
    }

    @Override
    public String toString() {
        return (tripName + " " + bucketListName + " " + dueDate + " " + status + "\n");
    }

    public static Comparator<Travel> tripComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel travel, Travel t1) {
            String tripList1 = travel.getTripName();
            String tripList2 = t1.getTripName();

            //Use this for Ascending
            return tripList1.compareTo(tripList2);

            //User this for Descending
            //return tripList2.compareTo(tripList1);
        }
    };

    public static Comparator<Travel> dateComparator = new Comparator<Travel>() {
        @Override
        public int compare(Travel travel, Travel t1) {

            Date buckList1 = travel.getDueDate();
            Date buckList2 = t1.getDueDate();

            //User this for Ascending
            return buckList1.compareTo(buckList2);

            //Use this for Descending
            //return buckList2.compareTo(buckList1);
        }
    };

    @Override
    public int compare(Travel travel, Travel t1) {
        return 0;
    }
}


