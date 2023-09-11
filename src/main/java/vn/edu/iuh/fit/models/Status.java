package vn.edu.iuh.fit.models;

public enum Status {
    ACTIVE(1),
    DEACTIVE(0),
    DELETE(-1);


    private final int status;

    private Status(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
