package com.example.carparking;

public class StoreReservedData {
    String markertitle;
    String saveCurrentDate;
    String saveCurrentTime;
    String countHours;
    String countMinutes;

    public StoreReservedData(){

    }

    public StoreReservedData(String markertitle, String saveCurrentDate,
                             String saveCurrentTime, String countHours, String countMinutes) {
        this.markertitle = markertitle;
        this.saveCurrentDate = saveCurrentDate;
        this.saveCurrentTime = saveCurrentTime;
        this.countHours = countHours;
        this.countMinutes = countMinutes;
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

    public String getCountHours() {
        return countHours;
    }

    public void setCountHours(String countHours) {
        this.countHours = countHours;
    }

    public String getCountMinutes() {
        return countMinutes;
    }

    public void setCountMinutes(String countMinutes) {
        this.countMinutes = countMinutes;
    }
}
