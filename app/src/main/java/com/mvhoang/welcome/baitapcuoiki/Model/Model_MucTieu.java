package com.mvhoang.welcome.baitapcuoiki.Model;

import java.io.Serializable;

public class Model_MucTieu implements Serializable {
    private int id;
    private String NoiDung;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public Model_MucTieu(int id, String noiDung) {
        this.id = id;
        NoiDung = noiDung;
    }
}
