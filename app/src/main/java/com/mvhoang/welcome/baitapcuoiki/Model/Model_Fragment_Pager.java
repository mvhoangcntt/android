package com.mvhoang.welcome.baitapcuoiki.Model;

import java.io.Serializable;

public class Model_Fragment_Pager implements Serializable {
    private int id;
    private String Status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Model_Fragment_Pager(int id, String status, String date) {
        this.id = id;
        Status = status;
        Date = date;
    }

    private String Date;
}
