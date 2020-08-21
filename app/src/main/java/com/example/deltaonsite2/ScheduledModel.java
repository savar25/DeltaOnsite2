package com.example.deltaonsite2;

public class ScheduledModel {

    int ID;
    String message;
    String pnum;
    Integer hr;
    Integer min;

    public ScheduledModel(int ID, String message, String pnum, Integer hr, Integer min) {
        this.ID = ID;
        this.message = message;
        this.pnum = pnum;
        this.hr = hr;
        this.min = min;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }
}
