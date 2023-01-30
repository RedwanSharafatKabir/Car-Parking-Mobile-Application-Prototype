package com.find.parkinglot.admin_panel;

public class StoreReviewData {
    String message;
    String comment;

    public StoreReviewData(){

    }

    public StoreReviewData(String message, String comment) {
        this.message = message;
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
