package com.mvhoang.welcome.baitapcuoiki.Model;

import java.io.Serializable;

public class ModelCongViec implements Serializable {
    private int STT;
    private String Name;
    private String Status;
    private String Date;

    public int getSTT() {
        return STT;
    }

    public ModelCongViec setSTT(int STT) {
        this.STT = STT;
        return this;
    }

    public String getName() {
        return Name;
    }

    public ModelCongViec setName(String name) {
        Name = name;
        return this;
    }

    public String getStatus() {
        return Status;
    }

    public ModelCongViec setStatus(String status) {
        Status = status;
        return this;
    }

    public String getDate() {
        return Date;
    }

    public ModelCongViec setDate(String date) {
        Date = date;
        return this;
    }

    public ModelCongViec(int STT, String name, String status, String date) {
        this.STT = STT;
        Name = name;
        Status = status;
        Date = date;
    }
}
