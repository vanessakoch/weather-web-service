package com.example.client2;

public class Update {
    String date;
    String hour;

    public Update(String date, String hour) {
        this.date = date;
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Update{" +
                "date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
