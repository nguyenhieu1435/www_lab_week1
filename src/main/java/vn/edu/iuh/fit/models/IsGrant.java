package vn.edu.iuh.fit.models;

public enum IsGrant {
    DISABLE(0),
    ENABLE(1);

    private final int grant;

    private IsGrant(int grant){
        this.grant = grant;
    }

    public int getGrant() {
        return grant;
    }
}
