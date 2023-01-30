package com.find.parkinglot;

public class StoreReservedData {
    String markertitle;
    String saveCurrentDate;
    String saveCurrentTime;

    public StoreReservedData(){

    }

    public StoreReservedData(String markertitle, String saveCurrentDate, String saveCurrentTime) {
        this.markertitle = markertitle;
        this.saveCurrentDate = saveCurrentDate;
        this.saveCurrentTime = saveCurrentTime;
    }

    public String getMarkertitle() {
        return markertitle;
    }

    public void setMarkertitle(String markertitle) {
        this.markertitle = markertitle;
    }

    public String getSaveCurrentDate() {
        return saveCurrentDate;
    }

    public void setSaveCurrentDate(String saveCurrentDate) {
        this.saveCurrentDate = saveCurrentDate;
    }

    public String getSaveCurrentTime() {
        return saveCurrentTime;
    }

    public void setSaveCurrentTime(String saveCurrentTime) {
        this.saveCurrentTime = saveCurrentTime;
    }
}
