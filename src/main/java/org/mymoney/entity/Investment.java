package org.mymoney;

public class Investment {
    private String id;
    private float sharePercentage;
    private int startingAmount;
    private int sipAmount;
    public Investment(int startingAmount) {
        this.startingAmount = startingAmount;
    }

    public float getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(float sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public int getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(int startingAmount) {
        this.startingAmount = startingAmount;
    }

    public int getSipAmount() {
        return sipAmount;
    }

    public void setSipAmount(int sipAmount) {
        this.sipAmount = sipAmount;
    }
}
