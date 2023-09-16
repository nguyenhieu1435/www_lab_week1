package vn.edu.iuh.fit.models;

public enum Status {
    ACTIVE(1),
    DEACTIVE(0),
    DELETE(-1);


    private final int statusNumber;

    private Status(int statusNumber){
        this.statusNumber = statusNumber;
    }

    public int getStatusNumber(){
        return statusNumber;
    }

    public static Status fromStatusNumber(int statusNumber){
        switch (statusNumber){
            case 1: return Status.ACTIVE;
            case 0: return Status.DEACTIVE;
            case -1: return Status.DELETE;
            default: return Status.DEACTIVE;
        }
    }


}
