package vn.edu.iuh.fit.models;

import jakarta.annotation.Priority;

public enum IsGrant {
    DISABLE("0"),
    ENABLE("1");

    private final String grant;

    private IsGrant(String grant){
        this.grant = grant;
    }

    public String getGrant() {
        return grant;
    }
    public static IsGrant getStatusNumber(String grant){
        switch (grant){
            case "0":
                return IsGrant.DISABLE;
            case "1":
                return IsGrant.ENABLE;
            default:
                return IsGrant.DISABLE;
        }
    }
}
